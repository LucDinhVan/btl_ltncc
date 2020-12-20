import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {TreeviewConfig, TreeviewItem} from 'ngx-treeview';
import {TranslateService} from '@ngx-translate/core';
import {ObjectsService} from '../../../../@core/services/objects.service';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-object-update',
  styleUrls: ['./object-update.component.scss'],
  templateUrl: './object-update.component.html',
})
export class ObjectUpdateComponent implements OnInit {
  title: string;
  inputObject: FormGroup;
  data: any;
  loading = false;
  item: TreeviewItem[] = [];
  treeViewConfig = TreeviewConfig.create({
    hasFilter: true,
    hasAllCheckBox: false,
    maxHeight: 300,
    hasCollapseExpand: true,
  });
  listType = [
    {name: 'common.type.1', code: 1},
    {name: 'common.type.0', code: 0}
  ];

  constructor(protected ref: NbDialogRef<ObjectUpdateComponent>,
              private objectsService: ObjectsService,
              private toastr: NbToastrService,
              private translate: TranslateService) {
  }

  ngOnInit(): void {
    this.initForm();
    this.loading = true;
    this.getParenTree(this.data?.type ? this.data.type : 0);
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

  onSelectType(e) {
    this.getParenTree(e);
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

  initForm() {
    this.inputObject = new FormGroup({
      id: new FormControl(this.data?.id, []),
      code: new FormControl(this.data?.code, [Validators.required, Validators.maxLength(200)]),
      name: new FormControl(this.data?.name, [Validators.required, Validators.maxLength(250)]),
      path: new FormControl(this.data?.path, [Validators.maxLength(500)]),
      icon: new FormControl(this.data?.icon, [Validators.maxLength(255)]),
      status: new FormControl(this.data?.status, [Validators.required]),
      type: new FormControl(this.data?.type, [Validators.required]),
      description: new FormControl(this.data?.description, [Validators.maxLength(500)]),
      parenId: new FormControl(this.data?.parenId ? this.data.parenId === 0 ? null : this.data.parenId : null, [])
    }, {});
    this.inputObject.get('status').setValue(true);
    if (this.data) {
      this.inputObject.patchValue(this.data);
      const status = this.data.status === 1 ? true : false;
      this.inputObject.get('status').patchValue(status);
    } else {
      this.inputObject.get('type').patchValue(0);
    }
    ;
  }

  parenIdChange($event) {
    this.inputObject.get('parenId').setValue($event);
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    this.inputObject.get('status').patchValue(this.inputObject.get('status').value ? 1 : 0);
    this.inputObject.markAllAsTouched();
    if (this.inputObject.valid) {
      this.loading = true;
      if (this.inputObject.get('parenId').value == null) {
        this.inputObject.get('parenId').setValue(0);
      }
      if (this.data == null) {
        this.objectsService.insert(this.inputObject.value).subscribe(
          (value) => this.ref.close(value),
          (error) => {
            this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
            this.loading = false;
          },
          () => this.loading = false,
        );
      } else {
        this.objectsService.update(this.inputObject.value).subscribe(
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
}
