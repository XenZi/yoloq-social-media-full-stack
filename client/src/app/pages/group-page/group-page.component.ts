import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { last } from 'rxjs';
import { GroupAdmin } from 'src/app/domains/entity/GroupAdmin';
import User from 'src/app/domains/entity/User';
import { GroupAdminService } from 'src/app/services/group-admin/group-admin.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-group-page',
  templateUrl: './group-page.component.html',
  styleUrls: ['./group-page.component.scss'],
})
export class GroupPageComponent {
  activeNumber: number = 2;
  groupID: number | undefined;
  groupAdmins: GroupAdmin[] | undefined;
  loggedUser!: User | null;
  isUserAdminOfGroup: boolean = false;
  constructor(
    private route: ActivatedRoute,
    private groupAdminService: GroupAdminService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.loggedUser = this.userService.getUser();

    this.route.paramMap.subscribe((params) => {
      this.groupID = Number(params.get('id'));
    });
    this.groupAdminService
      .getAllAdminsForGroup(this.groupID!)
      .subscribe((data) => {
        this.groupAdmins = data;
        data.forEach((admin) => {
          if (admin.user.id == this.loggedUser?.id) {
            this.isUserAdminOfGroup = true;
          }
        });
      });
  }

  changeNumber(newActive: number) {
    this.activeNumber = newActive;
  }
}
