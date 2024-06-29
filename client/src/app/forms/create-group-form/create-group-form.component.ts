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
      attachedPDF: [''],
    });
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.createGroupForm.patchValue({
        attachedPDF: file
      });
      this.createGroupForm.get('attachedPDF')?.updateValueAndValidity();
    }
    console.log(this.createGroupForm.get('attachedPDF')?.value);
  }
  

  onSubmit() {
    const name = this.createGroupForm.get('name');
    const description = this.createGroupForm.get('description');
    const attachedPDF = this.createGroupForm.get('attachedPDF')?.value;
    this.groupService.createGroup(name?.value, description?.value, attachedPDF);
  }
}
