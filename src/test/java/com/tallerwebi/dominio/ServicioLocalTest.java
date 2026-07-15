package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

public class ServicioLocalTest {

  private ServicioLocalImpl servicioLocal;
  private RestTemplate restTemplateMock;

  private static final Double LAT_USUARIO = -34.6;
  private static final Double LON_USUARIO = -58.4;

  @BeforeEach
  public void init() {
    this.restTemplateMock = mock(RestTemplate.class);
    this.servicioLocal = new ServicioLocalImpl(restTemplateMock);
  }

  @Test
  public void obtenerLocalesMasCercanosDeberiaOrdenarPorDistanciaAlUsuario() {
    String json =
      "{\"elements\":[" +
      "{\"type\":\"node\",\"lat\":-34.9,\"lon\":-58.9,\"tags\":{\"name\":\"Lejana\",\"shop\":\"perfumery\"}}," +
      "{\"type\":\"node\",\"lat\":-34.601,\"lon\":-58.401,\"tags\":{\"name\":\"Cercana\",\"shop\":\"perfumery\"}}" +
      "]}";
    this.dadoQueLaApiResponde(json);

    List<Local> resultado =
      this.servicioLocal.obtenerLocalesMasCercanos(LAT_USUARIO, LON_USUARIO, 3);

    assertThat(resultado, hasSize(2));
    assertThat(resultado.get(0).getNombre(), equalTo("Cercana"));
    assertThat(resultado.get(1).getNombre(), equalTo("Lejana"));
  }

  @Test
  public void obtenerLocalesMasCercanosDeberiaConstruirLaDireccionDesdeLosTagsDeOsm() {
    String json =
      "{\"elements\":[" +
      "{\"type\":\"node\",\"lat\":-34.601,\"lon\":-58.401,\"tags\":{\"name\":\"Con Direccion\"," +
      "\"shop\":\"perfumery\",\"addr:street\":\"Av. Ejemplo\",\"addr:housenumber\":\"123\"}}" +
      "]}";
    this.dadoQueLaApiResponde(json);

    List<Local> resultado =
      this.servicioLocal.obtenerLocalesMasCercanos(LAT_USUARIO, LON_USUARIO, 3);

    assertThat(resultado.get(0).getDireccion(), equalTo("Av. Ejemplo 123"));
  }

  @Test
  public void obtenerLocalesMasCercanosDeberiaUsarDireccionPorDefectoSiNoHayTagsDeCalle() {
    String json =
      "{\"elements\":[" +
      "{\"type\":\"node\",\"lat\":-34.601,\"lon\":-58.401,\"tags\":{\"name\":\"Sin Direccion\",\"shop\":\"cosmetics\"}}" +
      "]}";
    this.dadoQueLaApiResponde(json);

    List<Local> resultado =
      this.servicioLocal.obtenerLocalesMasCercanos(LAT_USUARIO, LON_USUARIO, 3);

    assertThat(resultado.get(0).getDireccion(), equalTo("Dirección no disponible"));
  }

  @Test
  public void obtenerLocalesMasCercanosDeberiaIgnorarElementosSinNombre() {
    String json =
      "{\"elements\":[" +
      "{\"type\":\"node\",\"lat\":-34.601,\"lon\":-58.401,\"tags\":{\"shop\":\"perfumery\"}}," +
      "{\"type\":\"node\",\"lat\":-34.602,\"lon\":-58.402,\"tags\":{\"name\":\"Con Nombre\",\"shop\":\"perfumery\"}}" +
      "]}";
    this.dadoQueLaApiResponde(json);

    List<Local> resultado =
      this.servicioLocal.obtenerLocalesMasCercanos(LAT_USUARIO, LON_USUARIO, 3);

    assertThat(resultado, hasSize(1));
    assertThat(resultado.get(0).getNombre(), equalTo("Con Nombre"));
  }

  @Test
  public void obtenerLocalesMasCercanosDeberiaRespetarElLimiteDeCantidad() {
    String json =
      "{\"elements\":[" +
      "{\"type\":\"node\",\"lat\":-34.601,\"lon\":-58.401,\"tags\":{\"name\":\"Uno\",\"shop\":\"perfumery\"}}," +
      "{\"type\":\"node\",\"lat\":-34.602,\"lon\":-58.402,\"tags\":{\"name\":\"Dos\",\"shop\":\"perfumery\"}}" +
      "]}";
    this.dadoQueLaApiResponde(json);

    List<Local> resultado =
      this.servicioLocal.obtenerLocalesMasCercanos(LAT_USUARIO, LON_USUARIO, 1);

    assertThat(resultado, hasSize(1));
  }

  @Test
  public void obtenerLocalesMasCercanosDeberiaRetornarListaVaciaSiLaApiFalla() {
    when(this.restTemplateMock.getForObject(any(URI.class), eq(String.class)))
      .thenThrow(new RuntimeException("Overpass no responde"));

    List<Local> resultado =
      this.servicioLocal.obtenerLocalesMasCercanos(LAT_USUARIO, LON_USUARIO, 3);

    assertThat(resultado, empty());
  }

  @Test
  public void obtenerLocalesMasCercanosDeberiaRetornarListaVaciaSiElJsonEsInvalido() {
    this.dadoQueLaApiResponde("no es un json valido");

    List<Local> resultado =
      this.servicioLocal.obtenerLocalesMasCercanos(LAT_USUARIO, LON_USUARIO, 3);

    assertThat(resultado, empty());
  }

  private void dadoQueLaApiResponde(String json) {
    when(this.restTemplateMock.getForObject(any(URI.class), eq(String.class))).thenReturn(json);
  }
}
