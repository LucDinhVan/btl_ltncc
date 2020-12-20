import {NgModule} from '@angular/core';
import {NbMenuModule} from '@nebular/theme';

import {ThemeModule} from '../@theme/theme.module';
import {PagesComponent, role} from './pages.component';
import {PagesRoutingModule} from './pages-routing.module';
import {HomeModule} from './homes/home.module';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    HomeModule,
  ],
  declarations: [
    PagesComponent, role
  ],
})
export class PagesModule {
}
