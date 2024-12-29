import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoardComponent } from './components/board-list/board-list.component';
import { HomeComponent } from './components/home/home.component';
import { TaskComponent } from './components/task/task.component';
import { SprintComponent } from './components/sprint/sprint.component';
import { AddSprintComponent } from './components/addsprint/addsprint.component';
import { AddtaskComponent } from './components/addtask/addtask.component';
import { TaskDetailsComponent } from './components/taskdetails/taskdetails.component';
import { AddboardComponent } from './components/addboard/addboard.component';
import { LoginComponent } from './components/login/login.component';
import { AdminGuard } from './services/admin-guard.service';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'boards', component: BoardComponent},
  { path: 'login', component: LoginComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: 'home', component: HomeComponent },
  { path: 'tasks/board/:boardId', component: TaskComponent },
  { path: 'sprints', component: SprintComponent},
  { path: 'addsprint', component: AddSprintComponent, canActivate: [AdminGuard] },
  { path: 'addboard', component: AddboardComponent, canActivate: [AdminGuard] },
  { path: 'board/:boardId/addtask', component: AddtaskComponent, canActivate: [AdminGuard] },
  { path: 'task/:taskId', component: TaskDetailsComponent },
  { path: '**', component: HomeComponent }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
