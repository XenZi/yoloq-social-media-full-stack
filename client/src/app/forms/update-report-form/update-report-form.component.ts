import { Component, Input } from '@angular/core';
import Comment from 'src/app/domains/entity/Comment';
import Report from 'src/app/domains/entity/Report';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-update-report-form',
  templateUrl: './update-report-form.component.html',
  styleUrls: ['./update-report-form.component.scss'],
})
export class UpdateReportFormComponent {
  @Input() report!: Report;
  valueToShow!: string;

  constructor(private reportService: ReportService) {}

  ngOnInit() {
    console.log(this.report);
    if (this.report.reportedPost != null) {
      this.valueToShow = this.report.reportedPost.content;
    } else {
      this.valueToShow = this.report.reportedComment?.text as string;
    }
  }

  reject() {
    this.reportService.updateReport(false, this.report.id);
  }

  approve() {
    this.reportService.updateReport(true, this.report.id);
  }
}
