import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { TaskListComponent } from './components/task-list/task-list.component';
import { TaskFormComponent } from './components/task-form/task-form.component';

const routes: Routes = [
  { path: 'projects', component: ProjectListComponent },
  { path: 'tasks/:projectId', component: TaskListComponent },
  { path: 'add-tasks/:projectId', component: TaskFormComponent }, // Route pour ajouter une tâche
  { path: '', redirectTo: '/projects', pathMatch: 'full' },
  { path: 'task-list/:projectId', component: TaskListComponent },
  { path: 'tasks/projects/:projectId', component: TaskListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
