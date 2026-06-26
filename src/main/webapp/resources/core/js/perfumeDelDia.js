const CLIMA_API_KEY = "17ba018d5a299310d2a08723910a7584";

// Posición del punto en la escala Sutil-Intenso por familia
const INTENSIDAD = {
  "Cítrica":   "20%",
  "Frutal":    "35%",
  "Helecho":   "50%",
  "Amaderada": "70%",
  "Oriental":  "85%"
};

/**
 * Devuelve las familias olfativas recomendadas (en orden de prioridad)
 * según los datos meteorológicos actuales.
 */
export function determinarFamilias(weatherData) {
  const temp      = weatherData.main.feels_like;
  const humidity  = weatherData.main.humidity;
  const viento    = weatherData.wind.speed;
  const condicion = weatherData.weather[0].main;
  const nubes     = weatherData.clouds.all;

  // Condiciones atmosféricas (mayor prioridad)
  if (condicion === "Thunderstorm")                    return ["Oriental", "Amaderada"];
  if (condicion === "Rain" || condicion === "Drizzle") return ["Oriental", "Amaderada"];
  if (condicion === "Snow")                            return ["Oriental", "Amaderada"];

  // Viento fuerte: necesitan más proyección
  if (viento > 7) return ["Oriental", "Amaderada"];

  // Calor húmedo: solo fragancias ligeras
  if (humidity > 75 && temp > 25) return ["Cítrica"];

  // Muy nublado
  if (nubes > 70) return ["Amaderada", "Helecho"];

  // Por temperatura (sensación térmica)
  if (temp > 28)  return ["Cítrica", "Frutal"];
  if (temp >= 20) return ["Helecho", "Frutal"];
  if (temp >= 12) return ["Amaderada", "Helecho"];
  return ["Oriental", "Amaderada"];
}

/**
 * Dado el catálogo y las familias priorizadas, elige un perfume
 * de forma determinística según el día (mismo perfume todo el día).
 */
export function elegirPerfumeDelDia(perfumes, familias) {
  for (const familia of familias) {
    const candidatos = perfumes.filter(p => p.familiaOlfativa === familia);
    if (candidatos.length > 0) {
      const hoy = new Date();
      const seed = hoy.getFullYear() * 10000 + (hoy.getMonth() + 1) * 100 + hoy.getDate();
      return candidatos[seed % candidatos.length];
    }
  }
  // Fallback: cualquier perfume del catálogo
  return perfumes[new Date().getDate() % perfumes.length];
}

function renderPerfumeDelDia(perfume, weatherData) {
  const condicion = weatherData.weather[0].description;
  const temp      = Math.round(weatherData.main.feels_like);

  const set = (id, valor) => {
    const el = document.getElementById(id);
    if (el) el.textContent = valor;
  };

  const img = document.getElementById("pddia-imagen");
  if (img) {
    img.onerror = () => { img.onerror = null; img.src = "/spring/img/sauvage.png"; };
    img.src = perfume.imagenUrl;
    img.alt = perfume.nombre;
  }

  set("pddia-clima-info", `${condicion.charAt(0).toUpperCase() + condicion.slice(1)} · ${temp}°C`);
  set("pddia-nombre",      perfume.nombre);
  set("pddia-marca",       perfume.marca);
  set("pddia-descripcion", perfume.descripcion);
  set("pddia-familia",     perfume.familiaOlfativa);
  set("pddia-notas",       `Notas: ${perfume.notas}`);

  const punto = document.getElementById("pddia-punto");
  if (punto) punto.style.left = INTENSIDAD[perfume.familiaOlfativa] ?? "50%";

  const input = document.getElementById("pddia-form-input");
  if (input) input.value = perfume.id;
}

async function cargarPerfumeDelDia() {
  const cityEl    = document.getElementById("cityInput");
  const countryEl = document.getElementById("countryInput");

  const city    = cityEl?.value.trim()           ?? "";
  const country = countryEl?.value.trim().toUpperCase() ?? "";

  if (!city || !country) return;

  const weatherUrl = `https://api.openweathermap.org/data/2.5/weather?q=${encodeURIComponent(city)},${encodeURIComponent(country)}&appid=${CLIMA_API_KEY}&units=metric&lang=es`;

  try {
    const [weatherRes, perfumesRes] = await Promise.all([
      fetch(weatherUrl),
      fetch("/spring/api/perfumes")
    ]);

    if (!weatherRes.ok)  throw new Error(`Clima: ${weatherRes.status}`);
    if (!perfumesRes.ok) throw new Error(`Perfumes: ${perfumesRes.status}`);

    const [weatherData, perfumes] = await Promise.all([
      weatherRes.json(),
      perfumesRes.json()
    ]);

    const familias = determinarFamilias(weatherData);
    const perfume  = elegirPerfumeDelDia(perfumes, familias);

    renderPerfumeDelDia(perfume, weatherData);
  } catch (err) {
    console.error("[PerfumeDelDia]", err);
  }
}

document.addEventListener("DOMContentLoaded", cargarPerfumeDelDia);
