import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FriendRequest } from 'src/app/domains/entity/FriendRequest';

@Injectable({
  providedIn: 'root',
})
export class FriendRequestService {
  private baseURL: string = 'http://localhost:8080/api/friend-request';
  constructor(private http: HttpClient) {}

  public addToFriend(userToID: number): Observable<FriendRequest> {
    return this.http.post<FriendRequest>(`${this.baseURL}`, {
      userToID,
    });
  }

  public getAllPendingRequest(): Observable<FriendRequest[]> {
    return this.http.get<FriendRequest[]>(`${this.baseURL}/pending`);
  }

  public updatePendingRequest(
    requestID: number,
    decision: boolean
  ): Observable<FriendRequest> {
    return this.http.put<FriendRequest>(`${this.baseURL}/${requestID}`, {
      requestID,
      decision,
    });
  }
}
