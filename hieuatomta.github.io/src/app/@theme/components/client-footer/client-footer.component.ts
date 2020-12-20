import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {FacebookService, InitParams} from 'ngx-facebook';
declare var jQuery: any;

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-footer-client',
  styleUrls: ['./client-footer.component.scss'],
  templateUrl: './client-footer.component.html',
})
export class ClientFooterComponent implements OnInit, OnDestroy {
  constructor(private facebookService: FacebookService) {
  }

  topWeb() {

  }

  ngOnInit(): void {
    this.initFacebookService();
    (function ($) {
      const windowH = $(window).height() / 2;

      $(window).on('scroll', function () {
        if ($(this).scrollTop() > windowH) {
          $('#myBtn').css('display', 'flex');
        } else {
          $('#myBtn').css('display', 'none');
        }
      });

      $('#myBtn').on('click', function () {
        $('html, body').animate({scrollTop: 0}, 300);
      });
    })(jQuery);

  }

  private initFacebookService(): void {
    const initParams: InitParams = {xfbml: true, version: 'v8.0'};
    this.facebookService.init(initParams);
  }


  ngOnDestroy() {
  }

  scroll = (event): void => {
  };

}

