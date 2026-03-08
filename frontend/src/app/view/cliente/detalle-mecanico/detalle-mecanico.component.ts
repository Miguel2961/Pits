import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MecanicoService, MecanicoDto } from '../../../services/mecanico.service/mecanico.service';

@Component({
  selector: 'app-detalle-mecanico',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './detalle-mecanico.component.html',
  styleUrl: './detalle-mecanico.component.css',
})
export class DetalleMecanicoComponent implements OnInit {
  mecanico = signal<MecanicoDto | null>(null);
  loading = signal(true);

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private mecanicoService: MecanicoService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.mecanicoService.getMecanicoById(id).subscribe({
      next: (m) => {
        this.mecanico.set(m);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
      },
    });
  }

  solicitarServicio(): void {
    const m = this.mecanico();
    if (m) {
      this.router.navigate(['/cliente/solicitar-servicio'], {
        queryParams: { mecanicoId: m.idMecanico },
      });
    }
  }
}
