import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import Group from 'src/app/models/entity/Group';
import { GroupService } from 'src/app/services/group/group.service';

@Component({
  selector: 'app-vertical-nav',
  templateUrl: './vertical-nav.component.html',
  styleUrls: ['./vertical-nav.component.scss'],
})
export class VerticalNavComponent {
  groups!: Observable<Group[]>;
  clickedInputNumber: number = -1;

  constructor(private groupService: GroupService) {}

  ngOnInit() {
    this.groups = this.groupService.getAll();
  }
}
