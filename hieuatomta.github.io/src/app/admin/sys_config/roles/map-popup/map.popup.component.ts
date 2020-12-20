import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {LayoutService} from '../../../../@core/utils';
import {NbDialogRef} from '@nebular/theme';
import {TreeviewConfig} from 'ngx-treeview';
import {ObjectsService} from '../../../../@core/services/objects.service';
import {TreeviewItem} from '../../../../shares/directives/tree-picker/ngx-treeview';
import { LoginService } from '../../../../@core/services/login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'ngx-map-popup',
  templateUrl: './map-popup.component.html',
  styleUrls: ['./map-popup.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class MapPopupComponent implements OnInit {
  loading = false;
  title: string;
  data: any;
  dataItems = [];
  isCollapse: boolean = false;
  treeViewConfig = TreeviewConfig.create({
    hasFilter: true,
    hasAllCheckBox: false,
    maxHeight: undefined,
    hasCollapseExpand: false,
  });
  values = [];

  constructor(private layoutService: LayoutService,
              private ref: NbDialogRef<MapPopupComponent>,
              private objectsService: ObjectsService,
              private loginService: LoginService,
              private router: Router) {
  }


  formatDataModule(data, parenId) {
    const arr = [];
    for (let i = 0; i < data.length; i++) {
      const dataItem = data[i];
      if (dataItem.parenId === parenId) {
        let children = [];
        if (dataItem.id != null) {
          children = this.formatDataModule(data, dataItem.id);
        }
        if (children.length > 0) {
          dataItem.children = children;
        } else {
          dataItem.children = null;
        }
        const check = (dataItem?.checked === 1) ? true : false;
        const dataTreeview = new TreeviewItem({
          text: dataItem.name,
          value: dataItem.code,
          children: dataItem.children,
          checked: check,
          collapsed: true,
        });
        arr.push(dataTreeview);
      }
    }
    return arr;
  }


  ngOnInit(): void {
    this.loading = true;
    this.objectsService.getAllObjRoleAction(this.data?.id).subscribe(
      (value) => {
        this.dataItems = this.formatDataModule(value.body.data.list, 0);
      },
      (error) => {
        this.loading = false;
      },
      () => this.loading = false,
    );
    this.layoutService.onCollapse.subscribe(value => this.isCollapse = value);
  }

  submit() {
    this.loading = true;
    const data = [];
    this.values.map(value => {
      const a = value.split('/');
      data.push({idRole: this.data.id, idObject: a[0], idAction: a[1]});
    });
    const req = {
      idRole: this.data.id,
      list: data,
    };

    this.objectsService.updateObjRoleAction(req).subscribe(
      (success) => {
      },
      (error) => {
        this.loading = false;
      },
      () => {
        this.loading = false;
        this.loginService.authenticationcate({}).subscribe(res => {
          if (res.status === 200) {
            const obj = res.body.listObjects;
            localStorage.setItem('objects', JSON.stringify(obj));
          }
        }, err => {
          localStorage.clear();
          this.router.navigate(['auths/login']);
        });
        this.ref.close('success');
      },
    );
  }

  cancel() {
    this.ref.close();
  }

}
