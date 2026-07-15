package com.tallerwebi.presentacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tallerwebi.dominio.Clima;
import com.tallerwebi.dominio.ServicioClima;
import com.tallerwebi.dominio.ServicioGemini;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
public class ControladorGemini {

  private static final String SIN_CLIMA =
    "El usuario no tiene ciudad/país configurados en su perfil, así que no hay datos de " +
    "clima disponibles. No le preguntes por su ciudad ni por el clima: simplemente sugerí, " +
    "de forma breve y amable, que complete su ciudad en su perfil para recibir " +
    "recomendaciones ajustadas al clima, y seguí ayudando igual según la ocasión y " +
    "preferencias que indique.";

  private static final String ERROR_CLIMA =
    "No se pudo obtener el clima actual por un problema técnico. No le preguntes la ciudad " +
    "al usuario ni le pidas el clima: seguí ayudando igual según la ocasión y preferencias " +
    "que indique.";

  private static final String SIN_GENERO =
    "El usuario no tiene un género configurado en su perfil. No se lo preguntes: seguí " +
    "ayudando igual según el clima, la ocasión y las preferencias que indique.";

  @Autowired
  private ServicioGemini servicioGemini;

  @Autowired
  private ServicioClima servicioClima;

  @PostMapping("/preguntar")
  public ResponseEntity<?> preguntar(@RequestBody GeminiDto geminiDto, HttpServletRequest request) {
    try {
      String contexto = construirContextoClima(request) + "\n" + construirContextoGenero(request);
      String respuesta = servicioGemini.preguntar(
        geminiDto.getPregunta(),
        contexto,
        false,
        geminiDto.getHistorial()
      );
      geminiDto.setRespuesta(respuesta);
      geminiDto.setContextoActual(servicioGemini.getSystemInstructions());
      return ResponseEntity.ok(geminiDto);
    } catch (JsonProcessingException exception) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al consultar Gemini: " + e.getMessage());
    }
  }

  private String construirContextoClima(HttpServletRequest request) {
    HttpSession session = request.getSession();
    Object ciudad = session.getAttribute("CIUDAD");
    Object pais = session.getAttribute("PAIS");
    if (ciudad == null || pais == null) {
      return SIN_CLIMA;
    }
    try {
      Clima clima = servicioClima.obtenerClima(ciudad.toString(), pais.toString());
      return String.format(
        "Datos de clima actuales del usuario, ya obtenidos por el sistema (no se los pidas " +
        "bajo ninguna circunstancia): ciudad=%s, temperatura=%.0f°C, sensación " +
        "térmica=%.0f°C, humedad=%d%%, condición=%s (%s). Usá estos datos directamente para " +
        "tus recomendaciones.",
        clima.getCiudad(),
        clima.getTemperatura(),
        clima.getSensacionTermica(),
        clima.getHumedad(),
        clima.getCondicion(),
        clima.getDescripcion()
      );
    } catch (Exception e) {
      return ERROR_CLIMA;
    }
  }

  private String construirContextoGenero(HttpServletRequest request) {
    Object genero = request.getSession().getAttribute("GENERO");
    if (genero == null || genero.toString().isEmpty()) {
      return SIN_GENERO;
    }
    return String.format(
      "Género del usuario, ya obtenido por el sistema (no se lo pidas bajo ninguna " +
      "circunstancia): %s. Usalo como un factor más para elegir qué perfumes del catálogo " +
      "recomendar.",
      genero
    );
  }

  @PostMapping("/limpiar")
  public ResponseEntity<Void> limpiarContexto() {
    this.servicioGemini.limpiarContexto();
    return ResponseEntity.ok().build();
  }
}
