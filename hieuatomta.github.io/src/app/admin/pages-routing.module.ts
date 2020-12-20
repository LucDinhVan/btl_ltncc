import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

import {PagesComponent} from './pages.component';
import {HomeComponent} from './homes/home.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'home',
      component: HomeComponent,
    },
    {
      path: 'users',
      loadChildren: () => import('./sys_config/users/users.module')
        .then(m => m.UsersModule),
    },
    {
      path: 'roles',
      loadChildren: () => import('./sys_config/roles/roles.module')
        .then(m => m.RolesModule),
      data: {
        breadcrumb: 'Quản lý nhóm quyền'
      }
    },
    {
      path: 'action',
      loadChildren: () => import('./sys_config/actions/actions.module')
        .then(m => m.ActionsModule),
    },
    {
      path: 'color',
      loadChildren: () => import('./sys_config/colors/colors.module')
        .then(m => m.ColorsModule),
    },
    {
      path: 'size',
      loadChildren: () => import('./sys_config/sizes/sizes.module')
        .then(m => m.SizesModule),
    },
    {
      path: 'objects',
      loadChildren: () => import('./sys_config/objects/objects.module')
        .then(m => m.ObjectsModule),
    },
    {
      path: 'image-management',
      loadChildren: () => import('./major/image-management/image-management.module')
        .then(m => m.ImageManagementModule),
    },
    {
      path: 'products-management',
      loadChildren: () => import('./major/product-management/product-management.module')
        .then(m => m.ProductManagementModule),
    },
    {
      path: 'logs',
      loadChildren: () => import('./sys_config/logs/logs.module')
        .then(m => m.LogsModule),
    },
    {
      path: 'layout',
      loadChildren: () => import('./demo/layout/layout.module')
        .then(m => m.LayoutModule),
    },
    {
      path: 'ui-features',
      loadChildren: () => import('./demo/ui-features/ui-features.module')
        .then(m => m.UiFeaturesModule),
    },
    {
      path: 'modal-overlays',
      loadChildren: () => import('./demo/modal-overlays/modal-overlays.module')
        .then(m => m.ModalOverlaysModule),
    },
    {
      path: 'charts',
      loadChildren: () => import('./demo/charts/charts.module')
        .then(m => m.ChartsModule),
    },
    {
      path: 'editors',
      loadChildren: () => import('./demo/editors/editors.module')
        .then(m => m.EditorsModule),
    },
    {
      path: 'tables',
      loadChildren: () => import('./homes/tables/tables.module')
        .then(m => m.TablesModule),
    },
    {
      path: '',
      redirectTo: 'home',
      pathMatch: 'full',
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
