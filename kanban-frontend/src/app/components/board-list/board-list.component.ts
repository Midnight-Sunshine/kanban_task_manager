// board-list.component.ts

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Board } from '../../models/Board';
import { BoardService } from '../../services/board.service';
import { LoginService } from '../../services/auth.service';

@Component({
  selector: 'app-board',
  templateUrl: './board-list.component.html',
  styleUrls: ['./board-list.component.css']
})
export class BoardComponent implements OnInit {
  boards: Board[] = [];
  userRole: string = ''; // Variable to store the user's role

  constructor(
    private router: Router,
    private boardService: BoardService,
    private Loginservice: LoginService
  ) { }

  ngOnInit(): void {
    this.fetchBoards();
    // Get the user's role from sessionStorage when the component initializes
    this.userRole = this.Loginservice.getUserRole();
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

  showTasks(boardId: number): void {
    this.router.navigate(['/tasks', 'board', boardId]);
  }

  goToAddBoard(): void {
    this.router.navigate(['/addboard']);
  }

  goToSprint(): void {
    this.router.navigate(['/sprints']);
  }

  deleteBoard(boardId: number): void {
    if (confirm('Are you sure you want to delete this board?')) {
      this.boardService.deleteBoard(boardId).subscribe();
      this.boards = this.boards.filter(board => board.id !== boardId);
    }
  }
}
