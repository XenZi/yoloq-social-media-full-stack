import { TestBed } from '@angular/core/testing';

import { SearchGroupsService } from './search-groups.service';

describe('SearchGroupsService', () => {
  let service: SearchGroupsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchGroupsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
