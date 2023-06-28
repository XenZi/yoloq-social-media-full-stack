import { Component, Input } from '@angular/core';
import { GroupAdmin } from 'src/app/domains/entity/GroupAdmin';
import User from 'src/app/domains/entity/User';
import OptionsItem from 'src/app/domains/model/OptionsItem';

@Component({
  selector: 'app-show-user-in-group',
  templateUrl: './show-user-in-group.component.html',
  styleUrls: ['./show-user-in-group.component.scss'],
})
export class ShowUserInGroupComponent {
  @Input() user!: User;
  @Input() groupAdmin: GroupAdmin | undefined;
  @Input() isOptionsButtonVisible!: boolean;
  optionItems!: OptionsItem[];
  constructor() {}

  showOptions() {
    this.optionItems = [
      {
        icon: 'fa-solid fa-user-minus',
        text: 'Remove user from group',
        onClick: () => {},
      },

      {
        icon: 'fa-solid fa-plus',
        text: 'Block user from group',
        onClick: () => {},
      },
    ];
    if (this.groupAdmin) {
    }
  }
}
