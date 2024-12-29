import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddboardComponent } from './addboard.component';

describe('AddboardComponent', () => {
  let component: AddboardComponent;
  let fixture: ComponentFixture<AddboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddboardComponent]
    });
    fixture = TestBed.createComponent(AddboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
