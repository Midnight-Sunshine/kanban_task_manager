import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LoginService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(): boolean {
    const userRole = this.loginService.getUserRole();
    if (userRole === 'ROLE_ADMIN') {
      return true;
    } else if (userRole === 'ROLE_USER') {
      this.router.navigate(['/unauthorized']);
      return false;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}