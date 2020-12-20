import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {ToastrService} from '../../@core/mock/toastr-service';
import {TranslateService} from '@ngx-translate/core';
import {HttpHeaders} from '@angular/common/http';
import {ProductsService} from '../../@core/services/products.service';
import {ColorService} from '../../@core/services/color.service';
import {SizeService} from '../../@core/services/size.service';
import {FormControl, FormGroup} from '@angular/forms';

declare var $: any;

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-home-client',
  styleUrls: ['./product-detail.component.scss'],
  templateUrl: './product-detail.component.html',
})
export class ProductDetailComponent implements OnInit, OnDestroy {
  ngOnDestroy(): void {
  }

  key: any;
  obj: any;
  rows: any;
  inputForm: any;

  constructor(private activatedRoute: ActivatedRoute,
              private toastr: ToastrService,
              private translateService: TranslateService,
              public productsService: ProductsService,
              private colorService: ColorService,
              private sizeService: SizeService,
              private router: Router) {
    this.activatedRoute.params.subscribe((params: Params) => {
      this.key = params['key'];
    });
    this.search(this.key, 0);
    // this.obj = null;
    // console.log(this.key);
    // for (let i = 0; i < this.lstData.length; i++) {
    //   if (this.lstData[i].code === this.key) {
    //     this.obj = this.lstData[i];
    //     console.log(this.lstData[i]);
    //     console.log(this.obj);
    //   }
    // }
    // if (this.obj === null) {
    //   // chuyen ve trang home bao loi ko tim thay san pham
    //   this.toastr.showToast('danger', 'Thông báo', 'Có lỗi trong quá trình xử lý');
    //   this.router.navigate(['trang-chu']);
    // }
  }

  search(code: any, pageToLoad: number) {
    this.productsService.doSearch1({}, {code: code, status: 0}).subscribe(
      (res) => {
        this.onSuccess(res.body.data, res.headers, pageToLoad);
      },
      (error) => {
      },
    );
  }

  thanhToan() {
    // this.inputForm.get('amount').setValue(this.soluong(3));
    // this.router.navigate(['/thanh-toan']);
  }

  protected onSuccess(data: any | null, headers: HttpHeaders, page: number): void {
    // this.page.count = data.totalPages;
    // this.page.offset = page || 0;
    this.rows = data.list || [];
    this.obj = this.rows[0];
  }

  lstRole1 = [];
  lstRole2 = [];
  getSoLuong = 1;

  soluong(e) {
    let soLuong = this.inputForm.get('amount').value;
    if (e === 0) {
      if (this.inputForm.get('amount').value === 0) {
        this.inputForm.get('amount').setValue(0);
        return;
      }
      soLuong = soLuong - 1;
      // this.inputForm.get('amount').setValue((soLuong - 1));
    } else {
      soLuong = soLuong + 1;
      // this.inputForm.get('amount').setValue((soLuong + 1));
    }
    this.inputForm.get('amount').setValue(soLuong)
    return soLuong;
  }

  ngOnInit(): void {
    this.inputForm = new FormGroup({
      color: new FormControl(null, []),
      size: new FormControl(null, []),
      amount: new FormControl(1, []),
      status: new FormControl(null, [])
    });
    this.sizeService.query().subscribe(res => {
      this.lstRole1 = res.body.data.list;
    }, err => {
    });
    this.colorService.query().subscribe(res => {
      this.lstRole2 = res.body.data.list;
    }, err => {
    });

    $('.wrap-slick3').each(function () {
      $(this).find('.slick3').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        fade: true,
        infinite: true,
        autoplay: false,
        autoplaySpeed: 6000,

        arrows: true,
        appendArrows: $(this).find('.wrap-slick3-arrows'),
        prevArrow: '<button class="arrow-slick3 prev-slick3"><i class="fa fa-angle-left" aria-hidden="true"></i></button>',
        nextArrow: '<button class="arrow-slick3 next-slick3"><i class="fa fa-angle-right" aria-hidden="true"></i></button>',

        dots: true,
        appendDots: $(this).find('.wrap-slick3-dots'),
        dotsClass: 'slick3-dots',
        customPaging: function (slick, index) {
          const portrait = $(slick.$slides[index]).data('thumb');
          return '<img src=" ' + portrait + ' "/><div class="slick3-dot-overlay"></div>';
        },
      });
    });
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
      const nameTab = $(e.target).attr('href');
      $(nameTab).find('.slick2').slick('reinit');
    });
    $('.wrap-slick2').each(function () {
      $(this).find('.slick2').slick({
        slidesToShow: 4,
        slidesToScroll: 4,
        infinite: false,
        autoplay: false,
        autoplaySpeed: 6000,
        arrows: true,
        appendArrows: $(this),
        prevArrow: '<button class="arrow-slick2 prev-slick2"><i class="fa fa-angle-left" aria-hidden="true"></i></button>',
        nextArrow: '<button class="arrow-slick2 next-slick2"><i class="fa fa-angle-right" aria-hidden="true"></i></button>',
        responsive: [
          {
            breakpoint: 1200,
            settings: {
              slidesToShow: 4,
              slidesToScroll: 4
            }
          },
          {
            breakpoint: 992,
            settings: {
              slidesToShow: 3,
              slidesToScroll: 3
            }
          },
          {
            breakpoint: 768,
            settings: {
              slidesToShow: 2,
              slidesToScroll: 2
            }
          },
          {
            breakpoint: 576,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1
            }
          }
        ]
      });
    });
    $('.gallery-lb').each(function () { // the containers for all your galleries
      $(this).magnificPopup({
        delegate: 'a', // the selector for gallery item
        type: 'image',
        gallery: {
          enabled: true
        },
        mainClass: 'mfp-fade'
      });
    });
  }
}
