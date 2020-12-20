import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ToastrService} from '../../../../@core/mock/toastr-service';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActionService} from '../../../../@core/services/action.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-action-update',
  styleUrls: ['./action-update.component.scss'],
  templateUrl: './action-update.component.html',
})
export class ActionUpdateComponent implements OnInit {
  lstRole = [];
  listStatus = [
    {name: 'common.status.1', code: 1},
    {name: 'common.status.0', code: 0}
  ];
  inputAction: any;
  itemRoles: any;
  loading = false;
  title: string;
  data: any;

  ngOnInit(): void {
    this.inputAction = new FormGroup({
      id: new FormControl(this.data?.id, []),
      name: new FormControl(null, [Validators.required]),
      code: new FormControl(null, [Validators.required]),
      description: new FormControl(null, []),
      status: new FormControl(null, [Validators.required])
    });
    this.inputAction.get('status').setValue(true);
    if (this.data) {
      this.inputAction.patchValue(this.data);
      const status = this.data.status === 1 ? true : false;
      this.inputAction.get('status').patchValue(status);
    };
  };


  constructor(
    private toastr1: ToastrService,
    private toastr: NbToastrService,
    private translate: TranslateService,
    public ref: NbDialogRef<ActionUpdateComponent>,
    private actionService: ActionService) {
  }


  submit() {
    this.inputAction.get('status').patchValue(this.inputAction.get('status').value ? 1 : 0);
    this.inputAction.markAllAsTouched();
    if (this.inputAction.valid) {
      this.loading = true;
      if (this.data == null) {
        this.actionService.insert(this.inputAction.value).subscribe(
          (value) => this.ref.close(value),
          (error) => {
            this.toastr.danger(error.error.detail, this.translate.instant('common.title_notification'));
            this.loading = false;
          },
          () => this.loading = false
        );
      } else {
        this.actionService.update(this.inputAction.value).subscribe(
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
