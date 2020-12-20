import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {ImageManagementComponent} from './image-management.component';

const routes: Routes = [{
  path: '',
  component: ImageManagementComponent,
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ImageManagementRoutingModule { }

export const routedComponents = [
  ImageManagementComponent,
];
