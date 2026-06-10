package com.tallerwebi.presentacion;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PerfumeApiController {

  @GetMapping(value = "/perfumes", produces = "application/json")
  public String obtenerPerfumesDelCatalogo() {
    // Al poner el InputStream entre paréntesis, Java lo cierra solo al terminar
    try (InputStream inputStream = new ClassPathResource("perfumes.json").getInputStream()) {
      byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
      return new String(bdata, StandardCharsets.UTF_8);
    } catch (Exception e) {
      return "{\"error\": \"No se pudo cargar el catálogo local: " + e.getMessage() + "\"}";
    }
  }
}
