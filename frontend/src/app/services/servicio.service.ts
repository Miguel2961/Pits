import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment';

export interface CrearServicioRequest {
  idVehiculo: number;
  idMecanico: number;
  servicioActual: string;
  reporte?: string;
}

export interface ServicioDto {
  idServicio: number;
  idVehiculo: number;
  placaVehiculo: string;
  idMecanico: number;
  nombreMecanico: string;
  servicioActual: string;
  reporte: string;
}

@Injectable({ providedIn: 'root' })
export class ServicioService {
  private readonly api = environment.apiUrl;

  constructor(private http: HttpClient) {}

  crearServicio(req: CrearServicioRequest): Observable<ServicioDto> {
    return this.http.post<ServicioDto>(`${this.api}/servicios`, req);
  }
}
