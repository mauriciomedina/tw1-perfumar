package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaPerfil extends VistaWeb {

  public VistaPerfil(Page page) {
    super(page);
    page.navigate("http://localhost:8080/spring/perfil");
  }

  public void seleccionarGenero(String genero) {
    this.page.selectOption("#genero", genero);
  }

  public String obtenerGeneroSeleccionado() {
    return (String) this.page.locator("#genero").evaluate("elemento => elemento.value");
  }

  public void darClickEnGuardarCambios() {
    this.darClickEnElElemento("#btn-guardar-perfil");
  }
}
