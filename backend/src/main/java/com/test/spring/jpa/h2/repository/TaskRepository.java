package com.test.spring.jpa.h2.repository;

import com.test.spring.jpa.h2.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByProjectId(Long projectId);  // Pour obtenir les tâches par ID de projet


  Optional<Task> findByIdAndProjectId(Long taskId, Long projectId);  // Pour obtenir une tâche par son ID et ID de projet

  List<Task> findByStatus(String status);  // Pour obtenir les tâches par statut

  List<Task> findByTitleContainingIgnoreCase(String title);  // Pour rechercher par titre (insensible à la casse)

  List<Task> findByStatusAndTitleContainingIgnoreCase(String status, String title);  // Pour rechercher par statut et titre
}

