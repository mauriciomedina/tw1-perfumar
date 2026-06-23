package com.tallerwebi.presentacion;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PerfumeApiController {

  private static final Pattern PATRON_OBJETO = Pattern.compile("\\{[^{}]+\\}", Pattern.DOTALL);
  private static final Pattern PATRON_NOMBRE = Pattern.compile("\"nombre\"\\s*:\\s*\"([^\"]+)\"");

  @GetMapping(value = "/perfumes", produces = "application/json")
  public String obtenerPerfumesDelCatalogo(
    @RequestParam(value = "nombre", required = false) String nombre
  ) {
    try (InputStream inputStream = new ClassPathResource("perfumes.json").getInputStream()) {
      byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
      String json = new String(bdata, StandardCharsets.UTF_8);
      if (nombre == null || nombre.trim().isEmpty()) {
        return json;
      }
      return filtrarPorNombre(json, nombre.trim().toLowerCase(Locale.ROOT));
    } catch (Exception e) {
      return "{\"error\": \"No se pudo cargar el catálogo local: " + e.getMessage() + "\"}";
    }
  }

  private String filtrarPorNombre(String json, String termino) {
    Matcher matcherObjeto = PATRON_OBJETO.matcher(json);
    List<String> coincidencias = new ArrayList<>();
    while (matcherObjeto.find()) {
      String objeto = matcherObjeto.group();
      Matcher matcherNombre = PATRON_NOMBRE.matcher(objeto);
      if (
        matcherNombre.find() && matcherNombre.group(1).toLowerCase(Locale.ROOT).contains(termino)
      ) {
        coincidencias.add(objeto);
      }
    }
    return "[" + String.join(",", coincidencias) + "]";
  }
}
