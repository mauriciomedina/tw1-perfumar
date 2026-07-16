class FooterComponent extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    const anioActual = new Date().getFullYear();

    this.innerHTML = `
      <footer class="footer mt-auto py-5 border-top position-relative overflow-hidden" style="border-color: rgba(0, 0, 0, 0.05) !important; background-color: transparent;">
        
        <!-- Elemento Decorativo (Marca de agua alineada a la derecha) -->
        <div class="decoration-ea d-none d-lg-block" style="position: absolute; top: 50%; left: 65%; transform: translateY(-50%); font-family: 'Bodoni Moda', serif; font-size: 8rem; opacity: 0.03; pointer-events: none; z-index: 0; white-space: nowrap;">
          PerfumAR
        </div>

        <!-- Contenedor del texto (z-index superior para que quede por encima de la decoración) -->
        <div class="container-fluid px-4 position-relative" style="z-index: 1;">
          <div class="d-flex flex-column flex-md-row justify-content-center align-items-center gap-3">
            <span class="brand-title" style="font-size: 1.2rem; cursor: default;">PerfumAR</span>
            <span class="text-muted small mb-0">
              &copy; ${anioActual} Colecci&oacute;n Exclusiva. Todos los derechos reservados.
            </span>
          </div>
        </div>
      </footer>
    `;
  }
}

customElements.define("app-footer", FooterComponent);