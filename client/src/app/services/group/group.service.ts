import { Injectable } from '@angular/core';
import { LocalStorageService } from '../localstorage/local-storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import Group from 'src/app/domains/entity/Group';
import { GroupMember } from 'src/app/domains/entity/GroupMember';
import { GroupAdmin } from 'src/app/domains/entity/GroupAdmin';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  private baseURL: string = 'http://localhost:8080/api/groups';
  private headers: HttpHeaders = new HttpHeaders({
    Authorization: `Bearer ${this.localStorageService.getItem('token')}`,
  });
  private groupsChanged = new Subject<void>();
  constructor(
    private localStorageService: LocalStorageService,
    private http: HttpClient
  ) {}

  public createGroup(name: string, description: string): void {
    this.http
      .post<any>(
        `${this.baseURL}`,
        { name, description },
        {
          headers: this.headers,
        }
      )
      .subscribe({
        next: (res) => {
          this.groupsChanged.next();
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  public updateGroup(name: string, description: string, id: number): void {
    this.http
      .put<any>(
        `${this.baseURL}/${id}`,
        {
          id,
          name,
          description,
        },
        {
          headers: this.headers,
        }
      )
      .subscribe({
        next: (res) => {
          this.groupsChanged.next();
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public deleteGroup(id: number): void {
    this.http
      .delete<any>(`${this.baseURL}/${id}`, {
        headers: this.headers,
      })
      .subscribe({
        next: (res) => {
          this.groupsChanged.next();
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public getAll(): Observable<Group[]> {
    return this.http.get<Group[]>(this.baseURL, {
      headers: this.headers,
    });
  }

  public getAllMembersInGroup(groupID: number): Observable<GroupMember[]> {
    return this.http.get<GroupMember[]>(`${this.baseURL}/members/${groupID}`);
  }

  public getOne(id: number): Observable<Group> {
    return this.http.get<Group>(`${this.baseURL}/${id}`);
  }

  public joinGroup(groupID: number): void {
    this.http
      .post<GroupMember>(`${this.baseURL}/join/${groupID}`, {})
      .subscribe({
        next: (res) => {
          this.groupsChanged.next();
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public getAllPendingMembersInGroup(
    groupID: number
  ): Observable<GroupMember[]> {
    return this.http.get<GroupMember[]>(
      `${this.baseURL}/members/pending/${groupID}`
    );
  }

  public updateGroupJoin(
    groupID: number,
    requestID: number,
    decision: boolean
  ): Observable<GroupMember> {
    return this.http.post<GroupMember>(
      `${this.baseURL}/members/update-join-request`,
      {
        groupID,
        requestID,
        decision,
      }
    );
  }

  public suspendGroup(groupID: number, suspendedReason: string) {
    return this.http
      .post<Group>(`${this.baseURL}/suspend`, {
        groupID,
        suspendedReason,
      })
      .subscribe({
        next: (res) => {
          this.groupsChanged.next();
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  public removeAdmin(adminID: number) {
    return this.http.delete(`${this.baseURL}/admin/${adminID}`).subscribe({
      next: (res) => {
        this.groupsChanged.next();
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  public getAllGroupsForUser(userID: number): Observable<Group[]> {
    return this.http.get<Group[]>(`${this.baseURL}/user/${userID}`);
  }

  public getGroupsChangedObservable(): Observable<void> {
    return this.groupsChanged.asObservable();
  }
}
