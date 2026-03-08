import { HttpInterceptorFn, HttpErrorResponse, HttpRequest, HttpHandlerFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { catchError, switchMap, throwError } from 'rxjs';

let isRefreshing = false;

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);

  const cloned = addToken(req, auth.getToken());

  return next(cloned).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && !req.url.includes('/auth/refresh') && !req.url.includes('/auth/login')) {
        return handleRefresh(req, next, auth);
      }
      return throwError(() => error);
    })
  );
};

function addToken(req: HttpRequest<unknown>, token: string | null): HttpRequest<unknown> {
  if (!token) return req;
  return req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
}

function handleRefresh(req: HttpRequest<unknown>, next: HttpHandlerFn, auth: AuthService) {
  if (isRefreshing) {
    return throwError(() => new Error('Already refreshing'));
  }

  isRefreshing = true;

  return auth.refreshToken().pipe(
    switchMap(() => {
      isRefreshing = false;
      return next(addToken(req, auth.getToken()));
    }),
    catchError(err => {
      isRefreshing = false;
      auth.logout();
      return throwError(() => err);
    })
  );
}
