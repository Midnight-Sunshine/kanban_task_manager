import { Component, OnInit } from '@angular/core';
import { Board } from 'src/app/models/Board';
import { Sprint } from 'src/app/models/Sprint';
import { LoginService } from 'src/app/services/auth.service';
import { BoardService } from 'src/app/services/board.service';
import { SprintService } from 'src/app/services/sprint.service';


@Component({
  selector: 'app-sprint',
  templateUrl: './sprint.component.html',
  styleUrls: ['./sprint.component.css']
})
export class SprintComponent implements OnInit {
  sprints: Sprint[] = [];
  boards: Board[] = [];
  alertMessage: string = '';
  userRole: string = ''; // Variable to store the user's role

  constructor(private sprintService: SprintService, private boardService: BoardService, private Loginservice: LoginService
  ) { }

  ngOnInit(): void {
    this.fetchSprints();
    this.fetchBoards();
    this.userRole = this.Loginservice.getUserRole();
  }

  fetchSprints(): void {
    this.sprintService.getAllSprints().subscribe(
      (sprints: Sprint[]) => {
        this.sprints = sprints;
      },
      (error: any) => {
        console.error('Error fetching sprints: ', error);
      }
    );
  }

  deleteSprint(sprintId: number): void {
    if (confirm('Are you sure you want to delete this sprint?')) {
      this.sprintService.deleteSprint(sprintId).subscribe(
        () => {
          this.sprints = this.sprints.filter(sprint => sprint.id !== sprintId);
          this.alertMessage = '';
        },
        (error: any) => {
          console.error('Error deleting sprint: ', error);
          // Set the alert message
          this.alertMessage = 'Error deleting sprint. Please try again.';
        }
      );
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
