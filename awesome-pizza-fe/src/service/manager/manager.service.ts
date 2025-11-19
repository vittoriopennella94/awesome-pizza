import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError, Observable, tap, throwError} from 'rxjs';
import {Router} from "@angular/router";

export interface CustomResponse<T> {
    success: boolean;
    message: string;
    data: T;
}

export class ErrorResponse {
    message: string;
    status: number;

    constructor() {
        this.status = 0;
        this.message = '';
    }
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
                console.log(response);
            }),
            catchError(err => {
                console.log(() => err);
                this.goToErrorPage(err);
                return throwError(() => err);
            }));
    }

    post<T>(endpoint: string, body: any): Observable<CustomResponse<T>> {
        return this.http.post<CustomResponse<T>>(`${this.baseUrl}/${endpoint}`, body, DEFAULT_HTTP_OPTIONS).pipe(tap(response => {
                console.log(response);
            }),
            catchError(err => {
                console.log(() => err);
                this.goToErrorPage(err);
                return throwError(() => err);
            }));
    }

    put<T>(endpoint: string, body: any): Observable<CustomResponse<T>> {
        return this.http.put<CustomResponse<T>>(`${this.baseUrl}/${endpoint}`, body, DEFAULT_HTTP_OPTIONS).pipe(tap(response => {
                console.log(response);
            }),
            catchError(err => {
                console.log(() => err);
                this.goToErrorPage(err);
                return throwError(() => err);
            }));
    }

    private goToErrorPage(error: HttpErrorResponse) {
        if(error && error.status) {
            switch (error.status) {
                case 404:
                    console.log("NOT FOUND");
                    if(error.error && error.error.data){
                        alert(error.error.data.message);
                    }
                    break;
                case 400:
                    console.log("BAD REQUEST");
                    if(error.error && error.error.data){
                        alert(error.error.data.message);
                    }
                    break;
                case 500:
                    console.log("INTERNAL SERVER ERROR");
                    this.router.navigate(['/errore']);
                    break
                default:
                    console.error(error);
                    this.router.navigate(['/errore']);
                    break;
            }
        }
    }
}
