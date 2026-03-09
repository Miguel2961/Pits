import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () =>
      import('./view/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'registro',
    loadComponent: () =>
      import('./view/registro/registro.component').then((m) => m.RegistroComponent),
  },
  {
    path: 'cliente',
    loadComponent: () =>
      import('./view/cliente/cliente-layout.component').then((m) => m.ClienteLayoutComponent),
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'mecanicos', pathMatch: 'full' },
      {
        path: 'mecanicos',
        loadComponent: () =>
          import('./view/cliente/feed-mecanicos/feed-mecanicos.component').then(
            (m) => m.FeedMecanicosComponent
          ),
      },
      {
        path: 'mecanicos/:id',
        loadComponent: () =>
          import('./view/cliente/detalle-mecanico/detalle-mecanico.component').then(
            (m) => m.DetalleMecanicoComponent
          ),
      },
      {
        path: 'solicitar-servicio',
        loadComponent: () =>
          import('./view/cliente/solicitar-servicio/solicitar-servicio.component').then(
            (m) => m.SolicitarServicioComponent
          ),
      },
      {
        path: 'historial',
        loadComponent: () =>
          import('./view/cliente/historial/historial.component').then(
            (m) => m.HistorialComponent
          ),
      },
    ],
  },
  { path: '**', redirectTo: 'login' },
];
