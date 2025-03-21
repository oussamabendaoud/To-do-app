package com.test.spring.jpa.h2.controller;

import com.test.spring.jpa.h2.dto.TaskDTO;
import com.test.spring.jpa.h2.model.Task;
import com.test.spring.jpa.h2.repository.TaskRepository;
import com.test.spring.jpa.h2.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;

  public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository) {
    this.taskRepository = taskRepository;
    this.projectRepository = projectRepository;
  }

  // Créer une tâche
  @PostMapping("/projects/{projectId}")
  public ResponseEntity<Task> createTask(@PathVariable Long projectId, @RequestBody Task taskRequest) {
    return projectRepository.findById(projectId)
            .map(project -> {
              Task task = new Task(
                      taskRequest.getTitle(),
                      taskRequest.getDescription(),
                      taskRequest.getStatus(),
                      taskRequest.getDueDate(),
                      project
              );
              return new ResponseEntity<>(taskRepository.save(task), HttpStatus.CREATED);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  // Récupérer toutes les tâches d'un projet et les convertir en DTOs
  @GetMapping("/projects/{projectId}")
  public ResponseEntity<List<TaskDTO>> getAllTasks(@PathVariable Long projectId) {
    try {
      List<Task> tasks = taskRepository.findByProjectId(projectId);
      if (tasks.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      // Convertir les entités Task en TaskDTO
      List<TaskDTO> taskDTOs = tasks.stream()
              .map(task -> new TaskDTO(task.getTitle(), task.getDescription(), task.getDueDate()))
              .collect(Collectors.toList());

      return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Supprimer une tâche
  @DeleteMapping("/{taskId}")
  public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long taskId) {
    try {
      // Vérifie si la tâche existe
      if (taskRepository.existsById(taskId)) {
        // Supprime la tâche de la base de données
        taskRepository.deleteById(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retourne 204 (No Content) si la suppression a réussi
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retourne 404 (Not Found) si la tâche n'existe pas
    } catch (Exception e) {
      // Si une exception se produit, retourne 500 (Internal Server Error)
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
