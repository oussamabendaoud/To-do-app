import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/project.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  showForm = false;
  todayDate = new Date().toISOString().split('T')[0];

  projectForm = this.fb.group({
    name: ['', [Validators.required, Validators.maxLength(50)]],
    description: ['', Validators.required],
    startDate: [this.todayDate, Validators.required],
    endDate: ['']
  });

  constructor(
    private projectService: ProjectService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.projectService.getAll().subscribe((projects: Project[]) => {
      this.projects = projects;
    });
  }

  addProject(): void {
    if (this.projectForm.valid) {
      const formValue = this.projectForm.value;
      const updatedProject: Project = {
        name: formValue.name!,
        description: formValue.description!,
        startDate: new Date(formValue.startDate!),
        endDate: formValue.endDate ? new Date(formValue.endDate) : undefined,
        createdAt: new Date()
      };

      this.projectService.create(updatedProject).subscribe({
        next: () => {
          this.loadProjects();
          this.toggleForm();
        },
        error: (error) => console.error(error)
      });
    }
  }

// Vérifier que projectId est un nombre avant de l'utiliser
  addTaskToProject(projectId: number | undefined): void {
    if (projectId !== undefined) {
      // Effectuer l'action avec l'ID du projet
      this.router.navigate([`/add-tasks/${projectId}`]);
    } else {
      console.error('ID du projet non valide');
    }
  }



  // Méthode pour éditer un projet
  editProject(projectId: number): void {
    const project = this.projects.find((p) => p.id === projectId);
    if (project) {
      this.projectForm.setValue({
        name: project.name,
        description: project.description,
        startDate: project.startDate.toISOString().split('T')[0],
        endDate: project.endDate ? project.endDate.toISOString().split('T')[0] : ''
      });
      this.showForm = true;
    }
  }

  deleteProject(projectId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce projet ?')) {
      this.projectService.delete(projectId).subscribe({
        next: () => {
          this.loadProjects();
        },
        error: (error) => console.error(error)
      });
    }
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
    if (!this.showForm) this.projectForm.reset();
  }
}
