import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../environment';

export interface EspecialidadDto {
  idEspecialidad: number;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class EspecialidadService {
  private readonly api = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getEspecialidades(): Observable<EspecialidadDto[]> {
    return this.http.get<EspecialidadDto[]>(`${this.api}/especialidades`).pipe(
      map((list) => {
        const arr = Array.isArray(list) ? list : [];
        return arr.map((e) => {
          const raw = e as unknown as Record<string, unknown>;
          return {
            idEspecialidad: Number(e.idEspecialidad ?? raw['id_especialidad']),
            nombre: String(e.nombre ?? ''),
          };
        });
      })
    );
  }
}
