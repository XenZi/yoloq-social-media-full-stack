import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import User from 'src/app/domains/entity/User';
import { LocalStorageService } from '../localstorage/local-storage.service';
import { ModalService } from '../modal/modal.service';
import { ToastService } from '../toast/toast.service';
import { ToastNotificationType } from 'src/app/domains/enums/ToastNotificationType';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseURL: string = 'http://localhost:8080/api/users';

  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService,
    private modalService: ModalService,
    private toastService: ToastService,
    private router: Router
  ) {}

  public fetchUserDetailsAfterLogin(): void {
    this.http
      .get<User>(`${this.baseURL}/whoami`, {
        headers: {
          Authorization: `Bearer ${this.localStorage.getItem('token')}`,
        },
      })
      .subscribe({
        next: (res) => {
          this.localStorage.setItem('user', JSON.stringify(res));
        },
      });
  }

  public getUser(): User | null {
    let savedLocally = this.localStorage.getItem('user');
    if (savedLocally == null) {
      return null;
    }

    return JSON.parse(savedLocally) as User;
  }

  public updateUser(
    id: number,
    username: string,
    firstName: string,
    lastName: string
  ) {
    this.http
      .put(`${this.baseURL}/${id}`, {
        id,
        username,
        firstName,
        lastName,
      })
      .subscribe({
        next: (res) => {
          this.modalService.close();
          this.toastService.showToast(
            'Updated',
            "You've successfully updated your information",
            ToastNotificationType.Success
          );
          this.localStorage.clear();
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.toastService.showToast(
            'Error',
            err.error.message,
            ToastNotificationType.Error
          );
        },
      });
  }

  public getUserDetails(userID: number): Observable<User> {
    return this.http.get<User>(`${this.baseURL}/${userID}`);
  }

  public getAllUserFriends(userID: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseURL}/friends/${userID}`);
  }

  public removeFriend(friendID: number): Observable<User> {
    return this.http.delete<User>(`${this.baseURL}/friends/${friendID}`);
  }

  public search(firstName: string, lastName: string): Observable<User[]> {
    return this.http.get<User[]>(
      `${this.baseURL}/search?firstName=${firstName}&lastName=${lastName}`
    );
  }

  public uploadImage(form: FormData) {
    this.http.put<User>(`${this.baseURL}/profile-image`, form).subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
