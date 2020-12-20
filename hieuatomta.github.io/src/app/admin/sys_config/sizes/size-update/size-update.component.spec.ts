import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {SizeUpdateComponent} from './size-update.component';


describe('UserUpdateComponent', () => {
  let component: SizeUpdateComponent;
  let fixture: ComponentFixture<SizeUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SizeUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SizeUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
