package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.dominio.ServicioFavorito;
import com.tallerwebi.dominio.ServicioResena;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPerfumeTest {

  private ServicioColeccion servicioColeccionMock;

  private ServicioResena servicioResenaMock;
  private ControladorPerfume controlador;
  private HttpServletRequest requestMock;
  private HttpSession sessionMock;
  private ServicioFavorito servicioFavoritoMock;

  @BeforeEach
  public void init() {
    this.servicioColeccionMock = mock(ServicioColeccion.class);
    this.servicioFavoritoMock = mock(ServicioFavorito.class);
    this.servicioResenaMock = mock(ServicioResena.class);
    this.requestMock = mock(HttpServletRequest.class);
    this.sessionMock = mock(HttpSession.class);

    when(requestMock.getSession()).thenReturn(sessionMock);
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(1L);
    when(servicioResenaMock.listarPorPerfume(1L)).thenReturn(Collections.emptyList());

    this.controlador =
      new ControladorPerfume(
        this.servicioColeccionMock,
        this.servicioFavoritoMock,
        this.servicioResenaMock
      );
  }

  @Test
  public void queAlNavegarALaEspecificacionMeLleveALaVistaEspecificacion() {
    ModelAndView mav = controlador.mostrarEspecificacion(1L, requestMock);

    assertThat(mav.getViewName(), equalToIgnoringCase("especificacion"));
  }

  @Test
  public void queAlNavegarALaEspecificacionMeIndiqueSiEsFavorito() {
    when(servicioFavoritoMock.esFavorito(1L, 1L)).thenReturn(true);

    ModelAndView mav = controlador.mostrarEspecificacion(1L, requestMock);

    assertThat((Boolean) mav.getModel().get("esFavorito"), is(true));
  }

  @Test
  public void queAlNavegarALaEspecificacionMeTraigaElPromedioDeResenas() {
    when(servicioResenaMock.promedioDePuntuacion(1L)).thenReturn(4.5);

    ModelAndView mav = controlador.mostrarEspecificacion(1L, requestMock);

    assertThat((Double) mav.getModel().get("promedioPuntuacion"), is(4.5));
  }
}
