import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sprint } from '../models/Sprint';

@Injectable({
  providedIn: 'root'
})
export class SprintService {
  private baseUrl = 'http://localhost:91/api';

  constructor(private http: HttpClient) { }

  getAllSprints(): Observable<Sprint[]> {
    return this.http.get<Sprint[]>(`${this.baseUrl}/sprints`);
  }

  addSprint(sprint: Sprint): Observable<any>{
    return this.http.post<any>('http://localhost:91/api/sprints', sprint);
  }

  deleteSprint(sprintId: number): Observable<void> {
    return this.http.delete<any>(`${this.baseUrl}/sprintDeleted/${sprintId}`, { responseType: 'text' as 'json' });
  }
}
