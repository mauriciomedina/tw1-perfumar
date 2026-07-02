package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioFavorito;
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
public class ControladorFavorito {

  private ServicioFavorito servicioFavorito;

  @Autowired
  public ControladorFavorito(ServicioFavorito servicioFavorito) {
    this.servicioFavorito = servicioFavorito;
  }

  @RequestMapping(path = "/agregar-a-favoritos", method = RequestMethod.POST)
  public ModelAndView agregarAFavoritos(
    @RequestParam("idPerfume") Long idPerfume,
    @RequestParam(value = "origen", required = false) String origen,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return new ModelAndView("redirect:/login");

    servicioFavorito.agregarAFavoritos(idUsuario, idPerfume);

    String destino = (origen != null && !origen.isEmpty()) ? origen : "/favoritos";
    return new ModelAndView("redirect:" + destino);
  }

  @RequestMapping(path = "/favoritos", method = RequestMethod.GET)
  public ModelAndView mostrarFavoritos(HttpServletRequest request) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return new ModelAndView("redirect:/login");

    ModelMap modelo = new ModelMap();

    List<Perfume> misFavoritos = servicioFavorito.listar(idUsuario);

    modelo.put("perfumesFavoritos", misFavoritos);
    return new ModelAndView("favoritos", modelo);
  }

  @RequestMapping(path = "/eliminar-de-favoritos", method = RequestMethod.POST)
  public ModelAndView eliminarDeFavoritos(
    @RequestParam("idPerfume") Long idPerfume,
    @RequestParam(value = "origen", required = false) String origen,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return new ModelAndView("redirect:/login");

    servicioFavorito.eliminarFavorito(idUsuario, idPerfume);

    String destino = (origen != null && !origen.isEmpty()) ? origen : "/favoritos";
    return new ModelAndView("redirect:" + destino);
  }
}
