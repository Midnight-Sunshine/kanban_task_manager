import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Board } from 'src/app/models/Board';
import { LoginService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-board-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class BoardNavbarComponent {
  @Input() boards: Board[] = [];
  navbarVisible: boolean = false;

  constructor(private router: Router, private loginService :LoginService) { }

  showTasks(boardId: number): void {
    this.router.navigate(['/tasks', 'board', boardId]);
  }

  goToSprint(): void {
    this.router.navigate(['/sprints']);
  }
  goToboards(): void {
    this.router.navigate(['/boards']);
  }

  toggleNavbar(): void {
    this.navbarVisible = !this.navbarVisible;
  }
  Logout() {
    this.loginService.logout();
  }
}
