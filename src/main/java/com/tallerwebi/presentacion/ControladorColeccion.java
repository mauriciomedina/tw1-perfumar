package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioColeccion;
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
public class ControladorColeccion {

  private ServicioColeccion servicioColeccion;

  @Autowired
  public ControladorColeccion(ServicioColeccion servicioColeccion) {
    this.servicioColeccion = servicioColeccion;
  }

  @RequestMapping(path = "/agregar-a-coleccion", method = RequestMethod.POST)
  public ModelAndView agregarPerfume(
    @RequestParam("idPerfume") Long idPerfume,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return new ModelAndView("redirect:/login");

    servicioColeccion.guardarEnColeccion(idUsuario, idPerfume);
    return new ModelAndView("redirect:/listado");
  }

  @RequestMapping(path = "/listado", method = RequestMethod.GET)
  public ModelAndView mostrarListado(HttpServletRequest request) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return new ModelAndView("redirect:/login");

    ModelMap modelo = new ModelMap();

    // AHORA SÍ TRAEMOS SOLO LOS PERFUMES DE ESTE USUARIO
    List<Perfume> misPerfumes = servicioColeccion.listar(idUsuario);

    modelo.put("perfumesColeccion", misPerfumes);
    return new ModelAndView("listado", modelo);
  }

  @RequestMapping(path = "/eliminar-de-coleccion", method = RequestMethod.POST)
  public ModelAndView eliminarDeColeccion(
    @RequestParam("idPerfume") Long idPerfume,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");

    if (idUsuario == null) {
      return new ModelAndView("redirect:/login");
    }

    servicioColeccion.eliminarPerfume(idUsuario, idPerfume);
    return new ModelAndView("redirect:/listado");
  }
}
