import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { tap } from "rxjs/operators";
import { AuthenticationResponse } from "../models/AuthResponse";
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private readonly URL: string = 'http://localhost:91';
  private readonly USER_ROLE_KEY = 'userRole';
  private readonly USER_ID_KEY = 'userId';

  constructor(private http: HttpClient, private router : Router) { }

  authenticate(username: string, password: string) {
    return this.http.post<AuthenticationResponse>(`${this.URL}/authenticate`, 
    { username: username, password: password })
      .pipe(tap(response => {
        sessionStorage.setItem(this.USER_ROLE_KEY, response.user.roles);
        sessionStorage.setItem(this.USER_ID_KEY, response.user.id.toString());
      }));
  }
  register(username: string, email: string, password: string): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.URL}/register`, { username, email, password });
  }
  logout() {
    sessionStorage.clear();
    this.router.navigate(['/home']);
  }
  getUserRole(): string {
    return sessionStorage.getItem(this.USER_ROLE_KEY) || '';
  }
  getUserId(): number | null {
    const userId = sessionStorage.getItem(this.USER_ID_KEY);
    return userId ? parseInt(userId, 10) : null;
  }
}
