package com.tallerwebi.punta_a_punta;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import com.tallerwebi.punta_a_punta.vistas.VistaNuevoUsuario;
import com.tallerwebi.punta_a_punta.vistas.VistaPerfil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VistaPerfilE2E {

  static Playwright playwright;
  static Browser browser;
  BrowserContext context;
  Page page;

  private static final String EMAIL = "perfil-e2e@unlam.edu.ar";
  private static final String CLAVE = "123456";

  @BeforeAll
  static void abrirNavegador() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch();
  }

  @AfterAll
  static void cerrarNavegador() {
    playwright.close();
  }

  @BeforeEach
  void crearContextoYRegistrarUsuario() {
    ReiniciarDB.limpiarBaseDeDatos();

    context = browser.newContext();
    page = context.newPage();

    VistaLogin vistaLogin = new VistaLogin(page);
    vistaLogin.darClickEnRegistrarse();

    VistaNuevoUsuario vistaNuevoUsuario = new VistaNuevoUsuario(page);
    vistaNuevoUsuario.escribirNombre("Usuario Perfil");
    vistaNuevoUsuario.escribirEMAIL(EMAIL);
    vistaNuevoUsuario.escribirClave(CLAVE);
    vistaNuevoUsuario.seleccionarPais("AR");
    vistaNuevoUsuario.seleccionarCiudad("Buenos Aires");
    vistaNuevoUsuario.seleccionarGenero("Masculino");
    vistaNuevoUsuario.darClickEnRegistrarme();

    VistaLogin vistaLoginParaIniciarSesion = new VistaLogin(page);
    vistaLoginParaIniciarSesion.escribirEMAIL(EMAIL);
    vistaLoginParaIniciarSesion.escribirClave(CLAVE);
    vistaLoginParaIniciarSesion.darClickEnIniciarSesion();
  }

  @AfterEach
  void cerrarContexto() {
    context.close();
  }

  @Test
  void deberiaMostrarElGeneroCargadoEnElRegistro() {
    VistaPerfil vistaPerfil = new VistaPerfil(page);

    assertThat(vistaPerfil.obtenerGeneroSeleccionado(), equalTo("Masculino"));
  }

  @Test
  void deberiaPermitirModificarElGeneroYPersistirElCambio() {
    VistaPerfil vistaPerfil = new VistaPerfil(page);

    vistaPerfil.seleccionarGenero("Femenino");
    vistaPerfil.darClickEnGuardarCambios();

    VistaPerfil vistaPerfilRecargada = new VistaPerfil(page);
    assertThat(vistaPerfilRecargada.obtenerGeneroSeleccionado(), equalTo("Femenino"));
  }
}
