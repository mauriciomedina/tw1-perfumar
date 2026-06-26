package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioColeccion;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorColeccionTest {

  private ControladorColeccion controladorColeccion;
  private ServicioColeccion servicioColeccionMock;
  private HttpServletRequest requestMock;
  private HttpSession sessionMock;

  @BeforeEach
  public void init() {
    servicioColeccionMock = mock(ServicioColeccion.class);
    requestMock = mock(HttpServletRequest.class);
    sessionMock = mock(HttpSession.class);

    
    when(requestMock.getSession()).thenReturn(sessionMock);
   
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(1L);

    controladorColeccion = new ControladorColeccion(servicioColeccionMock);
  }

  @Test
  public void queAlAgregarUnPerfumeExitosamenteRedirijaAlListado() {
    Long idPerfume = 1L;

  
    ModelAndView mav = controladorColeccion.agregarPerfume(idPerfume, requestMock);


    verify(servicioColeccionMock, times(1)).guardarEnColeccion(1L, idPerfume);

    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/listado"));
  }

  @Test
  public void queAlConsultarElListadoMuestreLaVistaDeColeccionConSuContenido() {
   
    ModelAndView mav = controladorColeccion.mostrarListado(requestMock);

    assertThat(mav.getViewName(), equalToIgnoringCase("listado"));
    assertThat(mav.getModel().get("perfumesColeccion"), notNullValue());
  }
}
