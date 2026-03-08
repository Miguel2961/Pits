import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environment';

export interface EspecialidadDto {
  idEspecialidad: number;
  nombre: string;
}

export interface MecanicoDto {
  idMecanico: number;
  nombre: string;
  contacto: string;
  certificacion: string | null;
  edad: number;
  experiencia: number;
  ciudad: string;
  foto: string | null;
  disponible: boolean;
  rating: number | null;
  cantidadValoraciones: number;
  especialidades: EspecialidadDto[];
}

@Injectable({ providedIn: 'root' })
export class MecanicoService {
  private readonly api = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getMecanicos(filters?: {
    especialidadId?: number;
    ciudad?: string;
    disponible?: boolean;
  }): Observable<MecanicoDto[]> {
    let params = new HttpParams();
    if (filters?.especialidadId != null) {
      params = params.set('especialidadId', filters.especialidadId);
    }
    if (filters?.ciudad) {
      params = params.set('ciudad', filters.ciudad);
    }
    if (filters?.disponible != null) {
      params = params.set('disponible', filters.disponible);
    }
    return this.http.get<MecanicoDto[]>(`${this.api}/mecanicos`, { params });
  }

  getMecanicoById(id: number): Observable<MecanicoDto> {
    return this.http.get<MecanicoDto>(`${this.api}/mecanicos/${id}`);
  }
}
