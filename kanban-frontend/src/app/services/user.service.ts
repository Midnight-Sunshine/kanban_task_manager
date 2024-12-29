import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>('http://localhost:91/Allusers');
  }
  private baseUrl = 'http://localhost:91/api/users';
  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}?username=${username}`);
  }

}
