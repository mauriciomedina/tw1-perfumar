package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioColeccion;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorColeccion {

  private ServicioColeccion servicioColeccion;

  @Autowired
  public ControladorColeccion(ServicioColeccion servicioColeccion) {
    this.servicioColeccion = servicioColeccion;
  }

  @PostMapping("/coleccion/agregar")
  public ModelAndView agregarPerfume(@RequestParam("idPerfume") Long idPerfume) {
    servicioColeccion.guardarEnColeccion(idPerfume);
    return new ModelAndView("redirect:/listado");
  }

  @GetMapping("/listado")
  public ModelAndView mostrarListado() {
    ModelAndView mav = new ModelAndView("listado");
    // Acá luego reemplazarás el ArrayList vacío por: servicioColeccion.obtenerTodos()
    mav.addObject("perfumesColeccion", new ArrayList<>());
    return mav;
  }
}
