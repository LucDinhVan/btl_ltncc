import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {createRequestOption} from '../../shares/utils/request-util';

@Injectable({
  providedIn: 'root'
})
export class ObjectsService {
  constructor(private http: HttpClient) {
  }

  query(): Observable<any> {
    return this.http.get<any[]>(`${environment.apiUrl}/object/getAll`, {
      observe: 'response'
    });
  }

  getAllObjRoleAction(id: any): Observable<any> {
    return this.http.get<any[]>(`${environment.apiUrl}/object/getAllObjRoleAction/${id}`, {
      observe: 'response'
    });
  }

  public updateObjRoleAction(data): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/object/updateObjRoleAction`, data, {
      observe: 'response'
    });
  }

  public doSearch(data: any, req?: any): Observable<any> {
    const options = createRequestOption(req);
    return this.http.post(`${environment.apiUrl}/object/doSearch`, data, {
      params: options,
      observe: 'response'
    });
  }

  public update(data): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/object/update`, data, {
      observe: 'response'
    });
  }

  public insert(data): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/object/insert`, data, {
      observe: 'response'
    });
  }

  public delete(data): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/object/delete`, data, {
      observe: 'response'
    });
  }

  getParent(): Observable<any> {
    return this.http.get<any[]>(`${environment.apiUrl}/object/getParent`, {
      observe: 'response'
    });
  }


  public getTreeParent(): Observable<any> {
    return this.http.post<any[]>(`${environment.apiUrl}/object/getTreeParent`, {}, {
      observe: 'response'
    });
  }

}
