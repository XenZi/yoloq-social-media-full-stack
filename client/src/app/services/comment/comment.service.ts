import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Comment from 'src/app/domains/entity/Comment';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseURL: string = 'http://localhost:8080/api/comments';
  private comments!: Observable<Comment[]>;
  constructor(private http: HttpClient) {}

  public createComment(text: string, postId: number): void {
    this.http
      .post<Comment>(`${this.baseURL}`, {
        text,
        postId,
      })
      .subscribe({
        next: (response) => {
          this.getAllCommentsForPost(postId);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public getAllCommentsForPost(postID: number): Observable<Comment[]> {
    this.comments = this.http.get<Comment[]>(`${this.baseURL}/post/${postID}`);
    return this.comments;
  }

  public updateComment(comment: Comment) {
    this.http
      .put<Comment>(`${this.baseURL}/${comment.id}`, {
        ...comment,
      })
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public createReplyOnComment(text: string, parentCommentID: number): void {
    this.http
      .post<Comment>(`${this.baseURL}`, {
        text,
        parentCommentID,
      })
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public deleteComment(id: number): void {
    this.http.delete<Comment>(`${this.baseURL}/${id}`).subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
