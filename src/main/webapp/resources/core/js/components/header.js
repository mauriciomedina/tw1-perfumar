class HeaderComponent extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    // Capturamos los valores de sesión que le vamos a pasar desde el HTML
    const ciudad = this.getAttribute('ciudad') || '';
    const pais = this.getAttribute('pais') || '';

    this.innerHTML = `
    <header class="header-nav">
      <div class="d-flex align-items-center gap-4">
        <div class="d-flex align-items-center gap-2 text-muted">
          <span id="currentWeather" class="label-caps" style="font-size: 10px">Elije tu ciudad</span>
        </div>
        <input type="text" id="cityInput" placeholder="Ciudad" value="${ciudad}" hidden />
        <input type="text" id="countryInput" placeholder="Pais" value="${pais}" hidden />
        <button id="getWeatherBtn" hidden>Buscar</button>
        <div id="weather-info"></div>
        <div class="search-container">
          <span id="buscador" class="material-symbols-outlined text-muted">search</span>
          <input class="search-input" id="inputBuscador" placeholder="Buscar perfume" type="text" />
        </div>
      </div>
      <div class="d-flex align-items-center gap-4">
        <a class="btn btn-link text-decoration-none text-dark p-0 label-caps" href="/spring/listado">Mi ColecciOn</a>
        <div class="d-flex align-items-center gap-3">
          <button class="btn btn-link text-dark p-0">
            <span class="material-symbols-outlined">notifications</span>
          </button>
          <a class="btn btn-link text-decoration-none text-dark p-0 label-caps" href="/spring/perfil">Perfil</a>
        </div>
      </div>
    </header>
    `;
  }
}

customElements.define('app-header', HeaderComponent);