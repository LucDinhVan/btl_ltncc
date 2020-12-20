import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {ObjectUpdateComponent} from './object-update.component';


describe('UserUpdateComponent', () => {
  let component: ObjectUpdateComponent;
  let fixture: ComponentFixture<ObjectUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObjectUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObjectUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
