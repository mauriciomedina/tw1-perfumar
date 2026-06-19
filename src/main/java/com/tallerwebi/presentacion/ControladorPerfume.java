package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioColeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  @RequestMapping(path = "/nuevo-perfume", method = RequestMethod.GET)
  public ModelAndView irAFormularioAlta() {
    return new ModelAndView("formularioAltaPerfume");
  }

  @RequestMapping(path = "/guardar-perfume", method = RequestMethod.POST)
  public ModelAndView guardarPerfume(@ModelAttribute("datosPerfume") DatosPerfume datosPerfume) {
    // capa de negocio para hacer el insert en la BD
    servicioColeccion.guardar(datosPerfume);

    // dashboard principal
    return new ModelAndView("redirect:/home");
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

    // 1. Creamos un perfume "de mentira" solo para que el HTML no tire error 500
    // (Asegurate de importar tu clase Perfume arriba de todo)
    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setId(id);
    perfumeSimulado.setNombre("Sauvage"); // Datos de prueba
    perfumeSimulado.setMarca("Dior");

    // 2. Lo mandamos a la vista
    modelo.put("perfume", perfumeSimulado);

    return new ModelAndView("especificacion", modelo);
  }
}
