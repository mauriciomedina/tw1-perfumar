package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class VistaNuevoUsuario extends VistaWeb {

  public VistaNuevoUsuario(Page page) {
    super(page);
  }

  public void escribirNombre(String nombre) {
    this.escribirEnElElemento("#nombre", nombre);
  }

  public void escribirEMAIL(String email) {
    this.escribirEnElElemento("#email", email);
  }

  public void escribirClave(String clave) {
    this.escribirEnElElemento("#password", clave);
  }

  public void seleccionarPais(String codigoPais) {
    // Las <option> de un <select> no cuentan como "visibles" para Playwright,
    // así que esperamos que estén presentes en el DOM en vez de visibles.
    this.page.waitForSelector(
        "#pais option[value='" + codigoPais + "']",
        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED)
      );
    this.page.selectOption("#pais", codigoPais);
  }

  public void seleccionarCiudad(String ciudad) {
    this.page.waitForSelector(
        "#ciudad option[value='" + ciudad + "']",
        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED)
      );
    this.page.selectOption("#ciudad", ciudad);
  }

  public void seleccionarGenero(String genero) {
    this.page.selectOption("#genero", genero);
  }

  public void darClickEnRegistrarme() {
    this.darClickEnElElemento("#btn-registrarme");
  }

  public String obtenerMensajeDeError() {
    return this.obtenerTextoDelElemento("p.alert.alert-danger");
  }
}
