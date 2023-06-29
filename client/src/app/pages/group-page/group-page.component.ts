import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { last } from 'rxjs';
import Group from 'src/app/domains/entity/Group';
import { GroupAdmin } from 'src/app/domains/entity/GroupAdmin';
import { GroupMember } from 'src/app/domains/entity/GroupMember';
import { Post } from 'src/app/domains/entity/Post';
import User from 'src/app/domains/entity/User';
import OptionsItem from 'src/app/domains/model/OptionsItem';
import { SuspendGroupFormComponent } from 'src/app/forms/suspend-group-form/suspend-group-form.component';
import { UpdateGroupFormComponent } from 'src/app/forms/update-group-form/update-group-form.component';
import { GroupAdminService } from 'src/app/services/group-admin/group-admin.service';
import { GroupService } from 'src/app/services/group/group.service';
import { ModalService } from 'src/app/services/modal/modal.service';
import { PostService } from 'src/app/services/post/post.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-group-page',
  templateUrl: './group-page.component.html',
  styleUrls: ['./group-page.component.scss'],
})
export class GroupPageComponent {
  activeNumber: number = 0;

  groupID: number | undefined;
  groupAdmins: GroupAdmin[] | undefined;
  groupMembers!: GroupMember[];
  groupPendingRequests!: GroupMember[];
  groupPosts!: Post[];

  adminOptionItems: OptionsItem[] = [];
  groupAdminUserItems: OptionsItem[] = [];

  isAdminOptionVisible: boolean = false;
  isAdminOptionForUserVisible: boolean = false;

  loggedUser!: User | null;
  isUserAdminOfGroup: boolean = false;
  group!: Group;
  isUserInGroup: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private groupAdminService: GroupAdminService,
    private userService: UserService,
    private groupService: GroupService,
    private modalService: ModalService,
    private postService: PostService
  ) {}

  ngOnInit() {
    this.loggedUser = this.userService.getUser();
    this.getGroupID();
    this.getAllPosts();
    this.getGroupData();
    this.getAllGroupAdmins();
    this.getAllPendingRequests();
    this.getAllMembers();
    this.formatPageOptionsForGroupAdmin();
    this.formatUserOptionsForGroupAdmin();
  }

  changeNumber(newActive: number) {
    this.activeNumber = newActive;
  }

  getGroupID() {
    this.route.paramMap.subscribe((params) => {
      this.groupID = Number(params.get('id'));
    });
  }

  getAllPosts() {
    this.postService
      .getAllPostsForGroup(this.groupID as number)
      .subscribe((data) => {
        this.groupPosts = data;
      });
  }
  getGroupData() {
    this.groupService.getOne(this.groupID as number).subscribe((data) => {
      this.group = data;
    });
  }

  getAllGroupAdmins() {
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

  getAllPendingRequests() {
    this.groupService
      .getAllPendingMembersInGroup(this.groupID as number)
      .subscribe((data) => {
        this.groupPendingRequests = data;
      });
  }
  getAllMembers() {
    this.groupService
      .getAllMembersInGroup(this.groupID as number)
      .subscribe((data) => {
        this.groupMembers = data;
        this.groupMembers.map((member) => {
          if (member.requestFrom.id === this.loggedUser?.id) {
            this.isUserInGroup = true;
          }
        });
      });
  }
  formatPageOptionsForGroupAdmin() {
    if (this.isUserAdminOfGroup) {
      this.adminOptionItems = [
        {
          icon: 'fa-solid fa-pen-to-square',
          text: 'Edit group',
          onClick: () => {
            this.updateGroup();
          },
        },
      ];
    }
    if (this.loggedUser?.role == 'ADMIN') {
      this.adminOptionItems.push({
        icon: 'fa-solid fa-x',
        text: 'Suspend group',
        onClick: () => {
          this.suspendGroup();
        },
      });
      this.adminOptionItems.push({
        icon: 'fa-solid fa-pen-to-square',
        text: 'Edit group',
        onClick: () => {
          this.updateGroup();
        },
      });
    }
  }

  formatUserOptionsForGroupAdmin() {
    if (this.isUserAdminOfGroup) {
      this.groupAdminUserItems = [
        {
          icon: 'fa-solid fa-x',
          text: 'Ban user',
          onClick: () => {
            console.log('ban user');
          },
        },
      ];
    }

    if (this.loggedUser?.role == 'ADMIN') {
      this.groupAdminUserItems = [
        {
          icon: 'fa-solid fa-x',
          text: 'Remove admin',
          onClick: () => {
            console.log('Remove admin');
          },
        },
      ];
    }
  }
  groupButtonOperation() {
    if (this.isUserAdminOfGroup || this.isUserInGroup) {
      return;
    }
    console.log('ddd');

    this.groupService.joinGroup(this.groupID as number).subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  suspendGroup() {
    this.modalService.open(SuspendGroupFormComponent, {
      group: this.group,
    });
  }

  toggleAdminOptionListVisible() {
    this.isAdminOptionVisible = !this.isAdminOptionVisible;
  }

  toggleAdminOptionForUserVisible() {
    this.isAdminOptionForUserVisible = !this.isAdminOptionForUserVisible;
  }

  updateGroup() {
    this.modalService.open(UpdateGroupFormComponent, { group: this.group });
  }
}
