import { TestBed, inject } from '@angular/core/testing';

import { ResponseToModelService } from './ResponseToModel.service.ts';

describe('ResponseToModelService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ResponseToModelService]
    });
  });

  it('should be created', inject([ResponseToModelService], (service: ResponseToModelService) => {
    expect(service).toBeTruthy();
  }));
});
