import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserApproveGroupJoinComponent } from './user-approve-group-join.component';

describe('UserApproveGroupJoinComponent', () => {
  let component: UserApproveGroupJoinComponent;
  let fixture: ComponentFixture<UserApproveGroupJoinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserApproveGroupJoinComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserApproveGroupJoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
