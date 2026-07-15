const PAISES_CIUDADES = {
  // Sudamérica
  AR: { nombre: "Argentina", ciudades: ["Buenos Aires", "Córdoba", "Rosario", "Mendoza", "La Plata"] },
  BR: { nombre: "Brasil", ciudades: ["São Paulo", "Rio de Janeiro", "Brasília", "Salvador", "Fortaleza"] },
  CL: { nombre: "Chile", ciudades: ["Santiago", "Valparaíso", "Concepción", "La Serena", "Antofagasta"] },
  CO: { nombre: "Colombia", ciudades: ["Bogotá", "Medellín", "Cali", "Barranquilla", "Cartagena"] },
  PE: { nombre: "Perú", ciudades: ["Lima", "Arequipa", "Trujillo", "Cusco", "Chiclayo"] },
  UY: { nombre: "Uruguay", ciudades: ["Montevideo", "Salto", "Punta del Este", "Paysandú", "Maldonado"] },
  VE: { nombre: "Venezuela", ciudades: ["Caracas", "Maracaibo", "Valencia", "Barquisimeto", "Maracay"] },

  // Norteamérica
  US: { nombre: "Estados Unidos", ciudades: ["New York", "Los Angeles", "Chicago", "Miami", "Houston"] },
  CA: { nombre: "Canadá", ciudades: ["Toronto", "Vancouver", "Montreal", "Calgary", "Ottawa"] },
  MX: { nombre: "México", ciudades: ["Ciudad de México", "Guadalajara", "Monterrey", "Puebla", "Tijuana"] },
  CU: { nombre: "Cuba", ciudades: ["La Habana", "Santiago de Cuba", "Camagüey", "Holguín", "Trinidad"] },
  PA: { nombre: "Panamá", ciudades: ["Ciudad de Panamá", "Colón", "David", "Santiago", "Chitré"] },
  CR: { nombre: "Costa Rica", ciudades: ["San José", "Alajuela", "Cartago", "Heredia", "Limón"] },
  GT: { nombre: "Guatemala", ciudades: ["Ciudad de Guatemala", "Quetzaltenango", "Antigua Guatemala", "Escuintla", "Cobán"] },

  // Europa
  ES: { nombre: "España", ciudades: ["Madrid", "Barcelona", "Valencia", "Sevilla", "Bilbao"] },
  FR: { nombre: "Francia", ciudades: ["París", "Marsella", "Lyon", "Toulouse", "Niza"] },
  DE: { nombre: "Alemania", ciudades: ["Berlín", "Munich", "Hamburgo", "Colonia", "Frankfurt"] },
  IT: { nombre: "Italia", ciudades: ["Roma", "Milan", "Nápoles", "Turín", "Florencia"] },
  GB: { nombre: "Reino Unido", ciudades: ["Londres", "Manchester", "Birmingham", "Liverpool", "Edimburgo"] },
  PT: { nombre: "Portugal", ciudades: ["Lisboa", "Oporto", "Coímbra", "Braga", "Faro"] },
  NL: { nombre: "Países Bajos", ciudades: ["Amsterdam", "Rotterdam", "The Hague", "Utrecht", "Eindhoven"] },

  // Asia
  CN: { nombre: "China", ciudades: ["Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Chengdu"] },
  JP: { nombre: "Japón", ciudades: ["Tokio", "Osaka", "Kioto", "Yokohama", "Nagoya"] },
  IN: { nombre: "India", ciudades: ["New Delhi", "Bombay", "Bangalore", "Calcuta", "Chennai"] },
  KR: { nombre: "Corea del Sur", ciudades: ["Seúl", "Busan", "Incheon", "Daegu", "Daejeon"] },
  TH: { nombre: "Tailandia", ciudades: ["Bangkok", "Chiang Mai", "Phuket", "Pattaya", "Nonthaburi"] },
  AE: { nombre: "Emiratos Árabes Unidos", ciudades: ["Dubai", "Abu Dabi", "Sharjah", "Ajman", "Al Ain"] },
  TR: { nombre: "Turquía", ciudades: ["Istanbul", "Ankara", "Esmirna", "Bursa", "Antalya"] },

  // África
  EG: { nombre: "Egipto", ciudades: ["Cairo", "Alexandria", "Giza", "Luxor", "Asuán"] },
  ZA: { nombre: "Sudáfrica", ciudades: ["Johannesburgo", "Cape Town", "Durban", "Pretoria", "Port Elizabeth"] },
  MA: { nombre: "Marruecos", ciudades: ["Casablanca", "Rabat", "Marrakech", "Fez", "Tangier"] },
  NG: { nombre: "Nigeria", ciudades: ["Lagos", "Abuja", "Kano", "Ibadan", "Port Harcourt"] },
  KE: { nombre: "Kenia", ciudades: ["Nairobi", "Mombasa", "Kisumu", "Nakuru", "Eldoret"] },
  DZ: { nombre: "Argelia", ciudades: ["Argel", "Orán", "Constantina", "Annaba", "Blida"] },
  ET: { nombre: "Etiopía", ciudades: ["Adís Abeba", "Dire Dawa", "Mekele", "Gondar", "Awasa"] },

  // Oceanía
  AU: { nombre: "Australia", ciudades: ["Sydney", "Melbourne", "Brisbane", "Perth", "Adelaida"] },
  NZ: { nombre: "Nueva Zelanda", ciudades: ["Auckland", "Wellington", "Christchurch", "Hamilton", "Dunedin"] },
  FJ: { nombre: "Fiyi", ciudades: ["Suva", "Nadi", "Lautoka", "Labasa", "Ba"] },
  PG: { nombre: "Papúa Nueva Guinea", ciudades: ["Port Moresby", "Lae", "Mount Hagen", "Madang", "Wewak"] },
  WS: { nombre: "Samoa", ciudades: ["Apia", "Vaitele", "Siusega", "Leulumoega", "Safotu"] },
  VU: { nombre: "Vanuatu", ciudades: ["Port Vila", "Luganville", "Norsup", "Isangel", "Sola"] },
  SB: { nombre: "Islas Salomón", ciudades: ["Honiara", "Auki", "Gizo", "Buala", "Kirakira"] },
};

