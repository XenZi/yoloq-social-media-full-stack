import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchPostsElasticComponent } from './search-posts-elastic.component';

describe('SearchPostsElasticComponent', () => {
  let component: SearchPostsElasticComponent;
  let fixture: ComponentFixture<SearchPostsElasticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchPostsElasticComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchPostsElasticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
