import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap, catchError, throwError, Observable, last } from 'rxjs';
import { LocalStorageService } from '../localstorage/local-storage.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseURL: string = 'http://localhost:8080/api/users';
  private headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  constructor(
    private http: HttpClient,
    private localStorageService: LocalStorageService,
    private router: Router
  ) {}

  public login(email: string, password: string): void {
    this.http
      .post<any>(
        `${this.baseURL}/login`,
        { email, password },
        { headers: this.headers }
      )
      .subscribe({
        next: (response) => {
          this.localStorageService.setItem('token', response.token);
          this.router.navigate(['/home']);
        },
        error: (error) => {
          console.error(error);
        },
      });
  }

  public register(
    username: string,
    password: string,
    email: string,
    firstName: string,
    lastName: string,
    profileImage: any
  ) {
    let formData: FormData = this.constructFormDataForRegister(
      username,
      password,
      email,
      firstName,
      lastName,
      profileImage
    );
    this.http.post<any>(`${this.baseURL}`, formData).subscribe({
      next: (response) => {
        this.router.navigate(['']);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  public logout(): void {
    this.localStorageService.removeItem('token');
    this.router.navigate(['']);
  }

  private constructFormDataForRegister(
    username: string,
    password: string,
    email: string,
    firstName: string,
    lastName: string,
    profileImage: any
  ): FormData {
    let formData: FormData = new FormData();
    formData.append('username', username);
    formData.append('password', password);
    formData.append('email', email);
    formData.append('firstName', firstName);
    formData.append('lastName', lastName);
    return formData;
  }

  public changePassword(
    oldPassword: string,
    newPassword: string,
    repeatedNewPassword: string
  ) {
    this.http
      .put<any>(
        `${this.baseURL}/change-password`,
        {
          oldPassword,
          newPassword,
          repeatedNewPassword,
        },
        {
          headers: {
            Authorization: `Bearer ${this.localStorageService.getItem(
              'token'
            )}`,
          },
        }
      )
      .subscribe({
        next: (res) => {
          this.localStorageService.removeItem('token');
          this.router.navigate(['']);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }
}
