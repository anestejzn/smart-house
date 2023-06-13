import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewRuleComponent } from './create-new-rule.component';

describe('CreateNewRuleComponent', () => {
  let component: CreateNewRuleComponent;
  let fixture: ComponentFixture<CreateNewRuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateNewRuleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateNewRuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
