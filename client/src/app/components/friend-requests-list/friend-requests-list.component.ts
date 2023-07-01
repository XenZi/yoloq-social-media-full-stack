import { Component } from '@angular/core';
import { FriendRequest } from 'src/app/domains/entity/FriendRequest';
import { FriendRequestService } from 'src/app/services/friend-request/friend-request.service';

@Component({
  selector: 'app-friend-requests-list',
  templateUrl: './friend-requests-list.component.html',
  styleUrls: ['./friend-requests-list.component.scss'],
})
export class FriendRequestsListComponent {
  requests!: FriendRequest[];
  constructor(private friendRequestService: FriendRequestService) {}

  ngOnInit() {
    this.friendRequestService.getAllPendingRequest().subscribe((data) => {
      console.log(data);
      this.requests = data;
    });
  }
}
