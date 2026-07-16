package com.tallerwebi.punta_a_punta;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

import com.microsoft.playwright.*;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VistaAutenticacionE2E {

  static Playwright playwright;
  static Browser browser;
  BrowserContext context;
  Page page;

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
  void crearContexto() {
    ReiniciarDB.limpiarBaseDeDatos();

    context = browser.newContext();
    page = context.newPage();
  }

  @AfterEach
  void cerrarContexto() {
    context.close();
  }

  @Test
  void navegarAHomeSinSesionDeberiaRedirigirALogin() throws MalformedURLException {
    page.navigate("http://localhost:8080/spring/home");

    entoncesDeberiaEstarEnLaVistaDeLogin();
  }

  @Test
  void navegarAPerfilSinSesionDeberiaRedirigirALogin() throws MalformedURLException {
    page.navigate("http://localhost:8080/spring/perfil");

    entoncesDeberiaEstarEnLaVistaDeLogin();
  }

  @Test
  void navegarAEspecificacionSinSesionDeberiaRedirigirALogin() throws MalformedURLException {
    page.navigate("http://localhost:8080/spring/especificacion?id=1");

    entoncesDeberiaEstarEnLaVistaDeLogin();
  }

  private void entoncesDeberiaEstarEnLaVistaDeLogin() throws MalformedURLException {
    URL url = new URL(page.url());
    assertThat(url.getPath(), matchesPattern("^/spring/login(?:;jsessionid=[^/\\s]+)?$"));
  }
}
