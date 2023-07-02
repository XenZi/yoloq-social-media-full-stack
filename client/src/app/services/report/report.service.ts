import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReportReason } from 'src/app/domains/enums/ReportReason';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  private baseURL: string = 'http://localhost:8080/api/reports';
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
}
