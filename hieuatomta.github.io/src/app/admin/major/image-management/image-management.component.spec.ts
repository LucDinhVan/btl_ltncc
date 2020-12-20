import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {ImageManagementComponent} from './image-management.component';


describe('UsersComponent', () => {
  let component: ImageManagementComponent;
  let fixture: ComponentFixture<ImageManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImageManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
