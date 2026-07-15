package com.tallerwebi.dominio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service("servicioLocal")
public class ServicioLocalImpl implements ServicioLocal {

  private static final String OVERPASS_URL = "https://overpass-api.de/api/interpreter";
  private static final int RADIO_BUSQUEDA_METROS = 5000;
  private static final int RADIO_TIERRA_KM = 6371;

  private final RestTemplate restTemplate;

  @Autowired
  public ServicioLocalImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<Local> obtenerLocalesMasCercanos(
    Double latUsuario,
    Double lonUsuario,
    Integer cantidadDeLocales
  ) {
    List<Local> encontrados = buscarPerfumeriasCercanas(latUsuario, lonUsuario);

    encontrados.sort(
      Comparator.comparingDouble(local ->
        calcularDistancia(latUsuario, lonUsuario, local.getLatitud(), local.getLongitud())
      )
    );

    return encontrados.stream().limit(cantidadDeLocales).collect(Collectors.toList());
  }

  private List<Local> buscarPerfumeriasCercanas(Double lat, Double lon) {
    try {
      String consulta = String.format(
        Locale.ROOT,
        "[out:json][timeout:25];(node[\"shop\"=\"perfumery\"](around:%d,%f,%f);" +
        "node[\"shop\"=\"cosmetics\"](around:%d,%f,%f););out body;",
        RADIO_BUSQUEDA_METROS,
        lat,
        lon,
        RADIO_BUSQUEDA_METROS,
        lat,
        lon
      );

      URI uri = UriComponentsBuilder
        .fromHttpUrl(OVERPASS_URL)
        .queryParam("data", consulta)
        .build()
        .encode()
        .toUri();

      String respuesta = restTemplate.getForObject(uri, String.class);
      return parsearRespuesta(respuesta);
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  private List<Local> parsearRespuesta(String json) {
    try {
      return extraerLocalesDelJson(json);
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  private List<Local> extraerLocalesDelJson(String json) throws JsonProcessingException {
    List<Local> locales = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    JsonNode raiz = mapper.readTree(json);
    for (JsonNode elemento : raiz.path("elements")) {
      JsonNode tags = elemento.path("tags");
      String nombre = tags.path("name").asText(null);
      if (nombre == null) {
        continue;
      }
      locales.add(
        new Local(
          nombre,
          construirDireccion(tags),
          elemento.path("lat").asDouble(),
          elemento.path("lon").asDouble()
        )
      );
    }
    return locales;
  }

  private String construirDireccion(JsonNode tags) {
    String calle = tags.path("addr:street").asText("");
    String altura = tags.path("addr:housenumber").asText("");
    if (!calle.isEmpty() && !altura.isEmpty()) {
      return calle + " " + altura;
    }
    if (!calle.isEmpty()) {
      return calle;
    }
    return "Dirección no disponible";
  }

  private Double calcularDistancia(
    Double latUsuario,
    Double lonUsuario,
    Double latLocal,
    Double lonLocal
  ) {
    Double latDistancia = Math.toRadians(latLocal - latUsuario);
    Double lonDistancia = Math.toRadians(lonLocal - lonUsuario);

    Double factorA =
      Math.sin(latDistancia / 2) * Math.sin(latDistancia / 2) +
      Math.cos(Math.toRadians(latUsuario)) *
        Math.cos(Math.toRadians(latLocal)) *
        Math.sin(lonDistancia / 2) *
        Math.sin(lonDistancia / 2);

    Double factorC = 2 * Math.atan2(Math.sqrt(factorA), Math.sqrt(1 - factorA));

    return RADIO_TIERRA_KM * factorC;
  }
}
