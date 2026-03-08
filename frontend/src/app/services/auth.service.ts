import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { environment } from '../environment';

export interface ClienteDto {
  idCliente: number;
  email: string;
  nombre: string;
  foto: string | null;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  tipo: string;
  expiresIn: number;
  cliente: ClienteDto;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly api = environment.apiUrl + '/auth';
  private currentUser = signal<ClienteDto | null>(null);

  readonly user = this.currentUser.asReadonly();
  readonly isAuthenticated = computed(() => !!this.getToken());

  constructor(private http: HttpClient, private router: Router) {}

  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.api}/login`, { email, password }).pipe(
      tap(res => this.handleAuth(res)),
      catchError(err => throwError(() => err))
    );
  }

  registro(nombre: string, email: string, password: string, foto?: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.api}/registro`, { nombre, email, password, foto }).pipe(
      tap(res => this.handleAuth(res)),
      catchError(err => throwError(() => err))
    );
  }

  refreshToken(): Observable<LoginResponse> {
    const refreshToken = localStorage.getItem('pits_refresh_token');
    if (!refreshToken) {
      return throwError(() => new Error('No refresh token'));
    }
    return this.http.post<LoginResponse>(`${this.api}/refresh`, { refreshToken }).pipe(
      tap(res => this.handleAuth(res)),
      catchError(err => {
        this.clearAuth();
        return throwError(() => err);
      })
    );
  }

  fetchCurrentUser(): Observable<ClienteDto> {
    return this.http.get<ClienteDto>(`${this.api}/me`).pipe(
      tap(user => this.currentUser.set(user)),
      catchError(err => {
        this.clearAuth();
        return throwError(() => err);
      })
    );
  }

  logout(): void {
    this.clearAuth();
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('pits_access_token');
  }

  getRefreshTokenValue(): string | null {
    return localStorage.getItem('pits_refresh_token');
  }

  getCurrentUser(): ClienteDto | null {
    return this.currentUser();
  }

  private handleAuth(res: LoginResponse): void {
    localStorage.setItem('pits_access_token', res.accessToken);
    localStorage.setItem('pits_refresh_token', res.refreshToken);
    this.currentUser.set(res.cliente);
  }

  private clearAuth(): void {
    localStorage.removeItem('pits_access_token');
    localStorage.removeItem('pits_refresh_token');
    this.currentUser.set(null);
  }
}
