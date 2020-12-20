import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ToastrService} from '../../../../@core/mock/toastr-service';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SizeService} from '../../../../@core/services/size.service';
import {TranslateService} from '@ngx-translate/core';
import {TreeviewConfig, TreeviewItem} from 'ngx-treeview';
import {ObjectsService} from '../../../../@core/services/objects.service';
import {ColorService} from '../../../../@core/services/color.service';
import {ProductsService} from '../../../../@core/services/products.service';
import {ColumnChangesService, DimensionsHelper, ScrollbarHelper} from '@swimlane/ngx-datatable';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-size-update',
  styleUrls: ['./products-update.component.scss'],
  templateUrl: './products-update.component.html',
  providers: [ScrollbarHelper, DimensionsHelper, ColumnChangesService],
})
export class ProductsUpdateComponent implements OnInit {
  lstRole = [];
  inputProduct: any;
  inputSizeColor: any;
  itemRoles: any;
  loading = false;
  title: string;
  data: any;
  lstRole1 = [];
  lstRole2 = [];
  item: TreeviewItem[] = [];
  treeViewConfig = TreeviewConfig.create({
    hasFilter: true,
    hasAllCheckBox: false,
    maxHeight: 300,
    hasCollapseExpand: true,
  });
  rows = [];

  listStatus = [
    {name: 'common.state.0', code: 0},
    {name: 'common.state.1', code: 1},
    {name: 'common.state.2', code: 2},
    {name: 'common.state.3', code: 3},
  ];
  arr = [];


  constructor(
    private objectsService: ObjectsService,
    private toastr1: ToastrService,
    private toastr: NbToastrService,
    private translate: TranslateService,
    public ref: NbDialogRef<ProductsUpdateComponent>,
    private sizeService: SizeService,
    private colorService: ColorService,
    private productsService: ProductsService,
  ) {
  }


  ngOnInit(): void {
    this.inputProduct = new FormGroup({
      id: new FormControl(this.data?.id, []),
      name: new FormControl(null, [Validators.required]),
      code: new FormControl(null, [Validators.required]),
      cost: new FormControl(null, [Validators.required]),
      description: new FormControl(null, []),
      status: new FormControl(this.data?.status, [Validators.required]),
      idParen: new FormControl(this.data?.idParen ? this.data.idParen === 0 ? null : this.data.idParen : null, [Validators.required])
    });
    // this.inputProduct.get('status').setValue(true);
    if (this.data) {
      this.inputProduct.patchValue(this.data);
      // const status = this.data.status === 1 ? true : false;
      // this.inputProduct.get('status').patchValue(status);
    }
    ;

    this.sizeService.query().subscribe(res => {
      this.lstRole1 = res.body.data.list;
    }, err => {
    });
    this.colorService.query().subscribe(res => {
      this.lstRole2 = res.body.data.list;
    }, err => {
    });
    this.getParenTree(this.data?.type ? this.data.type : 1);

  };

  parenIdChange($event) {
    this.inputProduct.get('idParen').setValue($event);
  }


  getParenTree(e: Number) {
    this.loading = true;
    this.objectsService.query().subscribe(res => {
        const result = res.body.data.list.filter(function (hero) {
          return hero.type === e;
        });
        this.item = this.formatDataTree(result, 0);
      }, (error) => {
        this.loading = false;
      },
      () => this.loading = false);
  }

  formatDataTree(data, parenId) {
    const arr = [];
    for (let i = 0; i < data.length; i++) {
      const dataItem = data[i];
      if (dataItem.parenId === parenId) {
        let children = [];
        if (dataItem.id != null) {
          children = this.formatDataTree(data, dataItem.id);
        }
        if (children.length > 0) {
          dataItem.children = children;
        } else {
          dataItem.children = null;
        }
        const dataTreeview = new TreeviewItem({text: dataItem.name, value: dataItem.id, children: dataItem.children});
        arr.push(dataTreeview);
      }
    }
    return arr;
  }


  submit() {
    // this.inputProduct.get('status').patchValue(this.inputProduct.get('status').value ? 1 : 0);
    this.inputProduct.markAllAsTouched();
    if (this.inputProduct.valid) {
      this.loading = true;
      if (this.data == null) {
        // this.inputProduct.get('productSizeColorList').setValue(this.rows);
        this.productsService.insert(this.inputProduct.value).subscribe(
          (value) => this.ref.close(value),
          (error) => {
            this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
            this.loading = false;
          },
          () => this.loading = false
        );
      } else {
        this.productsService.update(this.inputProduct.value).subscribe(
          (value) => this.ref.close(value),
          (error) => {
            this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
            this.loading = false;
          },
          () => this.loading = false,
        );
      }
    } else {
    }
  }

  cancel() {
    this.ref.close();
  }
}
