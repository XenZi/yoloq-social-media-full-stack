import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-change-password-form',
  templateUrl: './change-password-form.component.html',
  styleUrls: ['./change-password-form.component.scss'],
})
export class ChangePasswordFormComponent {
  changePasswordForm: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {
    this.changePasswordForm = this.formBuilder.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      repeatedNewPassword: ['', Validators.required],
    });
  }

  onSubmit() {
    const oldPassword = this.changePasswordForm.get('oldPassword');
    const newPassword = this.changePasswordForm.get('newPassword');
    const repeatedNewPassword = this.changePasswordForm.get(
      'repeatedNewPassword'
    );

    this.authService.changePassword(
      oldPassword?.value,
      newPassword?.value,
      repeatedNewPassword?.value
    );
  }
}
