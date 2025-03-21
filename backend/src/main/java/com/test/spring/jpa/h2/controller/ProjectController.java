package com.test.spring.jpa.h2.controller;

import com.test.spring.jpa.h2.model.Project;
import com.test.spring.jpa.h2.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  @Autowired
  ProjectRepository projectRepository;

  // GET all projects
  @GetMapping
  public ResponseEntity<List<Project>> getAllProjects() {
    try {
      List<Project> projects = projectRepository.findAll();  // Assurez-vous que la méthode `findAll()` fonctionne
      return projects.isEmpty() ?
              new ResponseEntity<>(HttpStatus.NO_CONTENT) :
              new ResponseEntity<>(projects, HttpStatus.OK);
    } catch (Exception e) {
      // Ajoutez un log ou imprimez l'exception pour voir l'erreur détaillée
      System.out.println("Error occurred: " + e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  // CREATE project
  @PostMapping
  public ResponseEntity<Project> createProject(@RequestBody Project project) {
    try {
      Project _project = projectRepository.save(project);  // Enregistrer dans la base de données
      return new ResponseEntity<>(_project, HttpStatus.CREATED);  // Retourner le projet créé
    } catch (Exception e) {
      // Capture d'exception et affichage des erreurs dans les logs
      System.out.println("Error: " + e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // En cas d'erreur, renvoyer 500
    }
  }





  // GET single project
  @GetMapping("/{id}")
  public ResponseEntity<Project> getProjectById(@PathVariable("id") long id) {
    Optional<Project> projectData = projectRepository.findById(id);
    return projectData.map(project ->
                    new ResponseEntity<>(project, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  // UPDATE project
  @PutMapping("/{id}")
  public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project) {
    Optional<Project> projectData = projectRepository.findById(id);

    if (projectData.isPresent()) {
      Project _project = projectData.get();
      _project.setName(project.getName());
      _project.setDescription(project.getDescription());
      _project.setStartDate(project.getStartDate());
      _project.setEndDate(project.getEndDate());
      return new ResponseEntity<>(projectRepository.save(_project), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // DELETE project
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") long id) {
    try {
      projectRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // DELETE all projects
  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteAllProjects() {
    try {
      projectRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
