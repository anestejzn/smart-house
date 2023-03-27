import { TestBed } from '@angular/core/testing';

import { CsrService } from './csr.service';

describe('CsrService', () => {
  let service: CsrService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CsrService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
