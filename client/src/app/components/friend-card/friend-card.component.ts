import { Component, Input } from '@angular/core';
import User from 'src/app/domains/entity/User';

@Component({
  selector: 'app-friend-card',
  templateUrl: './friend-card.component.html',
  styleUrls: ['./friend-card.component.scss'],
})
export class FriendCardComponent {
  @Input() user!: User;
}
