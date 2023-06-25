import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowUserInGroupComponent } from './show-user-in-group.component';

describe('ShowUserInGroupComponent', () => {
  let component: ShowUserInGroupComponent;
  let fixture: ComponentFixture<ShowUserInGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowUserInGroupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowUserInGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
