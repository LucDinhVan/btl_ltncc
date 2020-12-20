export interface ApiRequest {
  data: any;
}

export interface ApiResponse<T> {
  success: boolean;
  payload?: T;
  error?: any;
}
