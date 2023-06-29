import { Component, Input } from '@angular/core';
import { GroupAdmin } from 'src/app/domains/entity/GroupAdmin';
import User from 'src/app/domains/entity/User';
import OptionsItem from 'src/app/domains/model/OptionsItem';
import { GroupService } from 'src/app/services/group/group.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-show-user-in-group',
  templateUrl: './show-user-in-group.component.html',
  styleUrls: ['./show-user-in-group.component.scss'],
})
export class ShowUserInGroupComponent {
  @Input() user!: User;
  @Input() groupAdmin: GroupAdmin | undefined;
  @Input() isOptionsButtonVisible!: boolean;
  loggedUser!: User;
  optionItems: OptionsItem[] = [];
  isOptionItemsVisible: boolean = false;

  constructor(
    private userService: UserService,
    private groupService: GroupService
  ) {}

  setOptionItemsVisible() {
    console.log(this.optionItems);

    this.isOptionItemsVisible = !this.isOptionItemsVisible;
  }

  ngOnInit() {
    this.loggedUser = this.userService.getUser() as User;
    this.showOptions();
  }
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
      if (this.loggedUser.role == 'ADMIN') {
        this.optionItems.push({
          icon: 'fa-solid fa-user-minus',
          text: 'Remove from admin',
          onClick: () => {
            this.groupService.removeAdmin(this.groupAdmin?.id as number);
          },
        });
      }
    }
  }
}
