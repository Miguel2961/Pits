import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment';

export interface ClienteDto {
  idCliente: number;
  email: string;
  nombre: string;
  foto: string | null;
}

export interface VehiculoDto {
  idVehiculo: number;
  carburacion: string;
  placa: string;
  modelo: string;
  año: number;
  tecnoMecanica: string;
  tipo: string;
  color: string;
}

export interface CrearVehiculoRequest {
  carburacion?: string;
  placa: string;
  modelo?: string;
  año?: number;
  tecnoMecanica?: string;
  tipo?: string;
  color?: string;
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

export interface HistorialDto {
  idReporte: number;
  servicio: ServicioDto;
  informacionServicio: string;
}

@Injectable({ providedIn: 'root' })
export class ClienteService {
  private readonly api = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getMe(): Observable<ClienteDto> {
    return this.http.get<ClienteDto>(`${this.api}/clientes/me`);
  }

  updateMe(data: Partial<ClienteDto>): Observable<ClienteDto> {
    return this.http.put<ClienteDto>(`${this.api}/clientes/me`, data);
  }

  getMisVehiculos(): Observable<VehiculoDto[]> {
    return this.http.get<VehiculoDto[]>(`${this.api}/clientes/me/vehiculos`);
  }

  addVehiculo(req: CrearVehiculoRequest): Observable<VehiculoDto> {
    return this.http.post<VehiculoDto>(`${this.api}/clientes/me/vehiculos`, req);
  }

  getMiHistorial(): Observable<HistorialDto[]> {
    return this.http.get<HistorialDto[]>(`${this.api}/clientes/me/historial`);
  }
}
