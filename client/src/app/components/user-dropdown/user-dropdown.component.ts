import { Component } from '@angular/core';
import { Router } from '@angular/router';
import User from 'src/app/domains/entity/User';
import { UpdateUserFormComponent } from 'src/app/forms/update-user-form/update-user-form.component';
import { LocalStorageService } from 'src/app/services/localstorage/local-storage.service';
import { ModalService } from 'src/app/services/modal/modal.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user-dropdown',
  templateUrl: './user-dropdown.component.html',
  styleUrls: ['./user-dropdown.component.scss'],
})
export class UserDropdownComponent {
  user!: User;
  isVisible: boolean = false;
  constructor(
    private userService: UserService,
    private modalService: ModalService,
    private router: Router,
    private localStorageService: LocalStorageService
  ) {}

  ngOnInit() {
    this.user = this.userService.getUser() as User;
  }

  changeVisibleDropdown() {
    this.isVisible = !this.isVisible;
  }
  goToProfilePage() {
    this.router.navigate([`/profile/${this.user.id}`]);
  }

  openUserUpdateModal() {
    this.modalService.open(UpdateUserFormComponent, {});
  }

  logout() {
    this.localStorageService.clear();
    this.router.navigate(['/']);
  }
}
