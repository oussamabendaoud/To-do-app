export class Task {
  id: number;
  title: string;
  description: string;
  dueDate: string;
  status: string;  // Vérifiez que cette ligne existe bien

  constructor(id: number, title: string, description: string, dueDate: string, status: string) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.status = status;  // Assurez-vous que `status` est bien inclus
  }
}
