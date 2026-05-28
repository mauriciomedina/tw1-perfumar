package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioColeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPerfume {

  private ServicioColeccion servicioColeccion;

  // Inyección de dependencias a través del constructor
  @Autowired
  public ControladorPerfume(ServicioColeccion servicioColeccion) {
    this.servicioColeccion = servicioColeccion;
  }

  // Endpoint para ver el detalle de un perfume
  @RequestMapping(path = "/especificacion", method = RequestMethod.GET)
  public ModelAndView irAEspecificacion() {
    return new ModelAndView("especificacion");
  }

  // Endpoint para abrir el formulario de carga
  @RequestMapping(path = "/nuevo-perfume", method = RequestMethod.GET)
  public ModelAndView irAFormularioAlta() {
    return new ModelAndView("formularioAltaPerfume");
  }

  // Endpoint que recibe los datos del formulario y los manda a guardar
  @RequestMapping(path = "/guardar-perfume", method = RequestMethod.POST)
  public ModelAndView guardarPerfume(@ModelAttribute("datosPerfume") DatosPerfume datosPerfume) {
    // Llamamos a la capa de negocio para hacer el insert en la BD
    servicioColeccion.guardar(datosPerfume);

    // Redirigimos al dashboard principal
    return new ModelAndView("redirect:/listar-perfumes");
  }

  @RequestMapping("/listar-perfumes")
  public ModelAndView listarPerfumes() {
    ModelMap modelo = new ModelMap();
    modelo.put("mensaje", "nuevo perfume");
    modelo.put("perfumes", servicioColeccion.listar());
    return new ModelAndView("listar-perfumes", modelo);
  }
}
