import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent {
  registerForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', Validators.required],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
    });
  }

  onSubmit() {
    const username = this.registerForm.get('username');
    const password = this.registerForm.get('password');
    const email = this.registerForm.get('email');
    const firstName = this.registerForm.get('firstName');
    const lastName = this.registerForm.get('lastName');
    const profileImage = this.registerForm.get('profileImage');
    this.authService.register(
      username?.value,
      password?.value,
      email?.value,
      firstName?.value,
      lastName?.value,
      profileImage?.value ?? null
    );
  }

  onFileSelected(event: Event): void {
    const inputElement: HTMLInputElement = event.target as HTMLInputElement;
    const file = inputElement?.files?.[0];

    this.registerForm.patchValue({
      profileImage: file,
    });
  }
}
