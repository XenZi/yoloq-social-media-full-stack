import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GroupService } from 'src/app/services/group/group.service';

@Component({
  selector: 'app-create-group-form',
  templateUrl: './create-group-form.component.html',
  styleUrls: ['./create-group-form.component.scss'],
})
export class CreateGroupFormComponent {
  createGroupForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private groupService: GroupService
  ) {
    this.createGroupForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  onSubmit() {
    const name = this.createGroupForm.get('name');
    const description = this.createGroupForm.get('description');
    this.groupService.createGroup(name?.value, description?.value);
  }
}
