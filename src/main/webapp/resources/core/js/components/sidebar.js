class Sidebar extends HTMLElement {
  connectedCallback() {
    this.innerHTML = `
      <aside class="sidebar">
        <div class="mb-5">
          <a href="/spring/bienvenida" class="text-decoration-none text-reset">
            <h1 class="brand-title" style="cursor: pointer;">PerfumAR</h1>
          </a>
          <p class="text-muted small mb-0">Colecci&oacute;n Exclusiva</p>
        </div>
        <nav class="nav flex-column flex-grow-1">

          <a class="nav-link filtro-btn active" href="#" data-familia="TODAS">
            <span class="material-symbols-outlined">select_all</span>
            <span class="label-caps">Todas las Fragancias</span>
          </a>
          <a class="nav-link filtro-btn" href="#" data-familia="CITRIC">
            <span class="material-symbols-outlined">bakery_dining</span>
            <span class="label-caps">C&iacute;tricas</span>
          </a>
          <a class="nav-link filtro-btn" href="#" data-familia="AMADERADA">
            <span class="material-symbols-outlined">forest</span>
            <span class="label-caps">Amaderadas</span>
          </a>
          <a class="nav-link filtro-btn" href="#" data-familia="FRUTAL">
            <span class="material-symbols-outlined">nutrition</span>
            <span class="label-caps">Frutales</span>
          </a>
          <a class="nav-link filtro-btn" href="#" data-familia="HELECHO">
            <span class="material-symbols-outlined">spa</span>
            <span class="label-caps">Helecho</span>
          </a>
          <a class="nav-link filtro-btn" href="#" data-familia="ORIENTAL">
            <span class="material-symbols-outlined">auto_awesome</span>
            <span class="label-caps">Orientales</span>
          </a>
          <a class="nav-link" href="/spring/listado">
          <span class="material-symbols-outlined">collections_bookmark</span>
          <span class="label-caps">Mi Colecci&oacute;n</span>
    </a>
        </nav>
        <div class="mt-auto pt-3 border-top" style="border-color: rgba(0, 0, 0, 0.05) !important">
          <a class="nav-link mb-1" href="perfil.html">
            <span class="material-symbols-outlined">settings</span>
            <span class="label-caps">Configuraci&oacute;n</span>
          </a>
          <a class="nav-link" href="/spring/logout">
            <span class="material-symbols-outlined">logout</span>
            <span class="label-caps">Cerrar sesi&oacute;n</span>
          </a>
        </div>
      </aside>
    `;

    // DETECTORES DE CLICS
    this.querySelectorAll(".filtro-btn").forEach(boton => {
      boton.addEventListener("click", (event) => {
        event.preventDefault();

        // saca el activo al anterior y se lo asigna al nuevo activo
        this.querySelectorAll(".filtro-btn").forEach(b => b.classList.remove("active"));
        boton.classList.add("active");

        //filtra los perfumes
        const familiaBuscada = boton.getAttribute("data-familia");

        if (document.getElementById("galeria-perfumes")) {
          window.dispatchEvent(new CustomEvent("filtrar-perfumes", { detail: familiaBuscada }));
        } else {
          window.location.href = `/spring/home?familia=${familiaBuscada}`;
        }
      });
    });

    // CONTROL DE RUTA, si venis de otra pagina te trae denuevo
    const parametros = new URLSearchParams(window.location.search);
    const familiaUrl = parametros.get("familia");
    
    if (familiaUrl) {
      this.querySelectorAll(".filtro-btn").forEach(b => {
        if (b.getAttribute("data-familia") === familiaUrl) {
          this.querySelectorAll(".filtro-btn").forEach(btn => btn.classList.remove("active"));
          b.classList.add("active");
        }
      });
    }
  }
}

customElements.define("mi-sidebar", Sidebar);