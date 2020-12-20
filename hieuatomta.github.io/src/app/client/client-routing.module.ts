import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {HomeClientComponent} from './home_client/home-client.component';
import {ShopingCartComponent} from './shoping-cart/shoping-cart.component';
import {AboutComponent} from './about/about.component';
import {ContactComponent} from './contact/contact.component';
import {ProductDetailComponent} from './product-detail/product-detail.component';
import {ProductComponent} from './products/product.component';
import {ClientComponent} from './client.component';


const routes: Routes = [
  {
    path: '',
    component: ClientComponent,
    children: [
      {
        path: 'trang-chu',
        component: HomeClientComponent,
      },
      {
        path: 'thanh-toan',
        component: ShopingCartComponent,
      },
      {
        path: 've-chung-toi',
        component: AboutComponent,
      },
      {
        path: 'danh-sach-san-pham',
        component: ProductComponent,
      },
      {
        path: 'lien-he',
        component: ContactComponent,
      },
      {
        path: 'product-detail/:key',
        component: ProductDetailComponent,
      },
      // {
      //   path: 'chi-tiet/:key',
      //   component: ProductDetailComponent,
      // },
      {
        path: '',
        redirectTo: 'trang-chu',
        pathMatch: 'full',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClientRoutingModule {
}
