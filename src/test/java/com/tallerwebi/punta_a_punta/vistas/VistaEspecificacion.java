package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaEspecificacion extends VistaWeb {

  public VistaEspecificacion(Page page, Long idPerfume) {
    super(page);
    page.navigate("http://localhost:8080/spring/especificacion?id=" + idPerfume);
  }

  public void darClickEnBotonFavorito() {
    this.darClickEnElElemento("#boton-favorito");
    // El toggle de favorito se hace por fetch: esperamos a que la clase se
    // actualice en el DOM antes de seguir, en vez de leer el estado a ciegas.
    this.page.waitForSelector(
        "#icono-favorito.icono-favorito-lleno",
        new Page.WaitForSelectorOptions().setTimeout(5000)
      );
  }

  public boolean elCorazonEstaRelleno() {
    return Boolean.TRUE.equals(
      this.page.locator("#icono-favorito")
        .evaluate("elemento => elemento.classList.contains('icono-favorito-lleno')")
    );
  }

  public boolean elBotonEstaMarcadoComoFavorito() {
    return Boolean.TRUE.equals(
      this.page.locator("#boton-favorito")
        .evaluate("elemento => elemento.classList.contains('btn-outline-danger')")
    );
  }
}
