import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  email = '';
  password = '';
  error = signal<string | null>(null);
  loading = signal(false);

  constructor(
    private auth: AuthService,
    private router: Router,
    protected theme: ThemeService
  ) {}

  onSubmit(): void {
    if (!this.email || !this.password) {
      this.error.set('Completa todos los campos');
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    this.auth.login(this.email, this.password).subscribe({
      next: () => {
        this.router.navigate(['/cliente']);
      },
      error: () => {
        this.error.set('Email o contraseña incorrectos');
        this.loading.set(false);
      },
    });
  }
}
