import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from '../localstorage/local-storage.service';
import { Observable } from 'rxjs';
import { Post } from 'src/app/domains/entity/Post';
import { FormArray } from '@angular/forms';

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

  private formateFormData(
    content: string,
    files: FormArray,
    postedInGroupID: number | null
  ): FormData {
    let formData: FormData = new FormData();
    formData.append('content', content);
    if (postedInGroupID != null) {
      formData.append('postedInGroupID', postedInGroupID as unknown as string);
    }
    if (Array.from(files as unknown as Array<any>).length !== 0) {
      Array.from(files as unknown as Array<any>).forEach((file) => {
        formData.append('images', file);
      });
    }
    return formData;
  }

  private formateFormDataFromPostEntity(post: Post, files: FormArray) {
    let formData: FormData = new FormData();
    formData.append('content', post.content);
    post.imagePaths.forEach((path) => formData.append('imagePaths', path));
    if (Array.from(files as unknown as Array<any>).length !== 0) {
      Array.from(files as unknown as Array<any>).forEach((file) => {
        formData.append('images', file);
      });
    }
    return formData;
  }
  public createPost(
    content: string,
    files: FormArray,
    postedInGroupID: number | null = null
  ): void {
    this.http
      .post<any>(
        `${this.baseURL}`,
        this.formateFormData(content, files, postedInGroupID),
        {
          headers: this.constructHttpHeaders(),
        }
      )
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

  public updatePost(post: Post, files: FormArray) {
    this.http
      .put<any>(
        `${this.baseURL}/${post.id}`,
        this.formateFormDataFromPostEntity(post, files),
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

  public getAllPostsForGroup(groupID: number): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.baseURL}/group/${groupID}`);
  }
}
