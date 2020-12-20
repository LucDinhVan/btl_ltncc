import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {ProductsService} from '../../@core/services/products.service';
import {HttpHeaders} from '@angular/common/http';


@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-list-product',
  styleUrls: ['./list-product.component.scss'],
  templateUrl: './list-product.component.html',
})
export class ListProductComponent implements OnInit, OnDestroy {
  ngOnInit(): void {
    this.search(0);
  }

  ngOnDestroy(): void {
  }

  constructor(public productsService: ProductsService) {

  }

  isLoad: any;
  pageOfItems: Array<any>;
  rows: any;
  page = {
    limit: 10,
    count: 0,
    offset: 0,
  };
  data: {
    image: any;
    name: any;
    price: any;
  };

  search(pageToLoad: number) {
    this.isLoad = true;
    this.page.offset = pageToLoad;
    this.productsService.doSearch1({
    }, {status: 0}).subscribe(
      (res) => {
        this.onSuccess(res.body.data, res.headers, pageToLoad);
      },
      (error) => {
        this.isLoad = false;
      },
      () => this.isLoad = false,
    );
  }

  protected onSuccess(data: any | null, headers: HttpHeaders, page: number): void {
    // this.page.count = data.totalPages;
    // this.page.offset = page || 0;
    this.rows = data.list || [];
  }

  onChangePage(pageOfItems: Array<any>) {
    // update current page of items
    this.pageOfItems = pageOfItems;
  }



  lstData = [
    {
      code: 'sp01',
      image: 'assets/images/product-02.jpg',
      name: 'Herschel supply',
      price: '$35.31',
      type: 'women'
    },
    {
      code: 'sp02',
      image: 'assets/images/product-01.jpg',
      name: 'Only Check Trouser',
      price: '$25.50',
      type: 'shoes'
    },
    {
      code: 'sp03',
      image: 'assets/images/product-03.jpg',
      name: 'Classic Trench Coat',
      price: '$75.00',
      type: 'women'
    },
    {
      code: 'sp04',
      image: 'assets/images/product-04.jpg',
      name: 'Front Pocket Jumper',
      price: '$34.75',
      type: 'men'
    },
    {
      code: 'sp05',
      image: 'assets/images/product-05.jpg',
      name: 'Front Pocket Jumper',
      price: '$34.75',
      type: 'shoes'
    },
    {
      code: 'sp06',
      image: 'assets/images/product-07.jpg',
      name: 'Front Pocket Jumper',
      price: '$34.75',
      type: 'watches'
    },
    {
      code: 'sp07',
      image: 'assets/images/product-06.jpg',
      name: 'Front Pocket Jumper',
      price: '$34.75',
      type: 'men'
    },
    {
      code: 'sp08',
      image: 'assets/images/product-08.jpg',
      name: 'Front Pocket Jumper',
      price: '$34.75',
      type: 'watches'
    },
  ];

}
