import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MapImageProductComponent } from './map-image-product.component';

describe('MapModuleComponent', () => {
  let component: MapImageProductComponent;
  let fixture: ComponentFixture<MapImageProductComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MapImageProductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MapImageProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
