import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateGroupFormComponent } from './update-group-form.component';

describe('UpdateGroupFormComponent', () => {
  let component: UpdateGroupFormComponent;
  let fixture: ComponentFixture<UpdateGroupFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateGroupFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateGroupFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
