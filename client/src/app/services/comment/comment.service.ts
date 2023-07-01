import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import Comment from 'src/app/domains/entity/Comment';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseURL: string = 'http://localhost:8080/api/comments';
  private commentsChanged = new Subject<number>(); // change to number
  private repliesChanged = new Subject<number>();

  constructor(private http: HttpClient) {}

  public createComment(text: string, postId: number): void {
    this.http
      .post<Comment>(`${this.baseURL}`, {
        text,
        postId,
      })
      .subscribe({
        next: (response) => {
          this.commentsChanged.next(postId);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public getAllCommentsForPost(postID: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseURL}/post/${postID}`);
  }

  public updateComment(comment: Comment) {
    this.http
      .put<Comment>(`${this.baseURL}/${comment.id}`, {
        ...comment,
      })
      .subscribe({
        next: (res) => {
          console.log(res);
          this.commentsChanged.next(comment.postId);
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
          this.repliesChanged.next(parentCommentID);
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
        this.commentsChanged.next(id);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  public getRepliesForComment(commentId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseURL}/${commentId}/replies`);
  }

  public getCommentsChangedObservable(): Observable<number> {
    return this.commentsChanged.asObservable();
  }

  public getRepliesChangedObservable(): Observable<number> {
    return this.repliesChanged.asObservable();
  }
}
