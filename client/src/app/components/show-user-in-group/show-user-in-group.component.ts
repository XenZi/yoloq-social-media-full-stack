import { Component, Input } from '@angular/core';
import { GroupAdmin } from 'src/app/domains/entity/GroupAdmin';
import User from 'src/app/domains/entity/User';

@Component({
  selector: 'app-show-user-in-group',
  templateUrl: './show-user-in-group.component.html',
  styleUrls: ['./show-user-in-group.component.scss'],
})
export class ShowUserInGroupComponent {
  @Input() user!: User;
  @Input() groupAdmin: GroupAdmin | undefined;
  @Input() isOptionsButtonVisible!: boolean;

  constructor() {}
}
