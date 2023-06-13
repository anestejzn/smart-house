import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogSearchComponent } from './log-search.component';

describe('LogSearchComponent', () => {
  let component: LogSearchComponent;
  let fixture: ComponentFixture<LogSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
