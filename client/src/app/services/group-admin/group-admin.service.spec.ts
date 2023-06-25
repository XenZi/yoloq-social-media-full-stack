import { TestBed } from '@angular/core/testing';

import { GroupAdminService } from './group-admin.service';

describe('GroupAdminService', () => {
  let service: GroupAdminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GroupAdminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
