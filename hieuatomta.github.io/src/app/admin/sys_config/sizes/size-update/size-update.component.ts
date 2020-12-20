import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ToastrService} from '../../../../@core/mock/toastr-service';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SizeService} from '../../../../@core/services/size.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-size-update',
  styleUrls: ['./size-update.component.scss'],
  templateUrl: './size-update.component.html',
})
export class SizeUpdateComponent implements OnInit {
  lstRole = [];
  listStatus = [
    {name: 'common.status.1', code: 1},
    {name: 'common.status.0', code: 0}
  ];
  inputSize: any;
  itemRoles: any;
  loading = false;
  title: string;
  data: any;

  ngOnInit(): void {
    this.inputSize = new FormGroup({
      id: new FormControl(this.data?.id, []),
      name: new FormControl(null, [Validators.required]),
      code: new FormControl(null, [Validators.required]),
      description: new FormControl(null, []),
      status: new FormControl(null, [Validators.required])
    });
    this.inputSize.get('status').setValue(true);
    if (this.data) {
      this.inputSize.patchValue(this.data);
      const status = this.data.status === 1 ? true : false;
      this.inputSize.get('status').patchValue(status);
    };
  };


  constructor(
    private toastr1: ToastrService,
    private toastr: NbToastrService,
    private translate: TranslateService,
    public ref: NbDialogRef<SizeUpdateComponent>,
    private sizeService: SizeService) {
  }


  submit() {
    this.inputSize.get('status').patchValue(this.inputSize.get('status').value ? 1 : 0);
    this.inputSize.markAllAsTouched();
    if (this.inputSize.valid) {
      this.loading = true;
      if (this.data == null) {
        this.sizeService.insert(this.inputSize.value).subscribe(
          (value) => this.ref.close(value),
          (error) => {
            this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
            this.loading = false;
          },
          () => this.loading = false
        );
      } else {
        this.sizeService.update(this.inputSize.value).subscribe(
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