function poblarCiudades(selectCiudad, codigoPais, ciudadPreseleccionada) {
  const pais = PAISES_CIUDADES[codigoPais];

  if (!pais) {
    selectCiudad.innerHTML = "<option value=\"\">Seleccione un país primero</option>";
    selectCiudad.disabled = true;
    return;
  }

  selectCiudad.disabled = false;
  selectCiudad.innerHTML = pais.ciudades
    .map((ciudad) => {
      const seleccionada = ciudad === ciudadPreseleccionada ? " selected" : "";
      return `<option value="${ciudad}"${seleccionada}>${ciudad}</option>`;
    })
    .join("");
}

function inicializarSelectPaisCiudad(idSelectPais, idSelectCiudad) {
  const selectPais = document.getElementById(idSelectPais);
  const selectCiudad = document.getElementById(idSelectCiudad);
  if (!selectPais || !selectCiudad) return;

  const paisActual = selectPais.dataset.actual || "";
  const ciudadActual = selectCiudad.dataset.actual || "";

  const opcionesPaises = Object.keys(PAISES_CIUDADES)
    .map((codigo) => {
      const seleccionado = codigo === paisActual ? " selected" : "";
      return `<option value="${codigo}"${seleccionado}>${PAISES_CIUDADES[codigo].nombre}</option>`;
    })
    .join("");

  selectPais.innerHTML = "<option value=\"\">Seleccione un país</option>" + opcionesPaises;

  poblarCiudades(selectCiudad, paisActual, ciudadActual);

  selectPais.addEventListener("change", () => {
    poblarCiudades(selectCiudad, selectPais.value, "");
  });
}

window.inicializarSelectPaisCiudad = inicializarSelectPaisCiudad;