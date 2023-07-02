import { Component, Input } from '@angular/core';
import { ReportReason } from 'src/app/domains/enums/ReportReason';
import { ToastNotificationType } from 'src/app/domains/enums/ToastNotificationType';
import { ReportService } from 'src/app/services/report/report.service';
import { ToastService } from 'src/app/services/toast/toast.service';

@Component({
  selector: 'app-report-form',
  templateUrl: './report-form.component.html',
  styleUrls: ['./report-form.component.scss'],
})
export class ReportFormComponent {
  @Input() commentID!: number;
  @Input() postID!: number;
  reportReasons: string[];
  clickedReason: number = -1;
  constructor(
    private reportService: ReportService,
    private toastService: ToastService
  ) {
    this.reportReasons = Object.keys(ReportReason)
      .filter((key) => isNaN(Number(key)))
      .map((key) => ReportReason[key as keyof typeof ReportReason]);
  }

  changeReason(newReasonIndex: number) {
    this.clickedReason = newReasonIndex;
  }

  submitReport() {
    if (this.clickedReason == -1) {
      this.toastService.showToast(
        'Error',
        'You must select something in order to report',
        ToastNotificationType.Error
      );
    }
    let reportReason: ReportReason = Object.keys(ReportReason).find(
      (key) =>
        ReportReason[key as keyof typeof ReportReason] ==
        this.reportReasons[this.clickedReason]
    ) as ReportReason;

    if (this.postID) {
      this.reportService.createReportForPost(this.postID, reportReason);
    } else {
      this.reportService.createReportForComment(this.commentID, reportReason);
    }
  }
}
