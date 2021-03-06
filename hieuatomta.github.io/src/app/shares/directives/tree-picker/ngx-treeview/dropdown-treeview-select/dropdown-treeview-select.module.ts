import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DropdownTreeviewSelectComponent } from './dropdown-treeview-select.component';
import {TreeviewModule} from 'ngx-treeview';

@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    TreeviewModule.forRoot(),
  ],
  declarations: [
    DropdownTreeviewSelectComponent,
  ],
  exports: [
    DropdownTreeviewSelectComponent,
  ],
})
export class DropdownTreeviewSelectModule { }
