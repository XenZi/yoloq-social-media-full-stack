import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Group from 'src/app/domains/entity/Group';
import { GroupService } from 'src/app/services/group/group.service';

@Component({
  selector: 'app-update-group-form',
  templateUrl: './update-group-form.component.html',
  styleUrls: ['./update-group-form.component.scss'],
})
export class UpdateGroupFormComponent {
  updateFormGroup: FormGroup;
  @Input() group!: Group;
  constructor(
    private formBuilder: FormBuilder,
    private groupService: GroupService
  ) {
    this.updateFormGroup = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ngOnInit() {
    console.log(this.group);

    this.updateFormGroup = this.formBuilder.group({
      name: [this.group?.name],
      description: [this.group?.description],
    });
  }

  onSubmit() {
    const name = this.updateFormGroup.get('name');
    const description = this.updateFormGroup.get('description');
    this.groupService.updateGroup(
      name?.value,
      description?.value,
      this.group.id
    );
  }
}
