import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css',
})
export class RegistroComponent {
  nombre = '';
  email = '';
  password = '';
  foto = '';
  error = signal<string | null>(null);
  loading = signal(false);

  constructor(
    private auth: AuthService,
    private router: Router,
    protected theme: ThemeService
  ) {}

  onSubmit(): void {
    if (!this.nombre || !this.email || !this.password) {
      this.error.set('Completa los campos obligatorios');
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    this.auth.registro(this.nombre, this.email, this.password, this.foto || undefined).subscribe({
      next: () => {
        this.router.navigate(['/cliente']);
      },
      error: (err) => {
        const msg = err?.error?.message || 'Error al registrar. Intenta de nuevo.';
        this.error.set(msg);
        this.loading.set(false);
      },
    });
  }
}
