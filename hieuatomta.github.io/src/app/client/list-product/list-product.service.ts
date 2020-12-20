import {Injectable} from '@angular/core';
import {ProductDto} from '../dtos/product.dto';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiResponse} from '../../@core/model/api.model';
import {TypesUtilsService} from '../../@core/utils/types-utils.service';

@Injectable()
export class ListProductService {
  [x: string]: any;

  requestUrl: any = {
    getProducts: 'api/products',
    getProductDetail: 'api/products/{productName}'

  };

//   private PRODUCTS_URL = 'api/products';
//   private PRODUCTDETAIL_URL = 'api/products';

  constructor(
    private http: HttpClient,
    private typesUtilsService: TypesUtilsService
  ) {
  }

  getProducts(): Observable<ProductDto[]> {
    return this.http.get<ProductDto[]>(this.requestUrl.getProjectsByUserIdUrl);
  }

  getProductDetail(productId: string): Observable<ApiResponse<ProductDto>> {
    return this.http.get<ApiResponse<ProductDto>>(this.typesUtilsService.replaceParam(this.requestUrl.getProductDetail, {productId}));
  }


}
