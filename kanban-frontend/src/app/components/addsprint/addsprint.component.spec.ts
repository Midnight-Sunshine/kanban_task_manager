import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddsprintComponent } from './addsprint.component';

describe('AddsprintComponent', () => {
  let component: AddsprintComponent;
  let fixture: ComponentFixture<AddsprintComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddsprintComponent]
    });
    fixture = TestBed.createComponent(AddsprintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
