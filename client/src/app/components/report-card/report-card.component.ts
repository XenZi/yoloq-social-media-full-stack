import { Component, Input } from '@angular/core';
import Report from 'src/app/domains/entity/Report';
import { UpdateReportFormComponent } from 'src/app/forms/update-report-form/update-report-form.component';
import { ModalService } from 'src/app/services/modal/modal.service';

@Component({
  selector: 'app-report-card',
  templateUrl: './report-card.component.html',
  styleUrls: ['./report-card.component.scss'],
})
export class ReportCardComponent {
  @Input() report!: Report;
  constructor(private modalService: ModalService) {}

  openModal() {
    this.modalService.open(UpdateReportFormComponent, {
      report: this.report,
    });
  }
}
