import { Component, Input } from '@angular/core';
import { GroupMember } from 'src/app/domains/entity/GroupMember';
import User from 'src/app/domains/entity/User';
import { GroupService } from 'src/app/services/group/group.service';

@Component({
  selector: 'app-user-approve-group-join',
  templateUrl: './user-approve-group-join.component.html',
  styleUrls: ['./user-approve-group-join.component.scss'],
})
export class UserApproveGroupJoinComponent {
  @Input() user!: User;
  @Input() groupMember!: GroupMember;
  constructor(private groupService: GroupService) {}

  approveUserJoinGroup() {
    console.log('test');

    this.groupService
      .updateGroupJoin(this.groupMember.forGroup.id, this.groupMember.id, true)
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  declineUserJoinGroup() {
    this.groupService.updateGroupJoin(
      this.groupMember.forGroup.id,
      this.groupMember.id,
      false
    );
  }
}
