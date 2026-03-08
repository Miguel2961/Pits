import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService, HistorialDto } from '../../../services/cliente.service';

@Component({
  selector: 'app-historial',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './historial.component.html',
  styleUrl: './historial.component.css',
})
export class HistorialComponent implements OnInit {
  historial = signal<HistorialDto[]>([]);
  loading = signal(true);

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.clienteService.getMiHistorial().subscribe({
      next: (data) => {
        this.historial.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }
}
