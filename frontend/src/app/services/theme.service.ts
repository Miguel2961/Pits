import { Injectable, signal, computed, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export type Theme = 'dark' | 'light';

const STORAGE_KEY = 'pits_theme';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly platformId = inject(PLATFORM_ID);
  private readonly isBrowser = () => isPlatformBrowser(this.platformId);

  private readonly themeSignal = signal<Theme>('dark');

  readonly theme = this.themeSignal.asReadonly();
  readonly isDark = computed(() => this.themeSignal() === 'dark');
  readonly isLight = computed(() => this.themeSignal() === 'light');

  constructor() {
    if (this.isBrowser()) {
      this.themeSignal.set(this.loadStoredTheme());
      this.applyTheme(this.themeSignal());
    }
  }

  setTheme(theme: Theme): void {
    this.themeSignal.set(theme);
    this.applyTheme(theme);
    if (this.isBrowser()) {
      try {
        localStorage.setItem(STORAGE_KEY, theme);
      } catch {}
    }
  }

  toggleTheme(): void {
    const next = this.themeSignal() === 'dark' ? 'light' : 'dark';
    this.setTheme(next);
  }

  private loadStoredTheme(): Theme {
    if (!this.isBrowser()) return 'dark';
    try {
      const stored = localStorage.getItem(STORAGE_KEY) as Theme | null;
      if (stored === 'light' || stored === 'dark') return stored;
    } catch {}
    return 'dark';
  }

  private applyTheme(theme: Theme): void {
    if (this.isBrowser() && typeof document !== 'undefined') {
      document.documentElement.setAttribute('data-theme', theme);
    }
  }
}
