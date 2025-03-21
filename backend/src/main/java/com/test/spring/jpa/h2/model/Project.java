package com.test.spring.jpa.h2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt; // Ajout du timestamp

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Task> tasks;

  // Constructeurs
  public Project() {}

  // Constructeur avec tous les attributs, y compris les tâches
  public Project(String name, String description, LocalDate startDate, LocalDate endDate, List<Task> tasks) {
    this.name = name;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.tasks = tasks;
  }

  // Constructeur sans la liste des tâches
  public Project(String name, String description, LocalDate startDate, LocalDate endDate) {
    this.name = name;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  // Initialisation automatique de la date de création
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  // Getters & Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public LocalDate getStartDate() { return startDate; }
  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

  public LocalDate getEndDate() { return endDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public List<Task> getTasks() { return tasks; }
  public void setTasks(List<Task> tasks) { this.tasks = tasks; }

  // Méthode toString (utile pour afficher l'objet de manière lisible)
  @Override
  public String toString() {
    return "Project{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", createdAt=" + createdAt +
            ", tasks=" + tasks +
            '}';
  }

  // Méthodes equals et hashCode (utile pour la gestion des objets en collections)
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Project project = (Project) o;
    return Objects.equals(id, project.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
