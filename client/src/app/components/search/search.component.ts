import { Component } from '@angular/core';
import User from 'src/app/domains/entity/User';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent {
  users: User[] = [];

  constructor(private userService: UserService) {}

  onInputChange(event: any) {
    const value = event.target.value;
    const names = value.split(' ');
    if (value == '') {
      this.users = [];
      return;
    }
    const firstName = names[0];
    const lastName = names.length > 1 ? names[1] : '';

    this.userService.search(firstName, lastName).subscribe((users) => {
      this.users = users;
    });
  }
}
