import {Component, OnDestroy, OnInit} from '@angular/core';
import {NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService} from '@nebular/theme';

import {UserData} from '../../../@core/data/users';
import {LayoutService} from '../../../@core/utils';
import {map, takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  user: any;
  openMenu = false;

  themes = [
    {
      value: 'cosmic',
      name: 'Cosmic',
    },
    {
      value: 'default',
      name: 'Light',
    },
    {
      value: 'dark',
      name: 'Dark',
    },
  ];


  languages = [
    {
      value: 'en',
      name: 'English',
    },
    {
      value: 'vi',
      name: 'Vietnamese',
    },
  ];

  getLanguage() {
    let language = localStorage.getItem('languageName');
    if (language === undefined || language === null) {
      language = 'vi';
    }

    return language;

  }

  currentTheme = 'cosmic';
  language = 'vi';
  currentLanguage = this.getLanguage();

  menuClick(e) {
    console.log(e);
    if (e.menuId === 3) {
      localStorage.clear();
      this.router.navigate(['auths/login']);
    }
    if (e.menuId === 2) {
      console.log('Thực hiện mở popup đổi mật khẩu');
    }
    if (e.menuId === 1) {
      console.log('Thực hiện mở popup thông tin cá nhân');
    }
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['auths/login']);
  }

  userMenu = [
    {menuId: 1, title: 'Thông tin cá nhân'},
    {menuId: 2, title: 'Đổi mật khẩu'},
    {menuId: 3, title: 'Đăng xuất'},
  ];

  constructor(private sidebarService: NbSidebarService,
              private menuService: NbMenuService,
              private themeService: NbThemeService,
              private userService: UserData,
              private layoutService: LayoutService,
              private breakpointService: NbMediaBreakpointsService,
              private translate: TranslateService,
              public router: Router) {
  }


  ngOnInit() {
    this.user = localStorage.getItem('users');
    this.currentTheme = this.themeService.currentTheme;
    const {xl} = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);
    this.menuService.onItemClick().subscribe((event) => {
      this.menuClick(event.item);
    });
    this.themeService.onThemeChange()
      .pipe(
        map(({name}) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  changeTheme(themeName: string) {
    this.themeService.changeTheme(themeName);
  }

  changeLanguage(languageName: string) {
    console.log(languageName);
    localStorage.setItem('languageName', languageName);
    this.translate.use(languageName);

  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();

    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }

}
