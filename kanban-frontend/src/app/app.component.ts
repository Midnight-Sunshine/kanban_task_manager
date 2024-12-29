import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'kanban-web';
  constructor(private router: Router) { }

  navigateToBoard() {
    this.router.navigate(['/boards']);
  }
}
