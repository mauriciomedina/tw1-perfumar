package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioColeccion;
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

  @Autowired
  public ControladorPerfume(ServicioColeccion servicioColeccion) {
    this.servicioColeccion = servicioColeccion;
  }

  @RequestMapping("/listar-perfumes")
  public ModelAndView listarPerfumes() {
    ModelMap modelo = new ModelMap();
    modelo.put("mensaje", "nuevo perfume");
    modelo.put("perfumes", servicioColeccion.listar());
    return new ModelAndView("listar-perfumes", modelo);
  }

  @RequestMapping(path = "/especificacion", method = RequestMethod.GET)
  public ModelAndView mostrarEspecificacion(@RequestParam("id") Long id) {
    ModelMap modelo = new ModelMap();

    Perfume perfumeReal = servicioColeccion.buscarPerfume(id);

    modelo.put("perfume", perfumeReal);

    return new ModelAndView("especificacion", modelo);
  }
}
