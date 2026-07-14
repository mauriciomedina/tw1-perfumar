package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Clima;
import com.tallerwebi.dominio.FamiliaOlfativa;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioClima;
import com.tallerwebi.dominio.ServicioColeccion;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorClima {

  private static final int VIENTO_FUERTE = 7;
  private static final int HUMEDAD_ALTA = 75;
  private static final int TEMPERATURA_CALOR_HUMEDO = 25;
  private static final int NUBOSIDAD_ALTA = 70;
  private static final int TEMPERATURA_ALTA = 28;
  private static final int TEMPERATURA_MEDIA = 20;
  private static final int TEMPERATURA_BAJA = 12;

  private static final String CONDICION_TORMENTA = "Thunderstorm";
  private static final String CONDICION_LLUVIA = "Rain";
  private static final String CONDICION_LLOVIZNA = "Drizzle";
  private static final String CONDICION_NIEVE = "Snow";

  private static final Map<FamiliaOlfativa, String> INTENSIDAD = Map.of(
    FamiliaOlfativa.CITRICA,
    "20%",
    FamiliaOlfativa.FRUTAL,
    "35%",
    FamiliaOlfativa.HELECHO,
    "50%",
    FamiliaOlfativa.AMADERADA,
    "70%",
    FamiliaOlfativa.ORIENTAL,
    "85%"
  );

  @Autowired
  private ServicioClima servicioClima;

  @Autowired
  private ServicioColeccion servicioColeccion;

  @GetMapping(value = "/api/clima", produces = "application/json")
  @ResponseBody
  public ResponseEntity<?> obtenerClima(
    @RequestParam("city") String city,
    @RequestParam("country") String country
  ) {
    try {
      Clima clima = servicioClima.obtenerClima(city, country);
      return ResponseEntity.ok(clima);
    } catch (HttpClientErrorException.NotFound e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ciudad no encontrada.");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al consultar el clima: " + e.getMessage());
    }
  }

  @GetMapping("/bienvenida")
  public ModelAndView irABienvenida(
    @ModelAttribute("climaActual") Clima clima,
    HttpServletRequest request
  ) {
    Long idUsuario = (Long) request.getSession().getAttribute("USUARIO_ID");
    if (idUsuario == null) return new ModelAndView("redirect:/login");

    ModelMap model = new ModelMap();
    List<Perfume> perfumes = servicioColeccion.listar();

    if (!perfumes.isEmpty()) {
      List<FamiliaOlfativa> familias = determinarFamilias(clima);
      Perfume perfumeDelDia = elegirPerfumeDelDia(perfumes, familias);
      cargarModeloPerfumeDelDia(model, perfumeDelDia);
    }

    model.put("pddiaClimaInfo", construirTextoClima(clima));

    return new ModelAndView("bienvenida", model);
  }

  private String construirTextoClima(Clima clima) {
    if (clima == null || clima.getDescripcion() == null || clima.getDescripcion().isEmpty()) {
      return "Configurá tu ciudad en tu perfil para ver recomendaciones según el clima";
    }
    String descripcion = clima.getDescripcion();
    String descripcionCapitalizada =
      descripcion.substring(0, 1).toUpperCase(Locale.ROOT) + descripcion.substring(1);
    return descripcionCapitalizada + " · " + Math.round(clima.getSensacionTermica()) + "°C";
  }

  private void cargarModeloPerfumeDelDia(ModelMap model, Perfume perfume) {
    model.put("pddiaImagen", perfume.getImagenUrl());
    model.put("pddiaNombre", perfume.getNombre());
    model.put(
      "pddiaMarca",
      perfume.getMarca() != null ? perfume.getMarca().getNombreVisible() : ""
    );
    model.put("pddiaDescripcion", perfume.getDescripcion());
    model.put(
      "pddiaFamilia",
      perfume.getFamiliaOlfativa() != null ? perfume.getFamiliaOlfativa().getNombreVisible() : ""
    );
    model.put("pddiaNotas", "Notas: " + perfume.getNotas());
    model.put(
      "pddiaIntensidad",
      perfume.getFamiliaOlfativa() != null ? INTENSIDAD.get(perfume.getFamiliaOlfativa()) : "50%"
    );
    model.put("pddiaIdPerfume", perfume.getId());
  }

  private List<FamiliaOlfativa> determinarFamilias(Clima clima) {
    if (clima == null) {
      return List.of(FamiliaOlfativa.AMADERADA, FamiliaOlfativa.HELECHO);
    }
    if (esClimaAdverso(clima)) {
      return List.of(FamiliaOlfativa.ORIENTAL, FamiliaOlfativa.AMADERADA);
    }
    if (esCalorHumedo(clima)) {
      return List.of(FamiliaOlfativa.CITRICA);
    }
    if (clima.getNubosidad() > NUBOSIDAD_ALTA) {
      return List.of(FamiliaOlfativa.AMADERADA, FamiliaOlfativa.HELECHO);
    }
    return determinarFamiliasPorTemperatura(clima.getSensacionTermica());
  }

  private boolean esClimaAdverso(Clima clima) {
    boolean condicionSevera =
      CONDICION_TORMENTA.equals(clima.getCondicion()) ||
      CONDICION_LLUVIA.equals(clima.getCondicion()) ||
      CONDICION_LLOVIZNA.equals(clima.getCondicion()) ||
      CONDICION_NIEVE.equals(clima.getCondicion());
    return condicionSevera || clima.getVelocidadViento() > VIENTO_FUERTE;
  }

  private boolean esCalorHumedo(Clima clima) {
    return (
      clima.getHumedad() > HUMEDAD_ALTA && clima.getSensacionTermica() > TEMPERATURA_CALOR_HUMEDO
    );
  }

  private List<FamiliaOlfativa> determinarFamiliasPorTemperatura(double temperatura) {
    if (temperatura > TEMPERATURA_ALTA) {
      return List.of(FamiliaOlfativa.CITRICA, FamiliaOlfativa.FRUTAL);
    }
    if (temperatura >= TEMPERATURA_MEDIA) {
      return List.of(FamiliaOlfativa.HELECHO, FamiliaOlfativa.FRUTAL);
    }
    if (temperatura >= TEMPERATURA_BAJA) {
      return List.of(FamiliaOlfativa.AMADERADA, FamiliaOlfativa.HELECHO);
    }
    return List.of(FamiliaOlfativa.ORIENTAL, FamiliaOlfativa.AMADERADA);
  }

  private Perfume elegirPerfumeDelDia(List<Perfume> perfumes, List<FamiliaOlfativa> familias) {
    LocalDate hoy = LocalDate.now();

    for (FamiliaOlfativa familia : familias) {
      List<Perfume> candidatos = perfumes
        .stream()
        .filter(perfume -> perfume.getFamiliaOlfativa() == familia)
        .collect(Collectors.toList());
      if (!candidatos.isEmpty()) {
        int semilla = hoy.getYear() * 10000 + hoy.getMonthValue() * 100 + hoy.getDayOfMonth();
        return candidatos.get(semilla % candidatos.size());
      }
    }
    return perfumes.get(hoy.getDayOfMonth() % perfumes.size());
  }
}
