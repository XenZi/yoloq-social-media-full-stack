import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from '../localstorage/local-storage.service';

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
}
