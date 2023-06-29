import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuspendGroupFormComponent } from './suspend-group-form.component';

describe('SuspendGroupFormComponent', () => {
  let component: SuspendGroupFormComponent;
  let fixture: ComponentFixture<SuspendGroupFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuspendGroupFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SuspendGroupFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
