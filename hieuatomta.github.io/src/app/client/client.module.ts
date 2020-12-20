import {NgModule} from '@angular/core';
import {
  NbAlertModule,
  NbCardModule,
  NbCheckboxModule,
  NbIconModule,
  NbInputModule,
  NbLayoutModule,
  NbMenuModule,
  NbSpinnerModule,
} from '@nebular/theme';

import {ThemeModule} from '../@theme/theme.module';
import {ClientRoutingModule} from './client-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ClientComponent} from './client.component';
import {RECAPTCHA_SETTINGS, RecaptchaFormsModule, RecaptchaModule, RecaptchaSettings} from 'ng-recaptcha';
import {TranslateModule} from '@ngx-translate/core';
import {SharedModule} from '../shares/shared.module';
import {HomeClientComponent} from './home_client/home-client.component';
import {SlideshowModule} from 'ng-simple-slideshow';
import {ShopingCartComponent} from './shoping-cart/shoping-cart.component';
import {AboutComponent} from './about/about.component';
import {ContactComponent} from './contact/contact.component';
import {ProductDetailComponent} from './product-detail/product-detail.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ProductComponent} from './products/product.component';
import {SearchFilterComponent} from './search-filter/search-filter.component';
import {ListProductComponent} from './list-product/list-product.component';
import {JwPaginationModule} from 'jw-angular-pagination';
import {NgSelectModule} from '@ng-select/ng-select';


@NgModule({
    imports: [
        JwPaginationModule,
        ClientRoutingModule,
        ThemeModule,
        NbMenuModule,
        NbCardModule,
        NbAlertModule,
        FormsModule,
        NbInputModule,
        NbCheckboxModule,
        NbIconModule,
        ReactiveFormsModule,
        NbLayoutModule,
        NbSpinnerModule,
        RecaptchaModule,
        RecaptchaFormsModule,
        ReactiveFormsModule,
        TranslateModule,
        SharedModule,
        SlideshowModule,
        NgbModule,
        NgSelectModule
    ],
  declarations: [
    SearchFilterComponent,
    ClientComponent,
    HomeClientComponent,
    ShopingCartComponent,
    AboutComponent,
    ContactComponent,
    ProductDetailComponent,
    ListProductComponent,
    ProductComponent
  ],
  providers: [
    {
      provide: RECAPTCHA_SETTINGS,
      useValue: {
        siteKey: '6Ldch8gZAAAAAOAujSVYWFyoWkTaNgBNzE6qyxwg',
      } as RecaptchaSettings,
    }]
})
export class ClientModule {
}
