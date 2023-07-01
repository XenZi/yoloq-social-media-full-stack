import { Component, Input } from '@angular/core';
import { FriendRequest } from 'src/app/domains/entity/FriendRequest';
import { FriendRequestService } from 'src/app/services/friend-request/friend-request.service';

@Component({
  selector: 'app-friend-request',
  templateUrl: './friend-request.component.html',
  styleUrls: ['./friend-request.component.scss'],
})
export class FriendRequestComponent {
  @Input() request!: FriendRequest;

  constructor(private friendRequestService: FriendRequestService) {}

  approveRequest() {
    this.friendRequestService
      .updatePendingRequest(this.request.id, true)
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  declineRequest() {
    console.log('test');
    this.friendRequestService
      .updatePendingRequest(this.request.id, false)
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }
}
