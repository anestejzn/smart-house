import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    request = request.clone({
      withCredentials: true,
      setHeaders: {
        "Authorization": `Bearer ${sessionStorage.getItem("token") || ''}`,
        "Access-Control-Allow-Credentials": "true"
      }
    });
    return next.handle(request);
  }
}
