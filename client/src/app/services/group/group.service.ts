import { Injectable } from '@angular/core';
import { LocalStorageService } from '../localstorage/local-storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import Group from 'src/app/domains/entity/Group';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  private baseURL: string = 'http://localhost:8080/api/groups';
  private headers: HttpHeaders = new HttpHeaders({
    Authorization: `Bearer ${this.localStorageService.getItem('token')}`,
  });
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
          console.log(res);
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
          console.log(res);
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
          console.log(res);
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
}
