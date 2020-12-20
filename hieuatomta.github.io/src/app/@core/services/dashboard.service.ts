import {Injectable} from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  currentScreen: Subject<any> = new BehaviorSubject(null);
  currentProfile: Subject<any> = new BehaviorSubject(null);
  currentTheme: Subject<any> = new BehaviorSubject(null);
  openSearchBar = new Subject();
}
