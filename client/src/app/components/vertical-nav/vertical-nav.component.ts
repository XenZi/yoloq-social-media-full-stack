import { group } from '@angular/animations';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import Group from 'src/app/domains/entity/Group';
import { CreateGroupFormComponent } from 'src/app/forms/create-group-form/create-group-form.component';
import { GroupService } from 'src/app/services/group/group.service';
import { ModalService } from 'src/app/services/modal/modal.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-vertical-nav',
  templateUrl: './vertical-nav.component.html',
  styleUrls: ['./vertical-nav.component.scss'],
})
export class VerticalNavComponent {
  groups!: Group[];
  clickedInputNumber: number = -1;

  constructor(
    private groupService: GroupService,
    private modalService: ModalService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.groupService
      .getAllGroupsForUser(this.userService.getUser()?.id as number)
      .subscribe((data) => {
        this.groups = data;
      });
  }

  createGroup() {
    this.modalService.open(CreateGroupFormComponent, {});
  }

  navigateToTheGroup(groupID: number) {
    this.router.navigate([`/group/${groupID}`]);
  }
}
