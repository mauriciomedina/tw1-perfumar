package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioColeccion;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PerfumeApiController {

  private ServicioColeccion servicioColeccion;

  @Autowired
  public PerfumeApiController(ServicioColeccion servicioColeccion) {
    this.servicioColeccion = servicioColeccion;
  }

  @GetMapping(value = "/perfumes", produces = "application/json")
  public List<PerfumeCatalogoDTO> obtenerPerfumesDelCatalogo(
    @RequestParam(value = "nombre", required = false) String nombre
  ) {
    List<Perfume> perfumes = filtrarPorNombre(servicioColeccion.listar(), nombre);

    return perfumes.stream().map(this::convertirADTO).collect(Collectors.toList());
  }

  private List<Perfume> filtrarPorNombre(List<Perfume> perfumes, String nombre) {
    if (nombre == null || nombre.trim().isEmpty()) {
      return perfumes;
    }

    return perfumes
      .stream()
      .filter(perfume -> coincideConNombre(perfume, nombre))
      .collect(Collectors.toList());
  }

  private boolean coincideConNombre(Perfume perfume, String nombre) {
    String termino = nombre.trim().toLowerCase(Locale.ROOT);
    return perfume.getNombre().toLowerCase(Locale.ROOT).contains(termino);
  }

  private PerfumeCatalogoDTO convertirADTO(Perfume perfume) {
    return new PerfumeCatalogoDTO(
      perfume.getId(),
      perfume.getNombre(),
      perfume.getMarca() != null ? perfume.getMarca().getNombreVisible() : null,
      perfume.getFamiliaOlfativa() != null ? perfume.getFamiliaOlfativa().getNombreVisible() : null,
      perfume.getDescripcion(),
      perfume.getNotas(),
      perfume.getImagenUrl()
    );
  }
}
