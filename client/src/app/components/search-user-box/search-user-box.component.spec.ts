import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchUserBoxComponent } from './search-user-box.component';

describe('SearchUserBoxComponent', () => {
  let component: SearchUserBoxComponent;
  let fixture: ComponentFixture<SearchUserBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchUserBoxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchUserBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
