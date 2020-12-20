import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ToastrService} from '../../../../@core/mock/toastr-service';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ColorService} from '../../../../@core/services/color.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-color-update',
  styleUrls: ['./color-update.component.scss'],
  templateUrl: './color-update.component.html',
})
export class ColorUpdateComponent implements OnInit {
  lstRole = [];
  listStatus = [
    {name: 'common.status.1', code: 1},
    {name: 'common.status.0', code: 0}
  ];
  inputColor: any;
  itemRoles: any;
  loading = false;
  title: string;
  data: any;

  ngOnInit(): void {
    this.inputColor = new FormGroup({
      id: new FormControl(this.data?.id, []),
      name: new FormControl(null, [Validators.required]),
      code: new FormControl(null, [Validators.required]),
      description: new FormControl(null, []),
      status: new FormControl(null, [Validators.required])
    });
    this.inputColor.get('status').setValue(true);
    if (this.data) {
      this.inputColor.patchValue(this.data);
      const status = this.data.status === 1 ? true : false;
      this.inputColor.get('status').patchValue(status);
    };
  };


  constructor(
    private toastr1: ToastrService,
    private toastr: NbToastrService,
    private translate: TranslateService,
    public ref: NbDialogRef<ColorUpdateComponent>,
    private colorService: ColorService) {
  }


  submit() {
    this.inputColor.get('status').patchValue(this.inputColor.get('status').value ? 1 : 0);
    this.inputColor.markAllAsTouched();
    if (this.inputColor.valid) {
      this.loading = true;
      if (this.data == null) {
        this.colorService.insert(this.inputColor.value).subscribe(
          (value) => this.ref.close(value),
          (error) => {
            this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
            this.loading = false;
          },
          () => this.loading = false
        );
      } else {
        this.colorService.update(this.inputColor.value).subscribe(
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
