import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatePostFormComponent } from './update-post-form.component';

describe('UpdatePostFormComponent', () => {
  let component: UpdatePostFormComponent;
  let fixture: ComponentFixture<UpdatePostFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdatePostFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdatePostFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
