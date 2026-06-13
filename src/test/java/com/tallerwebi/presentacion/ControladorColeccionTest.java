package com.tallerwebi.presentacion;

// IMPORTS DE HAMCREST (Los que usa la cátedra)
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioColeccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorColeccionTest {

  private ControladorColeccion controladorColeccion;
  private ServicioColeccion servicioColeccionMock;

  @BeforeEach
  public void init() {
    servicioColeccionMock = mock(ServicioColeccion.class);
    controladorColeccion = new ControladorColeccion(servicioColeccionMock);
  }

  @Test
  public void queAlAgregarUnPerfumeExitosamenteRedirijaAlListado() {
    Long idPerfume = 1L;

    ModelAndView mav = controladorColeccion.agregarPerfume(idPerfume);

    verify(servicioColeccionMock, times(1)).guardarEnColeccion(idPerfume);

    // Sintaxis de Hamcrest
    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/listado"));
  }

  @Test
  public void queAlConsultarElListadoMuestreLaVistaDeColeccionConSuContenido() {
    ModelAndView mav = controladorColeccion.mostrarListado();

    // Sintaxis de Hamcrest
    assertThat(mav.getViewName(), equalToIgnoringCase("listado"));
    assertThat(mav.getModel().get("perfumesColeccion"), notNullValue());
  }
}
