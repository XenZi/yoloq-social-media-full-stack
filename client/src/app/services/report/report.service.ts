import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import Report from 'src/app/domains/entity/Report';
import { ReportReason } from 'src/app/domains/enums/ReportReason';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  private baseURL: string = 'http://localhost:8080/api/reports';
  private reportsChanged = new Subject<void>();
  constructor(private http: HttpClient) {}

  createReportForPost(postID: number, reportReason: ReportReason) {
    this.http
      .post(`${this.baseURL}`, {
        postID,
        reportReason,
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

  createReportForComment(commentID: number, reportReason: ReportReason) {
    this.http.post(`${this.baseURL}`, {
      commentID,
      reportReason,
    });
  }

  getAllReports(): Observable<Report[]> {
    return this.http.get<Report[]>(`${this.baseURL}/admin`);
  }

  getAllReportsForGroup(id: number): Observable<Report[]> {
    return this.http.get<Report[]>(`${this.baseURL}/group/${id}`);
  }

  updateReport(decision: boolean, reportID: number) {
    this.http
      .put<Report>(`${this.baseURL}/${reportID}`, {
        reportID,
        decision,
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
}
