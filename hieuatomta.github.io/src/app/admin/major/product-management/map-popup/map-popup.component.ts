import {Component, OnInit} from '@angular/core';
import {ColumnChangesService, DimensionsHelper, ScrollbarHelper} from '@swimlane/ngx-datatable';
import {NbDialogRef, NbDialogService, NbToastrService} from '@nebular/theme';
import {TranslateService} from '@ngx-translate/core';
import {ObjectActionService} from '../../../../@core/services/object-action.service';
import {SizeService} from '../../../../@core/services/size.service';
import {ColorService} from '../../../../@core/services/color.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ProductsService} from '../../../../@core/services/products.service';
import {ConfirmDialogComponent} from '../../../../shares/directives/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'ngx-map-module',
  templateUrl: './map-popup.component.html',
  styleUrls: ['./map-popup.component.scss'],
  providers: [ScrollbarHelper, DimensionsHelper, ColumnChangesService],
})
export class MapPopupComponent implements OnInit {
  title: string;
  data: any;
  loading = false;
  rows;
  allData: any;
  selectedUI = [];
  selected = [];
  originalData = [];
  isLoad: boolean;
  page = {
    limit: 5,
    count: 0,
    offset: 0,
  };
  columns = [
    {name: 'common.table.item_product_size', prop: 'nameSize', flexGrow: 1},
    {name: 'common.table.item_product_color', prop: 'nameColor', flexGrow: 1.5},
    {name: 'common.table.item_product_total', prop: 'amount', flexGrow: 1},
    {name: 'common.table.item_action', prop: 'action_btn', flexGrow: 1}
  ];
  lstRole1 = [];
  lstRole2 = [];
  inputProduct: any;

  constructor(public ref: NbDialogRef<MapPopupComponent>,
              private toastr: NbToastrService,
              private translate: TranslateService,
              private objectActionService: ObjectActionService,
              private sizeService: SizeService,
              private colorService: ColorService,
              private dialogService: NbDialogService,
              private toastrService: NbToastrService,
              private productsService: ProductsService,
  ) {
  }

  protected onSuccess(data: any | null): void {
    this.rows = data.list || [];
    this.selectedUI = [];
    this.selected.map(value => {
      this.rows.map((value1) => {
        if (value === value1.id) {
          this.selectedUI.push(value1);
        }
      });
    });
  }

  ngOnInit(): void {
    this.loading = true;
    this.inputProduct = new FormGroup({
      idProduct: new FormControl(this.data?.id, []),
      amount: new FormControl(null, [Validators.required]),
      idSize: new FormControl(null, [Validators.required]),
      idColor: new FormControl(null, [Validators.required]),
    });
    this.sizeService.query().subscribe(res => {
      this.lstRole1 = res.body.data.list;
    }, err => {
    });
    this.colorService.query().subscribe(res => {
      this.lstRole2 = res.body.data.list;
    }, err => {
    });
    this.search();
  }

  toAstrError() {
    this.toastr.success(this.translate.instant('common.table.unknown_error'),
      this.translate.instant('common.title_notification'));
  }

  addSizeColor() {
    this.inputProduct.markAllAsTouched();
    if (this.inputProduct.valid) {
      this.loading = true;
      this.productsService.insertSizeColor(this.inputProduct.value).subscribe(
        (value) => {
          this.search();
          this.loading = false;
        },
        (error) => {
          this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
          this.loading = false;
        },
        () => this.loading = false
      );
    }
  }

  search() {
    this.loading = true;
    this.productsService.doSearchByCode(this.data?.id).subscribe(res => {
        this.onSuccess(res.body.data);
        this.loading = false;
      },
      (error) => {
        this.loading = false;
      });
  };

  cancel() {
    this.ref.close();
  }

  deleteSizeColor(data) {
    this.dialogService.open(ConfirmDialogComponent, {
      context: {
        title: this.translate.instant('common.title_notification'),
        message: this.translate.instant('products.title_delete') + ' ' + data.nameColor + ' - ' + data.nameSize
      },
    }).onClose.subscribe(res => {
      if (res) {
        this.isLoad = true;
        this.productsService.deleteSizeColor(data.id).subscribe(() => {
          this.toastrService.success(this.translate.instant('products.delete_success'),
            this.translate.instant('common.title_notification'));
          this.search();
          this.isLoad = false;
        }, (err) => {
          this.toastrService.success(err.detail),
            this.translate.instant('common.title_notification');
          this.isLoad = false;
        });
      }
    });
  }
}
