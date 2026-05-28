package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioPerfume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPerfume {

  private ServicioPerfume servicioPerfume;

  @Autowired
  public ControladorPerfume(ServicioPerfume servicioPerfume) {
    this.servicioPerfume = servicioPerfume;
  }

  @RequestMapping("/nuevo-perfume")
  public ModelAndView nuevoPerfume() {
    ModelMap modelo = new ModelMap();
    modelo.put("perfume", new Perfume());
    modelo.put("mensaje", "nuevo perfume");
    return new ModelAndView("nuevo-perfume", modelo);
  }

  @RequestMapping(path = "/grabarPerfume", method = RequestMethod.POST)
  public ModelAndView grabarPerfume(@ModelAttribute("perfume") Perfume perfume) {
    ModelMap model = new ModelMap();
    try {
      servicioPerfume.grabar(perfume);
    } catch (Exception e) {
      model.put("error", "Error al grabar Perfume");
      return new ModelAndView("nuevo-perfume", model);
    }
    return new ModelAndView("redirect:/listar-perfumes");
  }

  @RequestMapping("/listar-perfumes")
  public ModelAndView listarPerfumes() {
    ModelMap modelo = new ModelMap();
    modelo.put("mensaje", "nuevo perfume");
    modelo.put("perfumes", servicioPerfume.listar());
    return new ModelAndView("listar-perfumes", modelo);
  }
}
