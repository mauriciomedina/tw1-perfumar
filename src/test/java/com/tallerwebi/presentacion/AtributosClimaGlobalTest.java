package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Clima;
import com.tallerwebi.dominio.ServicioClima;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AtributosClimaGlobalTest {

  private AtributosClimaGlobal atributosClimaGlobal;
  private ServicioClima servicioClimaMock;
  private HttpServletRequest requestMock;
  private HttpSession sessionMock;
  private static final String CIUDAD = "Buenos Aires";
  private static final String PAIS = "AR";

  @BeforeEach
  public void init() {
    servicioClimaMock = mock(ServicioClima.class);
    requestMock = mock(HttpServletRequest.class);
    sessionMock = mock(HttpSession.class);
    when(requestMock.getSession()).thenReturn(sessionMock);

    atributosClimaGlobal = new AtributosClimaGlobal();
    ReflectionTestUtils.setField(atributosClimaGlobal, "servicioClima", servicioClimaMock);
  }

  @Test
  public void climaActualDeberiaRetornarNullSiNoHayCiudadEnSesion() {
    when(sessionMock.getAttribute("CIUDAD")).thenReturn(null);
    when(sessionMock.getAttribute("PAIS")).thenReturn(PAIS);

    Clima resultado = atributosClimaGlobal.climaActual(requestMock);

    assertThat(resultado, nullValue());
  }

  @Test
  public void climaActualDeberiaConsultarElServicioSiHayCiudadYPaisEnSesion() {
    when(sessionMock.getAttribute("CIUDAD")).thenReturn(CIUDAD);
    when(sessionMock.getAttribute("PAIS")).thenReturn(PAIS);
    Clima climaEsperado = new Clima(CIUDAD, 22.0, 21.0, 60, 4.0, "Clear", "cielo claro", 10);
    when(servicioClimaMock.obtenerClima(CIUDAD, PAIS)).thenReturn(climaEsperado);

    Clima resultado = atributosClimaGlobal.climaActual(requestMock);

    assertThat(resultado, equalTo(climaEsperado));
  }

  @Test
  public void climaActualDeberiaRetornarNullSiElServicioFalla() {
    when(sessionMock.getAttribute("CIUDAD")).thenReturn(CIUDAD);
    when(sessionMock.getAttribute("PAIS")).thenReturn(PAIS);
    when(servicioClimaMock.obtenerClima(CIUDAD, PAIS))
      .thenThrow(new RuntimeException("Error inesperado"));

    Clima resultado = atributosClimaGlobal.climaActual(requestMock);

    assertThat(resultado, nullValue());
  }
}
