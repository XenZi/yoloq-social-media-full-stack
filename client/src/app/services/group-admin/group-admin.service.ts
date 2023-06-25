import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupAdmin } from 'src/app/domains/entity/GroupAdmin';

@Injectable({
  providedIn: 'root',
})
export class GroupAdminService {
  private baseURL: string = 'http://localhost:8080/api/group-admin';
  private groupBaseURL: string = 'http://localhost:8080/api/groups';
  constructor(private http: HttpClient) {}

  getAllAdminsForGroup(id: number): Observable<GroupAdmin[]> {
    return this.http.get<GroupAdmin[]>(`${this.baseURL}/${id}`);
  }

  createAdmin(groupID: number, userID: number) {
    this.http.post<GroupAdmin>(`${this.groupBaseURL}/admin`, {
      groupID,
      userID,
    });
  }

  deleteAdmin(adminID: number) {
    this.http.delete<GroupAdmin>(`
    ${this.groupBaseURL}/admin/${adminID}`);
  }
}
