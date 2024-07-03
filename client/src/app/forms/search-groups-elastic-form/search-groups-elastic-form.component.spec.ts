import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchGroupsElasticFormComponent } from './search-groups-elastic-form.component';

describe('SearchGroupsElasticFormComponent', () => {
  let component: SearchGroupsElasticFormComponent;
  let fixture: ComponentFixture<SearchGroupsElasticFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchGroupsElasticFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchGroupsElasticFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
