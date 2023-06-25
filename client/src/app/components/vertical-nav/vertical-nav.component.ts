import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import Group from 'src/app/domains/entity/Group';
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
    private groupService: GroupService,
    private modalService: ModalService
  ) {}

  ngOnInit() {
    this.groups = this.groupService.getAll();
  }

  createGroup() {}
}
