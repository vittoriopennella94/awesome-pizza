import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError, Observable, tap, throwError} from 'rxjs';
import {Router} from "@angular/router";

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
export class ManagerService {
    private baseUrl = environment.BASE_URL;

    constructor(private http: HttpClient, private router: Router) {
    }

    get<T>(endpoint: string): Observable<CustomResponse<T>> {
        return this.http.get<CustomResponse<T>>(`${this.baseUrl}/${endpoint}`, DEFAULT_HTTP_OPTIONS).pipe(
            tap(response => {
                if (!response || !response.success) {
                    this.goToErrorPage();
                }
            }),
            catchError(err => {
                this.goToErrorPage();
                return throwError(() => err);
            }));
    }

    post<T>(endpoint: string, body: any): Observable<CustomResponse<T>> {
        return this.http.post<CustomResponse<T>>(`${this.baseUrl}/${endpoint}`, body, DEFAULT_HTTP_OPTIONS).pipe(tap(response => {
                if (!response || !response.success) {
                    this.goToErrorPage();
                }
            }),
            catchError(err => {
                this.goToErrorPage();
                return throwError(() => err);
            }));
    }

    put<T>(endpoint: string, body: any): Observable<CustomResponse<T>> {
        return this.http.put<CustomResponse<T>>(`${this.baseUrl}/${endpoint}`, body, DEFAULT_HTTP_OPTIONS).pipe(tap(response => {
                if (!response || !response.success) {
                    this.goToErrorPage();
                }
            }),
            catchError(err => {
                this.goToErrorPage();
                return throwError(() => err);
            }));
    }

    private goToErrorPage() {
        this.router.navigate(['error']);
    }
}
