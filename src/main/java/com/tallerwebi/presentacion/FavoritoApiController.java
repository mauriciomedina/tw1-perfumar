package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioFavorito;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FavoritoApiController {

  private ServicioFavorito servicioFavorito;

  @Autowired
  public FavoritoApiController(ServicioFavorito servicioFavorito) {
    this.servicioFavorito = servicioFavorito;
  }

  @GetMapping(value = "/favoritos", produces = "application/json")
  public List<PerfumeCatalogoDTO> obtenerFavoritos(HttpServletRequest request) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return Collections.emptyList();

    List<Perfume> favoritos = servicioFavorito.listar(idUsuario);

    return favoritos.stream().map(this::convertirADTO).collect(Collectors.toList());
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
