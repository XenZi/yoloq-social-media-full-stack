import { Component } from '@angular/core';
import { GroupService } from 'src/app/services/group/group.service';
import { ModalService } from 'src/app/services/modal/modal.service';

@Component({
  selector: 'app-vertical-nav',
  templateUrl: './vertical-nav.component.html',
  styleUrls: ['./vertical-nav.component.scss'],
})
export class VerticalNavComponent {
  constructor(
    private modalService: ModalService,
    private groupService: GroupService
  ) {}
}
