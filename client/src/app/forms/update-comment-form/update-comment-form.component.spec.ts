import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCommentFormComponent } from './update-comment-form.component';

describe('UpdateCommentFormComponent', () => {
  let component: UpdateCommentFormComponent;
  let fixture: ComponentFixture<UpdateCommentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateCommentFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateCommentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
