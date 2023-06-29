import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Group from 'src/app/domains/entity/Group';
import { GroupService } from 'src/app/services/group/group.service';

@Component({
  selector: 'app-suspend-group-form',
  templateUrl: './suspend-group-form.component.html',
  styleUrls: ['./suspend-group-form.component.scss'],
})
export class SuspendGroupFormComponent {
  suspendGroupForm: FormGroup;
  @Input() group!: Group;

  constructor(
    private formBuilder: FormBuilder,
    private groupService: GroupService
  ) {
    this.suspendGroupForm = this.formBuilder.group({
      reason: ['', Validators.required],
    });
  }

  onSubmit() {
    let reason = this.suspendGroupForm.get('reason')?.value;
    this.groupService.suspendGroup(this.group.id, reason);
  }
}
