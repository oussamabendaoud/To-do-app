import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskService } from '../../services/task.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent implements OnInit {
  @Input() projectId!: number;
  taskForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private router: Router
  ) {
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      dueDate: ['', Validators.required],
      status: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }
  onSubmit(): void {
    if (this.taskForm.valid) {
      const taskData = new Task(
        this.taskForm.value.title,
        this.taskForm.value.description,
        this.taskForm.value.dueDate,
        this.taskForm.value.status,
        this.projectId.toString()  // Convertir projectId en string
      );

      this.taskService.createTask(taskData).subscribe(
        (response: Task) => {
          console.log('Tâche créée:', response);
          this.router.navigate(['/projects', this.projectId.toString(), 'tasks']);  // Convertir projectId en string ici aussi
        },
        (error: any) => {
          console.error('Erreur lors de la création de la tâche', error);
        }
      );
    }
  }





}
