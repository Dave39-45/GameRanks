import { TestBed, inject } from '@angular/core/testing';

import { IsLogedInService } from './is-logged-in.service.ts';

describe('IsLogedInService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IsLogedInService]
    });
  });

  it('should be created', inject([IsLogedInService], (service: IsLogedInService) => {
    expect(service).toBeTruthy();
  }));
});
