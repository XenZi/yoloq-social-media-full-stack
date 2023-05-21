import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import Group from 'src/app/models/entity/Group';
import { GroupService } from 'src/app/services/group/group.service';
import { ModalService } from 'src/app/services/modal/modal.service';

@Component({
  selector: 'app-vertical-nav',
  templateUrl: './vertical-nav.component.html',
  styleUrls: ['./vertical-nav.component.scss'],
})
export class VerticalNavComponent {
  groups!: Observable<Group[]>;
  clickedInputNumber: number = -1;

  constructor(
    private modalService: ModalService,
    private groupService: GroupService
  ) {}

  openModalForCreatingGroup() {
    this.clickedInputNumber = 3;
    this.modalService.openModal();
  }

  ngOnInit() {
    this.groups = this.groupService.getAll();
  }

  openModalForChangePassword() {
    this.clickedInputNumber = 6;
    this.modalService.openModal();
  }
}
