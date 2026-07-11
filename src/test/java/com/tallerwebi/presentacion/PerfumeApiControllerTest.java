package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioColeccion;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PerfumeApiControllerTest {

  private ServicioColeccion servicioColeccionMock;
  private PerfumeApiController perfumeApiController;

  @BeforeEach
  public void init() {
    servicioColeccionMock = mock(ServicioColeccion.class);
    perfumeApiController = new PerfumeApiController(servicioColeccionMock);

    Perfume sauvage = new Perfume();
    sauvage.setId(1L);
    sauvage.setNombre("Sauvage");

    Perfume fahrenheit = new Perfume();
    fahrenheit.setId(2L);
    fahrenheit.setNombre("Fahrenheit");

    when(servicioColeccionMock.listar()).thenReturn(Arrays.asList(sauvage, fahrenheit));
  }

  @Test
  public void queSiNoBuscaNombreDevuelveTodosLosPerfumes() {
    List<PerfumeCatalogoDTO> resultado = perfumeApiController.obtenerPerfumesDelCatalogo(null);

    assertThat(resultado, hasSize(2));
  }

  @Test
  public void queBuscarConNombreVacioDevuelveTodosLosPerfumes() {
    List<PerfumeCatalogoDTO> resultado = perfumeApiController.obtenerPerfumesDelCatalogo("  ");

    assertThat(resultado, hasSize(2));
  }

  @Test
  public void queBuscarPorNombreExistenteDevuelveElPerfumeCorrespondiente() {
    List<PerfumeCatalogoDTO> resultado = perfumeApiController.obtenerPerfumesDelCatalogo("Sauvage");

    assertThat(resultado, hasSize(1));
    assertThat(resultado.get(0).getNombre(), is("Sauvage"));
  }

  @Test
  public void queBuscarEsInsensibleAMayusculas() {
    List<PerfumeCatalogoDTO> resultado = perfumeApiController.obtenerPerfumesDelCatalogo("sauvage");

    assertThat(resultado, hasSize(1));
  }

  @Test
  public void queBuscarPorNombreInexistenteDevuelveListaVacia() {
    List<PerfumeCatalogoDTO> resultado = perfumeApiController.obtenerPerfumesDelCatalogo(
      "PerfumeQueNoExiste"
    );

    assertThat(resultado, hasSize(0));
  }
}
