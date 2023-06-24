import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Reaction from 'src/app/domains/entity/Reaction';
import { ReactionType } from 'src/app/domains/enums/ReactionType';

@Injectable({
  providedIn: 'root',
})
export class ReactionService {
  private baseURL: string = 'http://localhost:8080/api/reactions';
  constructor(private http: HttpClient) {}

  public getAll(): Observable<Reaction[]> {
    return this.http.get<Reaction[]>(this.baseURL);
  }

  public getAllReactionsForComment(commentID: number): Observable<Reaction[]> {
    return this.http.get<Reaction[]>(`${this.baseURL}/comment/${commentID}`);
  }

  public createReactionForPost(type: ReactionType, postIdReactedTo: number) {
    this.http
      .post<Reaction>(this.baseURL, { type, postIdReactedTo })
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public createReactionForComment(
    type: ReactionType,
    commentIdReactedTo: number
  ) {
    this.http
      .post<Reaction>(this.baseURL, {
        type,
        commentIdReactedTo,
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

  public getAllReactionsForPost(id: number): Observable<Reaction[]> {
    return this.http.get<Reaction[]>(`${this.baseURL}/post/${id}`);
  }

  public deleteReaction(id: number) {
    this.http.delete<Reaction>(`${this.baseURL}/${id}`).subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  public updateReaction(id: number, type: ReactionType) {
    this.http
      .put<Reaction>(`${this.baseURL}/${id}`, {
        id,
        type,
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
}
