// Posición del punto en la escala Sutil-Intenso por familia (mismo criterio que perfumeDelDia.js)
const INTENSIDAD_RECOMENDACION = {
  "Cítrica":   "20%",
  "Frutal":    "35%",
  "Helecho":   "50%",
  "Amaderada": "70%",
  "Oriental":  "85%"
};

/**
 * Cuenta cuántas veces aparece cada familia olfativa entre los favoritos
 * y devuelve las familias ordenadas de la más repetida a la menos repetida.
 */
export function familiasMasFrecuentes(favoritos) {
  const conteo = {};
  favoritos.forEach(p => {
    if (!p.familiaOlfativa) return;
    conteo[p.familiaOlfativa] = (conteo[p.familiaOlfativa] || 0) + 1;
  });

  return Object.keys(conteo).sort((a, b) => conteo[b] - conteo[a]);
}

/**
 * Elige un perfume del catálogo similar a los favoritos del usuario:
 * busca coincidencias de familia olfativa, excluyendo los que ya son
 * favoritos. Si hay varios candidatos, elige uno al azar (se refresca
 * en cada visita, a diferencia del perfume del día que es fijo).
 */
export function elegirRecomendacionPorFavoritos(catalogo, favoritos) {
  const idsFavoritos = new Set(favoritos.map(p => p.id));
  const disponibles = catalogo.filter(p => !idsFavoritos.has(p.id));

  if (disponibles.length === 0) return null;

  const familiasPrioritarias = familiasMasFrecuentes(favoritos);

  for (const familia of familiasPrioritarias) {
    const candidatos = disponibles.filter(p => p.familiaOlfativa === familia);
    if (candidatos.length > 0) {
      const elegido = candidatos[Math.floor(Math.random() * candidatos.length)];
      return { perfume: elegido, familia };
    }
  }

  // Fallback: ningún match de familia, tiramos cualquier perfume disponible
  const elegido = disponibles[Math.floor(Math.random() * disponibles.length)];
  return { perfume: elegido, familia: null };
}

function renderRecomendacion(perfume, familia) {
  const set = (id, valor) => {
    const el = document.getElementById(id);
    if (el) el.textContent = valor;
  };

  const img = document.getElementById("recomendacion-imagen");
  if (img) {
    img.src = perfume.imagenUrl;
    img.alt = perfume.nombre;
  }

  const motivo = familia
    ? `Porque te gustan los perfumes de familia ${familia}`
    : "Una fragancia que todavía no tenés en tus favoritos";

  set("recomendacion-motivo", motivo);
  set("recomendacion-nombre", perfume.nombre);
  set("recomendacion-marca", perfume.marca);
  set("recomendacion-descripcion", perfume.descripcion);
  set("recomendacion-familia", perfume.familiaOlfativa);
  set("recomendacion-notas", `Notas: ${perfume.notas}`);

  const punto = document.getElementById("recomendacion-punto");
  if (punto) punto.style.left = INTENSIDAD_RECOMENDACION[perfume.familiaOlfativa] ?? "50%";

  const link = document.getElementById("recomendacion-link");
  if (link) link.href = `/spring/especificacion?id=${perfume.id}`;

  const inputFavorito = document.getElementById("recomendacion-form-input");
  if (inputFavorito) inputFavorito.value = perfume.id;

  const seccion = document.getElementById("seccion-recomendacion");
  if (seccion) seccion.style.display = "block";
}

function mostrarSinFavoritos() {
  const vacio = document.getElementById("recomendacion-vacia");
  if (vacio) vacio.style.display = "block";
}

async function cargarRecomendacionPorFavoritos() {
  try {
    const [favoritosRes, catalogoRes] = await Promise.all([
      fetch("/spring/api/favoritos"),
      fetch("/spring/api/perfumes")
    ]);

    if (!favoritosRes.ok) throw new Error(`Favoritos: ${favoritosRes.status}`);
    if (!catalogoRes.ok) throw new Error(`Perfumes: ${catalogoRes.status}`);

    const [favoritos, catalogo] = await Promise.all([
      favoritosRes.json(),
      catalogoRes.json()
    ]);

    if (!favoritos.length) {
      mostrarSinFavoritos();
      return;
    }

    const recomendacion = elegirRecomendacionPorFavoritos(catalogo, favoritos);
    if (!recomendacion) {
      mostrarSinFavoritos();
      return;
    }

    renderRecomendacion(recomendacion.perfume, recomendacion.familia);
  } catch (err) {
    console.error("[RecomendacionFavoritos]", err);
  }
}

document.addEventListener("DOMContentLoaded", cargarRecomendacionPorFavoritos);