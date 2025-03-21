package com.test.spring.jpa.h2.repository;

import com.test.spring.jpa.h2.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  // Seule méthode nécessaire pour la recherche
  List<Project> findByNameContainingIgnoreCase(String name);

}