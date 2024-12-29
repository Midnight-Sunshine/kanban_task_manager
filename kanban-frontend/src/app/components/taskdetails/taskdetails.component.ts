import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Task } from 'src/app/models/Task';
import { Sprint } from 'src/app/models/Sprint';
import { User } from 'src/app/models/User';
import { TaskService } from 'src/app/services/task.service';
import { SprintService } from 'src/app/services/sprint.service';
import { UserService } from 'src/app/services/user.service';
import { Board } from 'src/app/models/Board';
import { BoardService } from 'src/app/services/board.service';
import { LoginService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-task-details',
  templateUrl: './taskdetails.component.html',
  styleUrls: ['./taskdetails.component.css']
})
export class TaskDetailsComponent implements OnInit {
  task: Task | null = null;
  sprints: Sprint[] = [];
  users: User[] = [];
  selectedSprintIds: number[] = [];
  selectedUserIds: number[] = [];
  boards: Board[] = [];
  userRole: string = ''; // Variable to store the user's role
  currentUserId: number = 0; // Current logged-in user's ID

  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService,
    private sprintService: SprintService,
    private userService: UserService,
    private router: Router,
    private boardService: BoardService,
    private Loginservice: LoginService
  ) {}

  ngOnInit(): void {
    this.fetchBoards();
    this.userRole = this.Loginservice.getUserRole();
    this.currentUserId = this.Loginservice.getUserId() || 0; // Provide a default value of 0 if null // Assuming this method exists

    this.route.params.subscribe(params => {
      const taskId = +params['taskId'];
      this.taskService.getTask(taskId).subscribe(
        (task: Task) => {
          this.task = task;

          // Safely map user IDs and sprint IDs, handling undefined or null values
          this.selectedUserIds = (task.users || []).map(u => u.id);
          this.selectedSprintIds = (task.sprints || []).map(s => s.id);

          console.log('Task Users:', task.users);
          console.log('Task Sprints:', task.sprints);
          console.log('Selected User IDs:', this.selectedUserIds);
          console.log('Selected Sprint IDs:', this.selectedSprintIds);
        },
        (error: any) => {
          console.error('Error fetching task details:', error);
        }
      );
    });

    this.sprintService.getAllSprints().subscribe(
      (sprints: Sprint[]) => {
        this.sprints = sprints;
      },
      (error: any) => {
        console.error('Error fetching sprints:', error);
      }
    );

    this.userService.getAllUsers().subscribe(
      (users: User[]) => {
        this.users = users;
      },
      (error: any) => {
        console.error('Error fetching users:', error);
      }
    );
  }

  isMemberOrAdmin(): boolean {
    // Check if the current user is a member of the task or has admin role
    return this.userRole === 'ROLE_ADMIN' || this.selectedUserIds.includes(this.currentUserId);
  }

  updateTask(): void {
    if (this.task) {
      // Log current task data
      console.log('Updating task:', this.task);

      // Ensure the selected sprint and user IDs are correctly mapped
      const sprintIds = this.selectedSprintIds.map((id: any) => +id); // Convert to numbers
      const userIds = this.selectedUserIds.map((id: any) => +id);

      // Update the task relationships
      this.task.sprints = this.sprints.filter((s) => sprintIds.includes(s.id));
      this.task.users = this.users.filter((u) => userIds.includes(u.id));

      // Update task via service
      this.taskService.updateTask(this.task).subscribe(
        (updatedTask: Task) => {
          console.log('Task updated successfully:', updatedTask);
          this.router.navigate(['/tasks/board', this.task?.board.id]); // Navigate back to task list
        },
        (error: any) => {
          console.error('Error updating task:', error);
        }
      );
    }
  }

  fetchBoards(): void {
    this.boardService.getAllBoards().subscribe(
      (boards: Board[]) => {
        this.boards = boards;
      },
      (error: any) => {
        console.error('Error fetching boards: ', error);
      }
    );
  }

  toggleUser(userId: number): void {
    const index = this.selectedUserIds.indexOf(userId);
    if (index !== -1) {
      this.selectedUserIds.splice(index, 1);
    } else {
      this.selectedUserIds.push(userId);
    }
  }
}
