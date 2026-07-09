package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Local;
import com.tallerwebi.dominio.ServicioLocal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalApiControllerTest {

  private ServicioLocal servicioLocalMock;
  private LocalApiController localApiController;

  @BeforeEach
  public void init() {
    servicioLocalMock = mock(ServicioLocal.class);
    localApiController = new LocalApiController(servicioLocalMock);
  }

  @Test
  public void queDevuelvaLosLocalesQueDevuelveElServicio() {
    Local localSimulado = new Local("Perfumeria Centro", "Av. Siempre Viva 123", -34.6, -58.4);
    when(servicioLocalMock.obtenerLocalesMasCercanos(-34.6, -58.4, 3))
      .thenReturn(Collections.singletonList(localSimulado));

    List<Local> resultado = localApiController.obtenerLocalesCercanos(-34.6, -58.4);

    assertThat(resultado, hasSize(1));
  }
}
