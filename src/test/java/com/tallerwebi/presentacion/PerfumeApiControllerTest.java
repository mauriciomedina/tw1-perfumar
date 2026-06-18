package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerfumeApiControllerTest {

  private PerfumeApiController perfumeApiController;

  @BeforeEach
  public void init() {
    perfumeApiController = new PerfumeApiController();
  }

  @Test
  public void queSiNoBuscaNombreDevuelveTodosLosPerfumes() {
    String resultado = perfumeApiController.obtenerPerfumesDelCatalogo(null);

    assertThat(resultado, startsWith("["));
    assertThat(resultado, containsString("Sauvage"));
    assertThat(resultado, containsString("Fahrenheit"));
  }

  @Test
  public void queBuscarConNombreVacioDevuelveTodosLosPerfumes() {
    String resultado = perfumeApiController.obtenerPerfumesDelCatalogo("  ");

    assertThat(resultado, startsWith("["));
    assertThat(resultado, containsString("Sauvage"));
  }

  @Test
  public void queBuscarPorNombreExistenteDevuelveElPerfumeCorrespondiente() {
    String resultado = perfumeApiController.obtenerPerfumesDelCatalogo("Sauvage");

    assertThat(resultado, containsString("Sauvage"));
    assertThat(resultado, not(containsString("Fahrenheit")));
  }

  @Test
  public void queBuscarEsInsensibleAMayusculas() {
    String resultado = perfumeApiController.obtenerPerfumesDelCatalogo("sauvage");

    assertThat(resultado, containsString("Sauvage"));
  }

  @Test
  public void queBuscarPorNombreInexistenteDevuelveArrayVacio() {
    String resultado = perfumeApiController.obtenerPerfumesDelCatalogo("PerfumeQueNoExiste");

    assertThat(resultado, equalTo("[]"));
  }
}
