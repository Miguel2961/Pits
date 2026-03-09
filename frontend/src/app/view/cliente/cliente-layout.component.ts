import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService, ClienteDto } from '../../services/auth.service';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-cliente-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './cliente-layout.component.html',
  styleUrl: './cliente-layout.component.css',
})
export class ClienteLayoutComponent implements OnInit {
  user = signal<ClienteDto | null>(null);

  constructor(
    private auth: AuthService,
    protected theme: ThemeService
  ) {}

  ngOnInit(): void {
    const cached = this.auth.getCurrentUser();
    if (cached) {
      this.user.set(cached);
    } else {
      this.auth.fetchCurrentUser().subscribe({
        next: (u) => this.user.set(u),
      });
    }
  }

  logout(): void {
    this.auth.logout();
  }
}
