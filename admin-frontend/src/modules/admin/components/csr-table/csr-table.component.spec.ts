import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CsrTableComponent } from './csr-table.component';

describe('CsrTableComponent', () => {
  let component: CsrTableComponent;
  let fixture: ComponentFixture<CsrTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CsrTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CsrTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
