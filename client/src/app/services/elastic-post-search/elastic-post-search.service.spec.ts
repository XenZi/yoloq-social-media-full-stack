import { TestBed } from '@angular/core/testing';

import { ElasticPostSearchService } from './elastic-post-search.service';

describe('ElasticPostSearchService', () => {
  let service: ElasticPostSearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElasticPostSearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
