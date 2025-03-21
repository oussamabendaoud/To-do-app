import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../models/task.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) { }

  getTasksByProjectId(projectId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/projects/${projectId}`);
  }

  deleteTask(taskId: number): Observable<void> {
    if (!taskId) {
      throw new Error('Task ID is undefined');
    }
    return this.http.delete<void>(`http://localhost:8081/api/tasks/${taskId}`);
  }



  createTask(task: Task): Observable<Task> {
    return this.http.post<Task>(`${this.apiUrl}`, task);
  }


}
