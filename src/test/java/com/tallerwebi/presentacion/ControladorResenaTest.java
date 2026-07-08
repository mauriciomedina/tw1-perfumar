package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioResena;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorResenaTest {

  private ControladorResena controladorResena;
  private ServicioResena servicioResenaMock;
  private HttpServletRequest requestMock;
  private HttpSession sessionMock;

  @BeforeEach
  public void init() {
    servicioResenaMock = mock(ServicioResena.class);
    requestMock = mock(HttpServletRequest.class);
    sessionMock = mock(HttpSession.class);

    when(requestMock.getSession()).thenReturn(sessionMock);
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(1L);

    controladorResena = new ControladorResena(servicioResenaMock);
  }

  @Test
  public void queAlAgregarUnaResenaExitosamenteLlameAlServicioYRedirijaAlOrigen() {
    ModelAndView mav = controladorResena.agregarResena(
      1L,
      "Muy buena fragancia",
      5,
      "/especificacion?id=1",
      requestMock
    );

    verify(servicioResenaMock, times(1)).agregarResena(1L, 1L, "Muy buena fragancia", 5);
    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/especificacion?id=1"));
  }

  @Test
  public void queSinOrigenRedirijaALaEspecificacionDelPerfume() {
    ModelAndView mav = controladorResena.agregarResena(
      1L,
      "Muy buena fragancia",
      5,
      null,
      requestMock
    );

    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/especificacion?id=1"));
  }
}
