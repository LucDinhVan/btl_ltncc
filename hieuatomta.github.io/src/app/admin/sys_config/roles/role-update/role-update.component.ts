import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ToastrService} from '../../../../@core/mock/toastr-service';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RolesService} from '../../../../@core/services/roles.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-role-update',
  styleUrls: ['./role-update.component.scss'],
  templateUrl: './role-update.component.html',
})
export class RoleUpdateComponent implements OnInit {
  lstRole = [];
  listStatus = [
    {name: 'common.status.1', code: 1},
    {name: 'common.status.0', code: 0}
  ];
  inputRoles: any;
  itemRoles: any;
  loading = false;
  title: string;
  data: any;

  ngOnInit(): void {
    this.inputRoles = new FormGroup({
      id: new FormControl(this.data?.id, []),
      name: new FormControl(null, [Validators.required]),
      code: new FormControl(null, [Validators.required]),
      description: new FormControl(null, []),
      status: new FormControl(null, [Validators.required])
    });
    this.inputRoles.get('status').setValue(true);
    if (this.data) {
      this.inputRoles.patchValue(this.data);
      const status = this.data.status === 1 ? true : false;
      this.inputRoles.get('status').patchValue(status);
    };
  };


  constructor(
    private toastr1: ToastrService,
    public ref: NbDialogRef<RoleUpdateComponent>,
    private toastr: NbToastrService,
    private translate: TranslateService,
    private rolesService: RolesService) {
  }


  submit() {
    this.inputRoles.get('status').patchValue(this.inputRoles.get('status').value ? 1 : 0);
    this.inputRoles.markAllAsTouched();
    if (this.inputRoles.valid) {
      this.loading = true;
      if (this.data == null) {
        this.rolesService.insert(this.inputRoles.value).subscribe(
          (value) => this.ref.close(value),
          (error) => {
            this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
            this.loading = false;
          },
          () => this.loading = false
        );
      } else {
        this.rolesService.update(this.inputRoles.value).subscribe(
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
