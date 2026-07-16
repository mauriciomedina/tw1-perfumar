package com.tallerwebi.punta_a_punta;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaEspecificacion;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import com.tallerwebi.punta_a_punta.vistas.VistaNuevoUsuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VistaFavoritosE2E {

  static Playwright playwright;
  static Browser browser;
  BrowserContext context;
  Page page;

  private static final String EMAIL = "favoritos-e2e@unlam.edu.ar";
  private static final String CLAVE = "123456";
  private static final Long ID_PERFUME = 1L;

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
    vistaNuevoUsuario.escribirNombre("Usuario Favoritos");
    vistaNuevoUsuario.escribirEMAIL(EMAIL);
    vistaNuevoUsuario.escribirClave(CLAVE);
    vistaNuevoUsuario.seleccionarPais("AR");
    vistaNuevoUsuario.seleccionarCiudad("Buenos Aires");
    vistaNuevoUsuario.seleccionarGenero("Otro");
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
  void elCorazonNoDeberiaEstarRellenoAntesDeMarcarComoFavorito() {
    VistaEspecificacion vistaEspecificacion = new VistaEspecificacion(page, ID_PERFUME);

    assertThat(vistaEspecificacion.elCorazonEstaRelleno(), is(false));
  }

  @Test
  void alHacerClickEnFavoritoElCorazonDeberiaQuedarRellenoYElBotonRojo() {
    VistaEspecificacion vistaEspecificacion = new VistaEspecificacion(page, ID_PERFUME);

    vistaEspecificacion.darClickEnBotonFavorito();

    assertThat(vistaEspecificacion.elCorazonEstaRelleno(), is(true));
    assertThat(vistaEspecificacion.elBotonEstaMarcadoComoFavorito(), is(true));
  }
}
