import { Component, Input } from '@angular/core';
import Group from 'src/app/domains/entity/Group';
import { GroupService } from 'src/app/services/group/group.service';

@Component({
  selector: 'app-group-vertical-nav-item',
  templateUrl: './group-vertical-nav-item.component.html',
  styleUrls: ['./group-vertical-nav-item.component.scss'],
})
export class GroupVerticalNavItemComponent {
  @Input() group!: Group;
  clickedInputNumber: number = -1;

  constructor(private groupService: GroupService) {}

  deleteGroup() {
    this.groupService.deleteGroup(this.group.id);
  }
}
