import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  forwardRef,
  Input,
  OnInit,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {ToastrService} from '../../../../@core/mock/toastr-service';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {FormControl, FormGroup, NG_VALUE_ACCESSOR, Validators} from '@angular/forms';
import {RolesService} from '../../../../@core/services/roles.service';
import {UsersService} from '../../../../@core/services/users.service';
import {passwordsMatchValidator, validDate} from '../../../../validator';
import {TranslateService} from '@ngx-translate/core';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-user-update',
  styleUrls: ['./user-update.component.scss'],
  templateUrl: './user-update.component.html',
})
export class UserUpdateComponent implements OnInit {
  @Input() value: any;
  @Input() readonly: boolean;
  @ViewChild('inputElement', {static: false}) inputElement: ElementRef;

  listRole = null;
  lstRole1 = [];
  listStatus = [
    {name: 'common.status.1', code: 1},
    {name: 'common.status.0', code: 0}
  ];
  inputUser: any;
  itemRoles: any;
  loading = false;
  title: string;
  data: any;
  showPassword = false;
  showPassword1 = false;
  mask;
  constructor(
    private toastr1: ToastrService,
    public ref: NbDialogRef<UserUpdateComponent>,
    private rolesService: RolesService,
    protected cd: ChangeDetectorRef,
    private toastr: NbToastrService,
    private translate: TranslateService,
    private userService: UsersService) {
  }

  ngOnInit(): void {
    this.listRole = this.data?.roleUser?.split(',').map(item => Number(item));
    this.inputUser = new FormGroup({
      name: new FormControl(this.data?.name, [Validators.required]),
      fullname: new FormControl(this.data?.fullname, [Validators.required]),
      phone: new FormControl(this.data?.phone, [Validators.pattern(/^\d{10}$/)]),
      mail: new FormControl(this.data?.mail, [Validators.required]),
      pass: new FormControl(this.randomPass(10), []),
      rePassword: new FormControl(null, []),
      imageUrl: new FormControl(this.data?.imageUrl, []),
      orBirthUser: new FormControl(this.data?.orBirthUser, [Validators.required]),
      status: new FormControl(this.data?.status, [Validators.required]),
      lstRole: new FormControl(this.listRole, []),
    }, {
      validators: passwordsMatchValidator,
    });
    this.inputUser.get('status').setValue(true);
    if (this.data) {
      this.inputUser.patchValue(this.data);
      const status = this.data.status === 1 ? true : false;
      this.inputUser.get('status').patchValue(status);
    };
    this.rolesService.query().subscribe(res => {
      this.lstRole1 = res.body.data.list;
    }, err => {
      console.log(err);
    });
  };

  randomPass(length) {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
  }

  changeValue() {
    if (!this.cd['destroyed']) {
      this.cd.detectChanges();
    }
  }

  toggleShowPassword(a: number) {
    if (a === 1) {
      this.showPassword = !this.showPassword;
    }
    if (a === 2) {
      this.showPassword1 = !this.showPassword1;
    }
  }

  getInputType(a: number) {
    if (a === 1) {
      if (this.showPassword) {
        return 'text';
      }
    }
    if (a === 2) {
      if (this.showPassword1) {
        return 'text';
      }
    }
    return 'password';
  }

  submit() {
    this.inputUser.get('status').patchValue(this.inputUser.get('status').value ? 1 : 0);
    this.inputUser.markAllAsTouched();
    if (this.inputUser.valid) {
      this.loading = true;
      const data = Object.assign({}, this.inputUser.value);
      data.id = this.data?.id;
      data.listRole = this.inputUser.get('lstRole').value;
      if (this.data == null) {
        this.userService.insert(data).subscribe(
          (value) => this.ref.close(value),
          error => {
            this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
            this.loading = false;
          },
          () => this.loading = false,
        );
      } else {
        this.userService.update(data).subscribe(
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
