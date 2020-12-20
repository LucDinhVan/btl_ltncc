import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {createRequestOption} from '../../shares/utils/request-util';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ObjectActionService {
  constructor(private http: HttpClient) {
  }

  query(): Observable<any> {
    return this.http.get<any[]>(`${environment.apiUrl}/objectAction/getAll`, {
      observe: 'response'
    });
  }

  public doSearch(data: any, req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.post(`${environment.apiUrl}/objectAction/doSearch`, data, {
      params: options,
      observe: 'response'
    });
  }

  public insert(data): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/objectAction/insert`, data, {
      observe: 'response'
    });
  }

  public delete(body?: any): Observable<any> {
    return this.http.post(`${environment.apiUrl}/objectAction/delete`, body, {
      observe: 'response'
    })
  }
}
