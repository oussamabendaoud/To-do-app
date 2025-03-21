package com.test.spring.jpa.h2.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private String status; // Valeurs possibles : pending, in_progress, completed

  @Column(name = "due_date")
  private LocalDate dueDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  @JsonIgnore
  private Project project;

  // Constructeurs
  public Task() {}

  public Task(String title, String description, String status, LocalDate dueDate, Project project) {
    this.title = title;
    this.description = description;
    this.status = status;
    this.dueDate = dueDate;
    this.project = project;
  }

  // Getters & Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public LocalDate getDueDate() { return dueDate; }
  public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

  public Project getProject() { return project; }
  public void setProject(Project project) { this.project = project; }
}