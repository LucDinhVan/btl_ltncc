import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {ToastrService} from '../../../@core/mock/toastr-service';
import {FormControl, FormGroup} from '@angular/forms';
import {TranslateService} from '@ngx-translate/core';
import {UsersService} from '../../../@core/services/users.service';
import {HttpHeaders} from '@angular/common/http';
import {ConfirmDialogComponent} from '../../../shares/directives/confirm-dialog/confirm-dialog.component';
import {SizeService} from '../../../@core/services/size.service';
import {LogsService} from '../../../@core/services/logs.service';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-users',
  styleUrls: ['./logs.component.scss'],
  templateUrl: './logs.component.html',
})
export class LogsComponent implements OnInit {
  ngOnInit(): void {
    this.search(0);
    this.logsService.doSearch({}).subscribe(res => {
      console.log(res), err => {
        console.log(err);
      };
    });
  }

  constructor(
    private toastr: ToastrService,
    private translate: TranslateService,
    private toastrService: NbToastrService,
    private userService: UsersService,
    private logsService: LogsService) {
  }

  isLoad: boolean;
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
    {name: 'common.table.item_logs_name', prop: 'userImpact', flexGrow: 1.5},
    {name: 'common.table.item_action', prop: 'codeAction', flexGrow: 1.5},
    {name: 'common.table.item_description', prop: 'content', flexGrow: 1.5},
    {name: 'common.table.item_log_ip', prop: 'ip', flexGrow: 1},
    {name: 'common.table.item_name_client', prop: 'nameClient', flexGrow: 1},
    {name: 'common.table.item_impact_time', prop: 'impactTime', flexGrow: 1},
  ];

  inputForm = new FormGroup({
    userImpact: new FormControl(null, []),
    codeAction: new FormControl(null, []),
    content: new FormControl(null, []),
    ip: new FormControl(null, []),
    impactTime: new FormControl(null, [])
  });

  pageCallback(pageInfo: { count?: number, pageSize?: number, limit?: number, offset?: number }) {
    this.page.offset = pageInfo.offset;
    this.search(pageInfo.offset);
  }

  protected onSuccess(data: any | null, headers: HttpHeaders, page: number): void {
    this.page.count = data.totalPages;
    this.page.offset = page || 0;
    this.rows = data.list || [];
  }

  search(pageToLoad: number) {
    this.isLoad = true;
    this.page.offset = pageToLoad;
    this.logsService.doSearch({
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

}
