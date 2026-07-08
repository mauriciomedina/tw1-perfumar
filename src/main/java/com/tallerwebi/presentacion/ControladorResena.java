package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioResena;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorResena {

  private ServicioResena servicioResena;

  @Autowired
  public ControladorResena(ServicioResena servicioResena) {
    this.servicioResena = servicioResena;
  }

  @RequestMapping(path = "/agregar-resena", method = RequestMethod.POST)
  public ModelAndView agregarResena(
    @RequestParam("idPerfume") Long idPerfume,
    @RequestParam("comentario") String comentario,
    @RequestParam("puntuacion") Integer puntuacion,
    @RequestParam(value = "origen", required = false) String origen,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return new ModelAndView("redirect:/login");

    servicioResena.agregarResena(idUsuario, idPerfume, comentario, puntuacion);

    String destino = (origen != null && !origen.isEmpty())
      ? origen
      : "/especificacion?id=" + idPerfume;
    return new ModelAndView("redirect:" + destino);
  }
}
