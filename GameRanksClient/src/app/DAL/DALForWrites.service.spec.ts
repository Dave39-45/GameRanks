import { TestBed, inject } from '@angular/core/testing';

import { DalforWritesService } from './dalfor-writes.service';

describe('DalforWritesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DalforWritesService]
    });
  });

  it('should be created', inject([DalforWritesService], (service: DalforWritesService) => {
    expect(service).toBeTruthy();
  }));
});
