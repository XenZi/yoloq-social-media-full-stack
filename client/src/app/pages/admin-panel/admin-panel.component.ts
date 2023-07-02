import { Component } from '@angular/core';
import Report from 'src/app/domains/entity/Report';
import { ReportService } from 'src/app/services/report/report.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss'],
})
export class AdminPanelComponent {
  reports!: Report[];

  constructor(private reportService: ReportService) {}

  ngOnInit() {
    this.reportService.getAllReports().subscribe((data) => {
      this.reports = data;
    });
  }
}
