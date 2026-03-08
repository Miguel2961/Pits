import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-mecanico-legacy',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `<p>Este componente fue reemplazado. <a routerLink="/cliente/mecanicos">Ir al feed de mecánicos</a></p>`,
})
export class MecanicoComponent {}
