import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { MecanicoService, MecanicoDto } from '../../../services/mecanico.service/mecanico.service';
import { EspecialidadService, EspecialidadDto } from '../../../services/especialidad.service';

@Component({
  selector: 'app-feed-mecanicos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './feed-mecanicos.component.html',
  styleUrl: './feed-mecanicos.component.css',
})
export class FeedMecanicosComponent implements OnInit {
  mecanicos = signal<MecanicoDto[]>([]);
  especialidades = signal<EspecialidadDto[]>([]);
  filtroEspecialidad: number | null = null;
  filtroCiudad = '';
  loading = signal(true);

  constructor(
    private mecanicoService: MecanicoService,
    private especialidadService: EspecialidadService
  ) {}

  ngOnInit(): void {
    this.especialidadService.getEspecialidades().subscribe({
      next: (data) => this.especialidades.set(data),
    });
    this.cargarMecanicos();
  }

  cargarMecanicos(): void {
    this.loading.set(true);
    this.mecanicoService
      .getMecanicos({
        especialidadId: this.filtroEspecialidad ?? undefined,
        ciudad: this.filtroCiudad || undefined,
      })
      .subscribe({
        next: (data) => {
          this.mecanicos.set(data);
          this.loading.set(false);
        },
        error: () => this.loading.set(false),
      });
  }

  aplicarFiltros(): void {
    this.cargarMecanicos();
  }

  limpiarFiltros(): void {
    this.filtroEspecialidad = null;
    this.filtroCiudad = '';
    this.cargarMecanicos();
  }

  renderStars(rating: number | null): string {
    if (rating == null) return 'Sin valoraciones';
    const full = Math.floor(rating);
    const half = rating - full >= 0.5 ? 1 : 0;
    const empty = 5 - full - half;
    return '\u2605'.repeat(full) + (half ? '\u00BD' : '') + '\u2606'.repeat(empty);
  }
}
