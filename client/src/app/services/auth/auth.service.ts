import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap, catchError, throwError, Observable, last } from 'rxjs';
import { LocalStorageService } from '../localstorage/local-storage.service';
import { Router } from '@angular/router';
import { ToastService } from '../toast/toast.service';
import { ToastNotificationType } from 'src/app/domains/enums/ToastNotificationType';
import { UserService } from '../user/user.service';
import { ModalService } from '../modal/modal.service';

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
    private router: Router,
    private toastService: ToastService,
    private userService: UserService,
    private modalService: ModalService
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
          this.toastService.showToast(
            'Login info',
            'You successfuly logged in',
            ToastNotificationType.Success
          );
          this.userService.fetchUserDetailsAfterLogin();
          setTimeout(() => {
            this.router.navigate(['/home']);
          }, 1000);
        },
        error: (error) => {
          console.error(error);
          this.toastService.showToast(
            'Login info',
            error.error.message,
            ToastNotificationType.Error
          );
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
          this.localStorageService.clear();
          this.modalService.close();
          this.toastService.showToast(
            'Successfully updated password',
            'You have successfully updated password',
            ToastNotificationType.Success
          );
          this.router.navigate(['']);
        },
        error: (err) => {
          console.log(err);
          this.toastService.showToast(
            'Error',
            err.error.message,
            ToastNotificationType.Error
          );
        },
      });
  }
}
