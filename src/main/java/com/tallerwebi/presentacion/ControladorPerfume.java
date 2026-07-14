package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.Resena;
import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.dominio.ServicioFavorito;
import com.tallerwebi.dominio.ServicioResena;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPerfume {

  private ServicioColeccion servicioColeccion;
  private ServicioFavorito servicioFavorito;
  private ServicioResena servicioResena;

  @Autowired
  public ControladorPerfume(
    ServicioColeccion servicioColeccion,
    ServicioFavorito servicioFavorito,
    ServicioResena servicioResena
  ) {
    this.servicioColeccion = servicioColeccion;
    this.servicioFavorito = servicioFavorito;
    this.servicioResena = servicioResena;
  }

  @RequestMapping("/listar-perfumes")
  public ModelAndView listarPerfumes() {
    ModelMap modelo = new ModelMap();
    modelo.put("mensaje", "nuevo perfume");
    modelo.put("perfumes", servicioColeccion.listar());
    return new ModelAndView("listar-perfumes", modelo);
  }

  @RequestMapping(path = "/especificacion", method = RequestMethod.GET)
  public ModelAndView mostrarEspecificacion(
    @RequestParam("id") Long id,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return new ModelAndView("redirect:/login");

    ModelMap modelo = new ModelMap();

    Perfume perfumeReal = servicioColeccion.buscarPerfume(id);
    modelo.put("perfume", perfumeReal);

    modelo.put("esFavorito", servicioFavorito.esFavorito(idUsuario, id));

    List<Resena> resenas = servicioResena.listarPorPerfume(id);
    Double promedio = servicioResena.promedioDePuntuacion(id);

    modelo.put("resenas", resenas);
    modelo.put("promedioPuntuacion", promedio != null ? promedio : 0.0);

    return new ModelAndView("especificacion", modelo);
  }
}
