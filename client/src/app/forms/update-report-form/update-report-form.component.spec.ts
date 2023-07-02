import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateReportFormComponent } from './update-report-form.component';

describe('UpdateReportFormComponent', () => {
  let component: UpdateReportFormComponent;
  let fixture: ComponentFixture<UpdateReportFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateReportFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateReportFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
