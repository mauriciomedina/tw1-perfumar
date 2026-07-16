class HeaderComponent extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    // El clima ya viene calculado por el backend (ControladorClima / AtributosClimaGlobal)
    const clima = this.getAttribute("clima") || "Elije tu ciudad";

    this.innerHTML = `
    <header class="header-nav">
      <div class="d-flex align-items-center gap-3 gap-lg-4">
        <button class="sidebar-toggle-btn d-lg-none" id="sidebarToggleBtn" aria-label="Abrir menú" type="button">
          <span class="material-symbols-outlined">menu</span>
        </button>
        <div class="d-none d-sm-flex align-items-center gap-2 text-muted">
          <span id="currentWeather" class="label-caps" style="font-size: 10px">${clima}</span>
        </div>
        <div class="search-container">
          <span id="buscador" class="material-symbols-outlined text-muted">search</span>
          <input class="search-input" id="inputBuscador" placeholder="Buscar perfume" type="text" />
        </div>
      </div>
      <div class="d-flex align-items-center gap-3 gap-lg-4">
        <a class="btn btn-link text-decoration-none text-dark p-0 label-caps" href="/spring/listado">Mi ColecciOn</a>
        <a class="btn btn-link text-decoration-none text-dark p-0 label-caps" href="/spring/favoritos">Favoritos</a>
        <div class="d-flex align-items-center gap-3">
          <a class="btn btn-link text-decoration-none text-dark p-0 label-caps" href="/spring/perfil">Perfil</a>
        </div>
      </div>
    </header>
    `;

    const inputBuscador = this.querySelector("#inputBuscador");
    if (inputBuscador) {
      inputBuscador.addEventListener("keydown", function (event) {
        if (event.key !== "Enter") return;
        const termino = inputBuscador.value.trim();
        const enHome = window.location.pathname.endsWith("/home");
        if (enHome && typeof window.buscarPerfume === "function") {
          window.buscarPerfume();
        } else {
          window.location.href = `/spring/home?buscar=${encodeURIComponent(termino)}`;
        }
      });
    }

    const botonToggle = this.querySelector("#sidebarToggleBtn");
    if (botonToggle) {
      // Sólo tiene sentido en páginas que incluyen <mi-sidebar>
      if (document.querySelector("mi-sidebar")) {
        botonToggle.addEventListener("click", () => {
          document.body.classList.toggle("sidebar-open");
        });
      } else {
        botonToggle.style.display = "none";
      }
    }
  }
}

customElements.define("app-header", HeaderComponent);