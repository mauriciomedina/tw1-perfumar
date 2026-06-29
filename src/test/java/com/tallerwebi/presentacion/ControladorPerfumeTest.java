package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.dominio.ServicioLocal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPerfumeTest {

  private ServicioColeccion servicioColeccionMock;
  private ServicioLocal servicioLocalMock;
  private ControladorPerfume controlador;

  @BeforeEach
  public void init() {
    this.servicioColeccionMock = mock(ServicioColeccion.class);
    this.servicioLocalMock = mock(ServicioLocal.class);

    this.controlador = new ControladorPerfume(this.servicioColeccionMock, this.servicioLocalMock);
  }

  @Test
  public void queAlNavegarALaEspecificacionMeLleveALaVistaEspecificacion() {
    ModelAndView mav = controlador.mostrarEspecificacion(1L, null, null);

    assertThat(mav.getViewName(), equalToIgnoringCase("especificacion"));
  }
}
