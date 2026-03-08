import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EspecialidadService, EspecialidadDto } from '../../../services/especialidad.service';
import { MecanicoService, MecanicoDto } from '../../../services/mecanico.service/mecanico.service';
import { ClienteService, VehiculoDto, CrearVehiculoRequest } from '../../../services/cliente.service';
import { ServicioService } from '../../../services/servicio.service';

@Component({
  selector: 'app-solicitar-servicio',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './solicitar-servicio.component.html',
  styleUrl: './solicitar-servicio.component.css',
})
export class SolicitarServicioComponent implements OnInit {
  paso = signal(1);
  loading = signal(false);
  error = signal<string | null>(null);
  success = signal(false);

  especialidades = signal<EspecialidadDto[]>([]);
  mecanicos = signal<MecanicoDto[]>([]);
  vehiculos = signal<VehiculoDto[]>([]);
  loadingEspecialidades = signal(true);
  loadingVehiculos = signal(true);
  errorEspecialidades = signal<string | null>(null);
  errorVehiculos = signal<string | null>(null);

  especialidadId: number | null = null;
  descripcion = '';
  mecanicoSeleccionado: MecanicoDto | null = null;
  vehiculoId: number | null = null;

  mostrarFormVehiculo = false;
  nuevoVehiculo: CrearVehiculoRequest = { placa: '' };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private especialidadService: EspecialidadService,
    private mecanicoService: MecanicoService,
    private clienteService: ClienteService,
    private servicioService: ServicioService
  ) {}

  ngOnInit(): void {
    this.loadingEspecialidades.set(true);
    this.especialidadService.getEspecialidades().subscribe({
      next: (data) => {
        this.especialidades.set(Array.isArray(data) ? data : []);
        this.loadingEspecialidades.set(false);
        this.errorEspecialidades.set(null);
      },
      error: () => {
        this.loadingEspecialidades.set(false);
        this.errorEspecialidades.set('No se pudieron cargar las especialidades.');
      },
    });

    this.loadingVehiculos.set(true);
    this.clienteService.getMisVehiculos().subscribe({
      next: (data) => {
        this.vehiculos.set(Array.isArray(data) ? data : []);
        this.loadingVehiculos.set(false);
        this.errorVehiculos.set(null);
      },
      error: () => {
        this.loadingVehiculos.set(false);
        this.errorVehiculos.set('No se pudieron cargar tus vehículos.');
      },
    });

    const mecanicoIdParam = this.route.snapshot.queryParamMap.get('mecanicoId');
    if (mecanicoIdParam) {
      const id = Number(mecanicoIdParam);
      this.mecanicoService.getMecanicoById(id).subscribe({
        next: (m) => {
          this.mecanicoSeleccionado = m;
          if (m.especialidades.length > 0) {
            this.especialidadId = m.especialidades[0].idEspecialidad;
          }
        },
      });
    }
  }

  siguientePaso(): void {
    if (this.paso() === 1) {
      if (!this.especialidadId || !this.vehiculoId || !this.descripcion) {
        this.error.set('Completa todos los campos');
        return;
      }
      this.error.set(null);

      if (this.mecanicoSeleccionado) {
        this.paso.set(3);
        return;
      }

      this.loading.set(true);
      this.mecanicoService.getMecanicos({ especialidadId: this.especialidadId }).subscribe({
        next: (data) => {
          this.mecanicos.set(data);
          this.loading.set(false);
          this.paso.set(2);
        },
        error: () => this.loading.set(false),
      });
    }
  }

  seleccionarMecanico(m: MecanicoDto): void {
    this.mecanicoSeleccionado = m;
    this.paso.set(3);
  }

  volverPaso(p: number): void {
    this.paso.set(p);
    this.error.set(null);
  }

  confirmar(): void {
    if (!this.vehiculoId || !this.mecanicoSeleccionado) return;

    this.loading.set(true);
    this.error.set(null);

    this.servicioService
      .crearServicio({
        idVehiculo: this.vehiculoId,
        idMecanico: this.mecanicoSeleccionado.idMecanico,
        servicioActual: this.descripcion,
      })
      .subscribe({
        next: () => {
          this.loading.set(false);
          this.success.set(true);
        },
        error: () => {
          this.error.set('Error al crear el servicio. Intenta de nuevo.');
          this.loading.set(false);
        },
      });
  }

  irAHistorial(): void {
    this.router.navigate(['/cliente/historial']);
  }

  agregarVehiculo(): void {
    if (!this.nuevoVehiculo.placa) return;
    this.clienteService.addVehiculo(this.nuevoVehiculo).subscribe({
      next: (v) => {
        this.vehiculos.update((list) => [...list, v]);
        this.vehiculoId = v.idVehiculo;
        this.mostrarFormVehiculo = false;
        this.nuevoVehiculo = { placa: '' };
      },
    });
  }
}
