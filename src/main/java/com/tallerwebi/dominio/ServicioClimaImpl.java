package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ServicioClimaImpl implements ServicioClima {

  private static final String URL = "https://api.openweathermap.org/data/2.5/weather";

  @Value("${OPENWEATHER_API_KEY:default}")
  private String apiKey;

  private final RestTemplate restTemplate;

  @Autowired
  public ServicioClimaImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public Clima obtenerClima(String ciudad, String pais) {
    URI uri = UriComponentsBuilder
      .fromHttpUrl(URL)
      .queryParam("q", ciudad + "," + pais)
      .queryParam("appid", apiKey)
      .queryParam("units", "metric")
      .queryParam("lang", "es")
      .build()
      .encode()
      .toUri();

    String respuesta = restTemplate.getForObject(uri, String.class);
    return extraerClima(respuesta);
  }

  private Clima extraerClima(String json) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(json);
      JsonNode main = root.path("main");
      JsonNode viento = root.path("wind");
      JsonNode weather = root.path("weather").path(0);
      JsonNode nubes = root.path("clouds");

      return new Clima(
        root.path("name").asText(),
        main.path("temp").asDouble(),
        main.path("feels_like").asDouble(),
        main.path("humidity").asInt(),
        viento.path("speed").asDouble(),
        weather.path("main").asText(),
        weather.path("description").asText(),
        nubes.path("all").asInt()
      );
    } catch (Exception e) {
      throw new IllegalStateException("No se pudo procesar la respuesta del clima", e);
    }
  }
}
