package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Clima;
import com.tallerwebi.dominio.ServicioClima;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AtributosClimaGlobal {

  @Autowired
  private ServicioClima servicioClima;

  // Se expone únicamente el objeto Clima (no un String suelto): RedirectView agrega
  // automáticamente los atributos del modelo de tipos simples (String, Number, etc.)
  // como query params a cualquier "redirect:...", lo que ensuciaría todas las
  // redirecciones de la app (login, logout, etc.) con "?algo=...".
  @ModelAttribute("climaActual")
  public Clima climaActual(HttpServletRequest request) {
    Object ciudad = request.getSession().getAttribute("CIUDAD");
    Object pais = request.getSession().getAttribute("PAIS");
    if (ciudad == null || pais == null) {
      return null;
    }
    try {
      return servicioClima.obtenerClima(ciudad.toString(), pais.toString());
    } catch (Exception e) {
      return null;
    }
  }
}
