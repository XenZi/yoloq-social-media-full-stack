import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from '../localstorage/local-storage.service';
import { Observable } from 'rxjs';
import { Post } from 'src/app/models/entity/Post';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private baseURL: string = 'http://localhost:8080/api/posts';

  constructor(
    private localStorageService: LocalStorageService,
    private http: HttpClient
  ) {}

  private constructHttpHeaders(): HttpHeaders {
    const token = this.localStorageService.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  private formateFormData(content: string): FormData {
    let formData: FormData = new FormData();
    formData.append('content', content);
    return formData;
  }

  public createPost(content: string): void {
    console.log(content);

    this.http
      .post<any>(`${this.baseURL}`, this.formateFormData(content), {
        headers: this.constructHttpHeaders(),
      })
      .subscribe({
        next: (response) => {
          console.log(response);
        },
        error: (error) => {
          console.error(error);
        },
      });
  }

  public getAll(): Observable<Post[]> {
    return this.http.get<Post[]>(this.baseURL, {
      headers: {
        Authorization: `Bearer ${this.localStorageService.getItem('token')}`,
      },
    });
  }

  public updatePost(id: number, content: string) {
    this.http
      .put<any>(
        `${this.baseURL}/${id}`,
        { id, content },
        {
          headers: {
            Authorization: `Bearer ${this.localStorageService.getItem(
              'token'
            )}`,
          },
        }
      )
      .subscribe({
        next: (response) => {
          console.log(response);
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  public deletePost(id: number): void {
    this.http
      .delete<any>(`${this.baseURL}/${id}`, {
        headers: {
          Authorization: `Bearer ${this.localStorageService.getItem('token')}`,
        },
      })
      .subscribe({
        next: (response) => {
          console.log(response);
        },
        error: (error) => {
          console.log(error);
        },
      });
  }
}
