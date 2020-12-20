import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';


@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-product',
  styleUrls: ['./product.component.scss'],
  templateUrl: './product.component.html',
})
export class ProductComponent implements OnInit, OnDestroy {
  ngOnDestroy(): void {
  }

  data: {
    image: any;
    name: any;
    price: any;
  };

  lstData = [
    {image: 'assets/images/product-02.jpg', name: 'Herschel supply', price: '$35.31', type: 'women'},
    {image: 'assets/images/product-01.jpg', name: 'Only Check Trouser', price: '$25.50', type: 'shoes'},
    {image: 'assets/images/product-03.jpg', name: 'Classic Trench Coat', price: '$75.00', type: 'women'},
    {image: 'assets/images/product-04.jpg', name: 'Front Pocket Jumper', price: '$34.75', type: 'men'},
    {image: 'assets/images/product-05.jpg', name: 'Front Pocket Jumper', price: '$34.75', type: 'shoes'},
    {image: 'assets/images/product-07.jpg', name: 'Front Pocket Jumper', price: '$34.75', type: 'watches'},
    {image: 'assets/images/product-06.jpg', name: 'Front Pocket Jumper', price: '$34.75', type: 'men'},
    {image: 'assets/images/product-08.jpg', name: 'Front Pocket Jumper', price: '$34.75', type: 'watches'},
  ];

  ngOnInit(): void {

  }
}
