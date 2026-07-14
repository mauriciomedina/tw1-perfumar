package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Clima;
import com.tallerwebi.dominio.FamiliaOlfativa;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioClima;
import com.tallerwebi.dominio.ServicioColeccion;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

public class ControladorClimaTest {

  private ControladorClima controladorClima;
  private ServicioClima servicioClimaMock;
  private ServicioColeccion servicioColeccionMock;
  private HttpServletRequest requestMock;
  private HttpSession sessionMock;
  private static final String CIUDAD = "Buenos Aires";
  private static final String PAIS = "AR";

  @BeforeEach
  public void init() {
    servicioClimaMock = mock(ServicioClima.class);
    servicioColeccionMock = mock(ServicioColeccion.class);
    requestMock = mock(HttpServletRequest.class);
    sessionMock = mock(HttpSession.class);
    when(requestMock.getSession()).thenReturn(sessionMock);
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(1L);
    controladorClima = new ControladorClima();
    ReflectionTestUtils.setField(controladorClima, "servicioClima", servicioClimaMock);
    ReflectionTestUtils.setField(controladorClima, "servicioColeccion", servicioColeccionMock);
  }

  private Perfume crearPerfume(FamiliaOlfativa familia) {
    Perfume perfume = new Perfume();
    perfume.setId(1L);
    perfume.setNombre("Perfume Test");
    perfume.setImagenUrl("/img/test.png");
    perfume.setDescripcion("Descripción de prueba");
    perfume.setNotas("Notas de prueba");
    perfume.setFamiliaOlfativa(familia);
    return perfume;
  }

  @Test
  public void irABienvenidaConClimaCalidoDeberiaElegirPerfumeCitrico() {
    Clima climaCalido = new Clima(CIUDAD, 30.0, 30.0, 40, 2.0, "Clear", "cielo claro", 5);
    Perfume perfumeCitrico = crearPerfume(FamiliaOlfativa.CITRICA);
    when(servicioColeccionMock.listar())
      .thenReturn(List.of(perfumeCitrico, crearPerfume(FamiliaOlfativa.ORIENTAL)));

    ModelAndView modelAndView = controladorClima.irABienvenida(climaCalido, requestMock);

    assertThat(modelAndView.getViewName(), equalTo("bienvenida"));
    assertThat(modelAndView.getModel().get("pddiaFamilia"), equalTo("Cítrica"));
    assertThat(modelAndView.getModel().get("pddiaIdPerfume"), equalTo(perfumeCitrico.getId()));
  }

  @Test
  public void irABienvenidaSinClimaDeberiaElegirPerfumeAmaderadoYMostrarMensajeGenerico() {
    Perfume perfumeAmaderado = crearPerfume(FamiliaOlfativa.AMADERADA);
    when(servicioColeccionMock.listar()).thenReturn(List.of(perfumeAmaderado));

    ModelAndView modelAndView = controladorClima.irABienvenida(null, requestMock);

    assertThat(modelAndView.getModel().get("pddiaFamilia"), equalTo("Amaderada"));
    assertThat(
      modelAndView.getModel().get("pddiaClimaInfo"),
      equalTo("Configurá tu ciudad en tu perfil para ver recomendaciones según el clima")
    );
  }

  @Test
  public void irABienvenidaConCatalogoVacioNoDeberiaAgregarDatosDePerfume() {
    when(servicioColeccionMock.listar()).thenReturn(List.of());

    ModelAndView modelAndView = controladorClima.irABienvenida(null, requestMock);

    assertThat(modelAndView.getModel().containsKey("pddiaNombre"), equalTo(false));
  }

  @Test
  public void irABienvenidaDeberiaRedirigirALoginSiNoHaySesion() {
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(null);

    ModelAndView modelAndView = controladorClima.irABienvenida(null, requestMock);

    assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
  }

  @Test
  public void obtenerClimaDeberiaRetornarRespuestaExitosa() {
    Clima clima = new Clima(CIUDAD, 22.5, 21.0, 60, 4.5, "Clear", "cielo claro", 10);
    when(servicioClimaMock.obtenerClima(CIUDAD, PAIS)).thenReturn(clima);

    ResponseEntity<?> response = controladorClima.obtenerClima(CIUDAD, PAIS);

    assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.OK.value()));
    assertThat(((Clima) response.getBody()).getCiudad(), equalTo(CIUDAD));
  }

  @Test
  public void obtenerClimaDeberiaRetornar404SiLaCiudadNoExiste() {
    when(servicioClimaMock.obtenerClima(CIUDAD, PAIS))
      .thenThrow(
        HttpClientErrorException.create(
          HttpStatus.NOT_FOUND,
          "Not Found",
          new HttpHeaders(),
          new byte[0],
          StandardCharsets.UTF_8
        )
      );

    ResponseEntity<?> response = controladorClima.obtenerClima(CIUDAD, PAIS);

    assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.NOT_FOUND.value()));
  }

  @Test
  public void obtenerClimaDeberiaRetornar500SiOcurreExcepcionGenerica() {
    when(servicioClimaMock.obtenerClima(CIUDAD, PAIS))
      .thenThrow(new RuntimeException("Error inesperado"));

    ResponseEntity<?> response = controladorClima.obtenerClima(CIUDAD, PAIS);

    assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    assertThat(
      response.getBody().toString(),
      equalTo("Error al consultar el clima: Error inesperado")
    );
  }
}
