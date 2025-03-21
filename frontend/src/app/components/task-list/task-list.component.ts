import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { TaskService } from '../../services/task.service';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit, OnDestroy {
  tasks: Task[] = [];
  filteredTasks: Task[] = [];
  projectId!: number;
  searchText: string = '';  // Champ de recherche dynamique
  selectedStatus: string = '';  // Statut sélectionné
  isLoading = true;
  errorMessage: string | null = null;

  currentPage: number = 1; // Page actuelle
  itemsPerPage: number = 5; // Nombre d'éléments par page

  private destroy$ = new Subject<void>();

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.resolveProjectId();
    this.loadTasks();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private resolveProjectId(): void {
    const id = this.route.snapshot.paramMap.get('projectId');
    if (id) {
      this.projectId = +id;
    } else {
      this.errorMessage = 'Project ID not found in URL';
      this.isLoading = false;
    }
  }

  loadTasks(): void {
    if (!this.projectId) return;

    this.isLoading = true;
    this.errorMessage = null;

    this.taskService.getTasksByProjectId(this.projectId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (tasks) => {
          this.tasks = tasks;
          this.filteredTasks = this.filterTasks(); // Applique le filtre
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error loading tasks:', err);
          this.errorMessage = 'Failed to load tasks. Please try again later.';
          this.isLoading = false;
        }
      });
  }

  // Fonction pour filtrer les tâches en fonction de la recherche et du statut
  filterTasks(): Task[] {
    return this.tasks.filter(task => {
      const matchesSearchText = task.title.toLowerCase().includes(this.searchText.toLowerCase()) ||
        task.description.toLowerCase().includes(this.searchText.toLowerCase());

      const matchesStatus = this.selectedStatus ? task.status.toLowerCase() === this.selectedStatus.toLowerCase() : true;

      return matchesSearchText && matchesStatus;
    }).slice((this.currentPage - 1) * this.itemsPerPage, this.currentPage * this.itemsPerPage); // Limite l'affichage à 5 tâches par page
  }

  // Méthode pour changer de page
  changePage(page: number): void {
    this.currentPage = page;
    this.filteredTasks = this.filterTasks(); // Refiltre les tâches en fonction de la nouvelle page
  }

  // Appelée lorsque l'utilisateur change le champ de recherche
  onSearchChange(): void {
    this.filteredTasks = this.filterTasks();
  }

  // Appelée lorsque l'utilisateur change le sélecteur de statut
  onStatusChange(): void {
    this.filteredTasks = this.filterTasks();
  }

  // Méthode pour afficher les détails de la tâche
  viewTaskDetails(taskId: number): void {
    console.log('Viewing details for task:', taskId);
  }

  // Méthode pour modifier la tâche
  editTask(taskId: number): void {
    console.log('Editing task:', taskId);
  }

  // Méthode pour supprimer la tâche
  deleteTask(taskId: number): void {
    console.log('Deleting task with ID:', taskId);
    if (!taskId) {
      console.error('Invalid taskId:', taskId);
      return;
    }

    if (!confirm('Are you sure you want to delete this task?')) return;

    this.taskService.deleteTask(taskId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.tasks = this.tasks.filter(task => task.id !== taskId);  // Mise à jour optimiste : supprime la tâche localement
        },
        error: (err) => {
          console.error('Error deleting task:', err);
          this.errorMessage = 'Failed to delete task. Please try again.';
        }
      });
  }
}
