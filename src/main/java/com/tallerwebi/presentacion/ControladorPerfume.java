package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Local;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.dominio.ServicioLocal;
import java.util.List;
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
  private ServicioLocal servicioLocal;

  @Autowired
  public ControladorPerfume(ServicioColeccion servicioColeccion, ServicioLocal servicioLocal) {
    this.servicioColeccion = servicioColeccion;
    this.servicioLocal = servicioLocal;
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
    @RequestParam(value = "lat", required = false) Double lat,
    @RequestParam(value = "lon", required = false) Double lon
  ) {
    ModelMap modelo = new ModelMap();

    Perfume perfumeReal = servicioColeccion.buscarPerfume(id);
    modelo.put("perfume", perfumeReal);

    if (lat != null && lon != null) {
      List<Local> localesCercanos = this.servicioLocal.obtenerLocalesMasCercanos(lat, lon, 3);

      modelo.put("locales", localesCercanos);
      modelo.put("latUsuario", lat);
      modelo.put("lonUsuario", lon);
    }

    return new ModelAndView("especificacion", modelo);
  }
}
