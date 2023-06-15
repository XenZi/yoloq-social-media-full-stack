import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowPostWithCommentsComponent } from './show-post-with-comments.component';

describe('ShowPostWithCommentsComponent', () => {
  let component: ShowPostWithCommentsComponent;
  let fixture: ComponentFixture<ShowPostWithCommentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowPostWithCommentsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowPostWithCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
