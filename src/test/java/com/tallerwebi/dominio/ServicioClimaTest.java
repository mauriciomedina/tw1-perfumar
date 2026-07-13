package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ServicioClimaTest {

  private ServicioClimaImpl servicioClima;
  private RestTemplate restTemplateMock;

  private static final String JSON_RESPONSE =
    "{" +
    "\"name\":\"Buenos Aires\"," +
    "\"main\":{\"temp\":22.5,\"feels_like\":21.0,\"humidity\":60}," +
    "\"wind\":{\"speed\":4.5}," +
    "\"weather\":[{\"main\":\"Clear\",\"description\":\"cielo claro\"}]," +
    "\"clouds\":{\"all\":10}" +
    "}";

  @BeforeEach
  public void init() {
    this.restTemplateMock = mock(RestTemplate.class);
    this.servicioClima = new ServicioClimaImpl(restTemplateMock);
  }

  @Test
  public void obtenerClimaDeberiaMapearLosCamposDesdeLaRespuestaDeLaApi() {
    this.dadoQueLaApiResponde(JSON_RESPONSE);

    Clima clima = this.servicioClima.obtenerClima("Buenos Aires", "AR");

    assertThat(clima.getCiudad(), equalTo("Buenos Aires"));
    assertThat(clima.getTemperatura(), equalTo(22.5));
    assertThat(clima.getSensacionTermica(), equalTo(21.0));
    assertThat(clima.getHumedad(), equalTo(60));
    assertThat(clima.getVelocidadViento(), equalTo(4.5));
    assertThat(clima.getCondicion(), equalTo("Clear"));
    assertThat(clima.getDescripcion(), equalTo("cielo claro"));
    assertThat(clima.getNubosidad(), equalTo(10));
  }

  @Test
  public void obtenerClimaDeberiaPropagarExcepcionSiLaCiudadNoExiste() {
    when(this.restTemplateMock.getForObject(any(URI.class), eq(String.class)))
      .thenThrow(
        HttpClientErrorException.create(
          HttpStatus.NOT_FOUND,
          "Not Found",
          new HttpHeaders(),
          new byte[0],
          StandardCharsets.UTF_8
        )
      );

    assertThrows(
      HttpClientErrorException.NotFound.class,
      () -> this.servicioClima.obtenerClima("CiudadInexistente", "AR")
    );
  }

  @Test
  public void obtenerClimaDeberiaLanzarExcepcionSiElJsonEsInvalido() {
    this.dadoQueLaApiResponde("no es un json valido");

    assertThrows(
      IllegalStateException.class,
      () -> this.servicioClima.obtenerClima("Buenos Aires", "AR")
    );
  }

  private void dadoQueLaApiResponde(String json) {
    when(this.restTemplateMock.getForObject(any(URI.class), eq(String.class))).thenReturn(json);
  }
}
