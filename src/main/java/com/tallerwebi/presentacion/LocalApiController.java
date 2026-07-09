package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Local;
import com.tallerwebi.dominio.ServicioLocal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LocalApiController {

  private static final int CANTIDAD_LOCALES_CERCANOS = 3;

  private ServicioLocal servicioLocal;

  @Autowired
  public LocalApiController(ServicioLocal servicioLocal) {
    this.servicioLocal = servicioLocal;
  }

  @GetMapping(value = "/locales-cercanos", produces = "application/json")
  public List<Local> obtenerLocalesCercanos(
    @RequestParam("lat") Double lat,
    @RequestParam("lon") Double lon
  ) {
    return servicioLocal.obtenerLocalesMasCercanos(lat, lon, CANTIDAD_LOCALES_CERCANOS);
  }
}
