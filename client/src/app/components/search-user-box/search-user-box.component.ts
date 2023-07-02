import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import User from 'src/app/domains/entity/User';

@Component({
  selector: 'app-search-user-box',
  templateUrl: './search-user-box.component.html',
  styleUrls: ['./search-user-box.component.scss'],
})
export class SearchUserBoxComponent {
  @Input() user!: User;

  constructor(private router: Router) {}

  navigateToProfile() {
    this.router.navigate([`/profile/${this.user.id}`]);
  }
}
