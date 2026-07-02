package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioFavorito;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorFavoritoTest {

  private ControladorFavorito controladorFavorito;
  private ServicioFavorito servicioFavoritoMock;
  private HttpServletRequest requestMock;
  private HttpSession sessionMock;

  @BeforeEach
  public void init() {
    servicioFavoritoMock = mock(ServicioFavorito.class);
    requestMock = mock(HttpServletRequest.class);
    sessionMock = mock(HttpSession.class);

    when(requestMock.getSession()).thenReturn(sessionMock);
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(1L);

    controladorFavorito = new ControladorFavorito(servicioFavoritoMock);
  }

  @Test
  public void queAlAgregarUnPerfumeExitosamenteRedirijaAFavoritosPorDefecto() {
    Long idPerfume = 1L;

    ModelAndView mav = controladorFavorito.agregarAFavoritos(idPerfume, null, requestMock);

    verify(servicioFavoritoMock, times(1)).agregarAFavoritos(1L, idPerfume);
    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/favoritos"));
  }

  @Test
  public void queAlAgregarUnPerfumeConOrigenRedirijaAEseOrigen() {
    Long idPerfume = 1L;

    ModelAndView mav = controladorFavorito.agregarAFavoritos(
      idPerfume,
      "/especificacion?id=1",
      requestMock
    );

    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/especificacion?id=1"));
  }

  @Test
  public void queAlConsultarFavoritosMuestreLaVistaConSuContenido() {
    ModelAndView mav = controladorFavorito.mostrarFavoritos(requestMock);

    assertThat(mav.getViewName(), equalToIgnoringCase("favoritos"));
    assertThat(mav.getModel().get("perfumesFavoritos"), notNullValue());
  }

  @Test
  public void queAlEliminarUnFavoritoLlameAlServicio() {
    Long idPerfume = 1L;

    controladorFavorito.eliminarDeFavoritos(idPerfume, null, requestMock);

    verify(servicioFavoritoMock, times(1)).eliminarFavorito(1L, idPerfume);
  }
}
