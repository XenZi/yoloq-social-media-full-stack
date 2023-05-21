import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupVerticalNavItemComponent } from './group-vertical-nav-item.component';

describe('GroupVerticalNavItemComponent', () => {
  let component: GroupVerticalNavItemComponent;
  let fixture: ComponentFixture<GroupVerticalNavItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupVerticalNavItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GroupVerticalNavItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
