import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

export interface CustomResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

const DEFAULT_HTTP_OPTIONS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Access-Control-Allow-Origin': '*'
  })
};

@Injectable({
  providedIn: 'root',
})
export class Manager {
  private http = inject(HttpClient);
  private baseUrl = environment.BASE_URL;

  get<T>(endpoint: string): Observable<CustomResponse<T>> {
    return this.http.get<CustomResponse<T>>(`${this.baseUrl}/${endpoint}`, DEFAULT_HTTP_OPTIONS).pipe();
  }

  post<T>(endpoint: string, body: any): Observable<CustomResponse<T>> {
    return this.http.post<CustomResponse<T>>(`${this.baseUrl}/${endpoint}`, body, DEFAULT_HTTP_OPTIONS).pipe();
  }

  put<T>(endpoint: string, body: any): Observable<CustomResponse<T>> {
    return this.http.put<CustomResponse<T>>(`${this.baseUrl}/${endpoint}`, body, DEFAULT_HTTP_OPTIONS).pipe();
  }
}
