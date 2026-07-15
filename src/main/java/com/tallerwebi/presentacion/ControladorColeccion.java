package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ItemColeccion;
import com.tallerwebi.dominio.ServicioColeccion;
import java.time.LocalDate;
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

  private static final String PARAM_ID_PERFUME = "idPerfume";
  private static final String SESSION_USUARIO_ID = "USUARIO_ID";
  private static final String REDIRECT_LOGIN = "redirect:/login";
  private static final String REDIRECT_LISTADO = "redirect:/listado";

  private ServicioColeccion servicioColeccion;

  @Autowired
  public ControladorColeccion(ServicioColeccion servicioColeccion) {
    this.servicioColeccion = servicioColeccion;
  }

  @RequestMapping(path = "/agregar-a-coleccion", method = RequestMethod.POST)
  public ModelAndView agregarPerfume(
    @RequestParam(PARAM_ID_PERFUME) Long idPerfume,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute(SESSION_USUARIO_ID);
    if (idUsuario == null) return new ModelAndView(REDIRECT_LOGIN);

    servicioColeccion.guardarEnColeccion(idUsuario, idPerfume);
    return new ModelAndView(REDIRECT_LISTADO);
  }

  @RequestMapping(path = "/listado", method = RequestMethod.GET)
  public ModelAndView mostrarListado(HttpServletRequest request) {
    Long idUsuario = (Long) request.getSession().getAttribute(SESSION_USUARIO_ID);
    if (idUsuario == null) return new ModelAndView(REDIRECT_LOGIN);

    ModelMap modelo = new ModelMap();

    // Traemos los perfumes de este usuario junto con su estado de favorito y maceración
    List<ItemColeccion> misPerfumes = servicioColeccion.listarConDetalle(idUsuario);

    modelo.put("perfumesColeccion", misPerfumes);
    modelo.put("fechaHoy", LocalDate.now().toString());
    return new ModelAndView("listado", modelo);
  }

  @RequestMapping(path = "/eliminar-de-coleccion", method = RequestMethod.POST)
  public ModelAndView eliminarDeColeccion(
    @RequestParam(PARAM_ID_PERFUME) Long idPerfume,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute(SESSION_USUARIO_ID);

    if (idUsuario == null) {
      return new ModelAndView(REDIRECT_LOGIN);
    }

    servicioColeccion.eliminarPerfume(idUsuario, idPerfume);
    return new ModelAndView(REDIRECT_LISTADO);
  }

  @RequestMapping(path = "/iniciar-maceracion", method = RequestMethod.POST)
  public ModelAndView iniciarMaceracion(
    @RequestParam(PARAM_ID_PERFUME) Long idPerfume,
    @RequestParam(value = "fechaInicioMaceracion", required = false) String fechaInicioMaceracion,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute(SESSION_USUARIO_ID);
    if (idUsuario == null) return new ModelAndView(REDIRECT_LOGIN);

    servicioColeccion.iniciarMaceracion(idUsuario, idPerfume, parsearFecha(fechaInicioMaceracion));
    return new ModelAndView(REDIRECT_LISTADO);
  }

  @RequestMapping(path = "/cancelar-maceracion", method = RequestMethod.POST)
  public ModelAndView cancelarMaceracion(
    @RequestParam(PARAM_ID_PERFUME) Long idPerfume,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute(SESSION_USUARIO_ID);
    if (idUsuario == null) return new ModelAndView(REDIRECT_LOGIN);

    servicioColeccion.cancelarMaceracion(idUsuario, idPerfume);
    return new ModelAndView(REDIRECT_LISTADO);
  }

  private LocalDate parsearFecha(String fecha) {
    if (fecha == null || fecha.isEmpty()) return null;
    return LocalDate.parse(fecha);
  }
}
