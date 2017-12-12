import { TestBed, inject } from '@angular/core/testing';

import { DalforReadsService } from './dalfor-reads.service';

describe('DalforReadsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DalforReadsService]
    });
  });

  it('should be created', inject([DalforReadsService], (service: DalforReadsService) => {
    expect(service).toBeTruthy();
  }));
});
