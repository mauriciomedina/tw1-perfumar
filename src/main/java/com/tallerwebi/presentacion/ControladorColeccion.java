package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioColeccion;
import java.util.List;
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
  public ModelAndView agregarPerfume(@RequestParam("idPerfume") Long idPerfume) {
    servicioColeccion.guardarEnColeccion(idPerfume);
    return new ModelAndView("redirect:/listado");
  }

  @RequestMapping(path = "/listado", method = RequestMethod.GET)
  public ModelAndView mostrarListado() {
    ModelMap modelo = new ModelMap();

    //el servicio real trae los perfumes de la base de datos
    List<Perfume> misPerfumes = servicioColeccion.listar();

    // se muestra la vista de coleccion de perfumes
    modelo.put("perfumesColeccion", misPerfumes);

    return new ModelAndView("listado", modelo);
  }
}
