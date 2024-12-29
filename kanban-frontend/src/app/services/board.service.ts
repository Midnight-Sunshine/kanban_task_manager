import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Board } from '../models/Board';
import { BoardDTO } from '../models/BoardDTO';

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  private baseUrl = 'http://localhost:91/api/boards';

  constructor(private http: HttpClient) {}

  getAllBoards(): Observable<Board[]> {
    return this.http.get<Board[]>(this.baseUrl);
  }

  addBoard(board: BoardDTO): Observable<any> {
    return this.http.post<any>(this.baseUrl, board);
  }

  deleteBoard(boardId: number): Observable<any> {
    const url = `http://localhost:91/api/boardsDeleted/${boardId}`;
    return this.http.delete<any>(url);
  }
}
