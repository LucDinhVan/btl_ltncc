import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {ToastrService} from '../../../@core/mock/toastr-service';
import {FormControl, FormGroup} from '@angular/forms';
import {RoleUpdateComponent} from './role-update/role-update.component';
import {TranslateService} from '@ngx-translate/core';
import {UsersService} from '../../../@core/services/users.service';
import {HttpHeaders} from '@angular/common/http';
import {ConfirmDialogComponent} from '../../../shares/directives/confirm-dialog/confirm-dialog.component';
import {RolesService} from '../../../@core/services/roles.service';
import {MapPopupComponent} from './map-popup/map.popup.component';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-roles',
  styleUrls: ['./roles.component.scss'],
  templateUrl: './roles.component.html',
})
export class RolesComponent implements OnInit {
  ngOnInit(): void {
    this.search(0);
    this.rolesService.doSearch({}).subscribe(res => {
      console.log(res), err => {
        console.log(err);
      };
    });
  }
  isLoad: boolean;

  constructor(
    private toastr: NbToastrService,
    private translate: TranslateService,
    private toastrService: NbToastrService,
    private userService: UsersService,
    private rolesService: RolesService,
    private dialogService: NbDialogService) {
  }

  loading = false;
  listStatus = [
    {name: 'common.status.1', code: 1},
    {name: 'common.status.0', code: 0}
  ];
  rows: Object[];
  page = {
    limit: 5,
    count: 0,
    offset: 0,
  };
  columns = [
    {name: 'common.table.item_number', prop: 'index', flexGrow: 0.3},
    {name: 'common.table.item_roles_name', prop: 'name', flexGrow: 1.5},
    {name: 'common.table.item_roles_code', prop: 'code', flexGrow: 1},
    {name: 'common.table.item_update_time', prop: 'updateTime', flexGrow: 1},
    {name: 'common.table.item_status', prop: 'status', flexGrow: 1},
    {name: 'common.table.item_map_popup', prop: 'editModule', flexGrow: 1},
    {name: 'common.table.item_action', prop: 'action_btn', flexGrow: 1}
  ];

  inputForm = new FormGroup({
    name: new FormControl(null, []),
    code: new FormControl(null, []),
    updateTime: new FormControl(null, []),
    status: new FormControl(null, [])
  });

  pageCallback(pageInfo: { count?: number, pageSize?: number, limit?: number, offset?: number }) {
    this.page.offset = pageInfo.offset;
    this.search(pageInfo.offset);
  }

  editUsers(data) {
    let title;
    if (data == null) {
      title = this.translate.instant('roles.title_add');
    } else {
      title = this.translate.instant('roles.title_edit');
    }
    this.dialogService.open(RoleUpdateComponent, {
      context: {
        title: title,
        data: data,
      },
      dialogClass: 'modal-full',
    }).onClose.subscribe(
      value => {
        if (value) {
          if (data == null) {
            this.toastrService.success(this.translate.instant('roles.content_add_success'),
              this.translate.instant('common.title_notification'));
          } else {
            this.toastrService.success(this.translate.instant('roles.content_edit_success'),
              this.translate.instant('common.title_notification'));
          }
          this.search(0);
        }
      }
    );
  }

  protected onSuccess(data: any | null, headers: HttpHeaders, page: number): void {
    this.page.count = data.totalPages;
    this.page.offset = page || 0;
    this.rows = data.list || [];
  }

  search(pageToLoad: number) {
    this.isLoad = true;
    this.page.offset = pageToLoad;
    this.rolesService.doSearch({
      page: this.page.offset,
      size: this.page.limit
    }, this.inputForm.value).subscribe(
      (res) => {
        this.onSuccess(res.body.data, res.headers, pageToLoad);
      },
      (error) => {
        this.isLoad = false;
      },
      () => this.isLoad = false,
    );
  }


  deleteUsers(data) {
    this.dialogService.open(ConfirmDialogComponent, {
      context: {
        title: this.translate.instant('common.title_notification'),
        message: this.translate.instant('roles.title_delete') + ' ' + data.name
      },
    }).onClose.subscribe(res => {
      if (res) {
        this.isLoad = true;
        this.rolesService.delete(data.id).subscribe(() => {
          this.toastrService.success(this.translate.instant('roles.delete_success'),
            this.translate.instant('common.title_notification'));
          this.search(0);
          this.isLoad = false;
        }, (err) => {
          this.toastrService.success(err.detail),
            this.translate.instant('common.title_notification');
          this.isLoad = false;
        });
      }
    });
  }

  openMapComponent(data) {
    this.dialogService.open(MapPopupComponent, {
      context: {
        title: this.translate.instant('roles.title_map_popup') + ' ' + data.name,
        data: data,
      },
      dialogClass: 'map-modal-full',
      hasScroll: true,
    }).onClose.subscribe(
      value => {
        if (value) {
          this.toastr.success(this.translate.instant('role_object.map_popup_success'),
            this.translate.instant('common.title_notification'));
        }
      },
    );
  }
}
