import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Task } from 'src/app/models/Task';
import { TaskService } from 'src/app/services/task.service';
import { TaskStatus } from 'src/app/models/TaskStatus';
import { Board } from 'src/app/models/Board';
import { BoardService } from 'src/app/services/board.service';
import { LoginService } from 'src/app/services/auth.service';
import { Sprint } from 'src/app/models/Sprint';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  tasks: Task[] = [];
  boards: Board[] = [];
  sprints : Sprint[] = [];
  TaskStatus = TaskStatus;
  boardId: number | any;
  userRole: string = ''; // Variable to store the user's role
  userId: number | any;

  
  constructor(
    private router: Router, 
    private route: ActivatedRoute,
    private taskService: TaskService,
    private boardService: BoardService,
    private Loginservice: LoginService
  ) { }
  ngOnInit(): void {
    this.fetchTasks();
    this.boardId = this.route.snapshot.params['boardId'];
    this.fetchBoards();
    this.userRole = this.Loginservice.getUserRole();
    this.userId=this.Loginservice.getUserId();

  }

  toggleDropdown(event: MouseEvent) {
    event.stopPropagation();
    const dropdownContent = (event.currentTarget as HTMLElement).nextElementSibling;
    if (dropdownContent) {
      dropdownContent.classList.toggle('show');
    }
  }
  onDragStart(event: DragEvent, task: Task) {
    event.dataTransfer!.setData('text/plain', JSON.stringify(task));
  }
  onDrop(event: DragEvent, status: TaskStatus) {
    event.preventDefault();
    const taskId = JSON.parse(event.dataTransfer!.getData('text/plain')).id;
    console.log(status);
    const taskToUpdate = this.tasks.find(task => task.id === taskId);
    if (taskToUpdate) {
      if (taskToUpdate.status === TaskStatus.TODO && status === TaskStatus.IN_PROGRESS) {
        this.updateTaskStatusToInProgress(taskId, status);
      } else if (taskToUpdate.status === TaskStatus.IN_PROGRESS && status === TaskStatus.DONE) {
        this.updateTaskStatusToDone(taskId, status);
      }else if (taskToUpdate.status === TaskStatus.IN_PROGRESS && status === TaskStatus.TODO) {
        this.updateTaskStatusToDo(taskId, status);
      }else if (taskToUpdate.status === TaskStatus.DONE && status === TaskStatus.TODO) {
        this.updateTaskStatusToDo(taskId, status);
      }else if (taskToUpdate.status === TaskStatus.DONE && status === TaskStatus.IN_PROGRESS) {
        this.updateTaskStatusToInProgress(taskId, status);
      }else if (taskToUpdate.status === TaskStatus.TODO && status === TaskStatus.DONE) {
        this.updateTaskStatusToDone(taskId, status);
      }
    } else {
      console.error('Task not found with ID: ', taskId);
    }
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
  }
  filterTasks(status: TaskStatus): Task[] {
    return this.tasks.filter(task => task.status === status);
  }
  fetchTasks(): void {
    const boardId = this.route.snapshot.params['boardId'];
    const userId = this.Loginservice.getUserId();
    const userRole = this.Loginservice.getUserRole();
  
    if (userRole === 'ROLE_ADMIN') {
      this.taskService.getTasksByBoardId(boardId)
        .subscribe(
          (tasks: Task[]) => {
            this.tasks = tasks;
          },
          (error: any) => {
            console.error('Error fetching tasks: ', error);
          }
        );
    } else {
      if (userId) {
        this.taskService.getTasksByBoardId(boardId)
          .subscribe(
            (tasks: Task[]) => {
              this.tasks = tasks.filter(task => task.users.some(user => user.id === userId));
            },
            (error: any) => {
              console.error('Error fetching tasks: ', error);
            }
          );
      } else {
        console.error('User ID not found');
      }
    }
  }
  

  updateTaskStatusToInProgress(taskId: number, status: TaskStatus): void {
    this.taskService.updateTaskStatusToInProgress(taskId, status)
      .subscribe(
        () => {
          this.fetchTasks();
        },
        (error: any) => {
          console.error('Error updating task status: ', error);
        }
      );  
  }
  updateTaskStatusToDone(taskId: number, status: TaskStatus): void {
    this.taskService.updateTaskStatusToDone(taskId, status)
      .subscribe(
        () => {
          this.fetchTasks();
        },
        (error: any) => {
          console.error('Error updating task status: ', error);
        }
      );  
  }
  updateTaskStatusToDo(taskId: number, status: TaskStatus): void {
    this.taskService.updateTaskStatusToDo(taskId, status)
      .subscribe(
        () => {
          this.fetchTasks();
        },
        (error: any) => {
          console.error('Error updating task status: ', error);
        }
      );  
  }
  deleteTask(taskId: number): void {
    if (confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(taskId).subscribe();
      this.tasks = this.tasks.filter(task => task.id !== taskId);
    }
  }

  navigateToAddTask(): void {
    if (this.boardId !== null) {
      this.router.navigate(['/board', this.boardId, 'addtask']);
    } else {
      console.error('Board ID is null');
    }
  }
  fetchBoards(): void {
    this.boardService.getAllBoards()
      .subscribe(
        (boards: Board[]) => {
          this.boards = boards;
        },
        (error: any) => {
          console.error('Error fetching boards: ', error);
        }
      );
  }

}
