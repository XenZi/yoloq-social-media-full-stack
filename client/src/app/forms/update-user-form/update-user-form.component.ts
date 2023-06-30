import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import User from 'src/app/domains/entity/User';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-update-user-form',
  templateUrl: './update-user-form.component.html',
  styleUrls: ['./update-user-form.component.scss'],
})
export class UpdateUserFormComponent {
  active: number = 1;
  loggedUser!: User;
  updateForm: FormGroup;
  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {
    this.updateForm = this.formBuilder.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.loggedUser = this.userService.getUser() as User;
    this.updateForm = this.formBuilder.group({
      username: [this.loggedUser.username],
      firstName: [this.loggedUser.firstName],
      lastName: [this.loggedUser.lastName],
    });
  }

  changeActiveForm(newActive: number) {
    this.active = newActive;
  }

  submit() {
    const enteredUsername = this.updateForm.get('username')?.value;
    const enteredFirstName = this.updateForm.get('firstName')?.value;
    const enteredLastName = this.updateForm.get('lastName')?.value;

    this.userService.updateUser(
      this.loggedUser.id as number,
      enteredUsername,
      enteredFirstName,
      enteredLastName
    );
  }
}
