import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageRowComponent } from './message-row.component';

describe('MessageRowComponent', () => {
  let component: MessageRowComponent;
  let fixture: ComponentFixture<MessageRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MessageRowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MessageRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
