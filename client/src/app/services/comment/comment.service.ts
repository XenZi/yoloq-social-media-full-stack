import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseURL: string = 'http://localhost:8080/api/comments';
  constructor(private http: HttpClient) {}

  public createComment(text: string, postId: number): void {
    this.http
      .post<Comment>(`${this.baseURL}`, {
        text,
        postId,
      })
      .subscribe({
        next: (response) => {
          console.log(response);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }
}
