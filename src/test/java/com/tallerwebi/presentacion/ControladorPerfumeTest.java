package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.presentacion.ControladorPerfume;
import com.tallerwebi.presentacion.DatosPerfume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPerfumeTest {

  private ControladorPerfume controladorPerfume;
  private ServicioColeccion servicioColeccionMock; // Nuestro actor de doble de riesgo

  @BeforeEach
  public void init() {
    // Antes de CADA test, preparamos el entorno:
    // 1. Creamos el mock del servicio (simulamos la base de datos)
    servicioColeccionMock = mock(ServicioColeccion.class);
    // 2. Le pasamos ese mock al controlador para que no tire error
    controladorPerfume = new ControladorPerfume(servicioColeccionMock);
  }

  // --- TEST 1: Navegación a Especificación ---
  @Test
  public void queAlNavegarALaEspecificacionMeLleveALaVistaEspecificacion() {
    // Ejecución
    ModelAndView modelAndView = controladorPerfume.irAEspecificacion();

    // Validación
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("especificacion"));
  }

  // --- TEST 2: Navegación a Formulario ---
  @Test
  public void queAlNavegarAlFormularioMeLleveALaVistaFormularioAltaPerfume() {
    // Ejecución
    ModelAndView modelAndView = controladorPerfume.irAFormularioAlta();

    // Validación
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("formularioAltaPerfume"));
  }

  // --- TEST 3: Guardar Datos y Redirigir ---
  @Test
  public void queAlGuardarUnPerfumeExitosamenteSeInvoqueAlServicioYRedirijaAlHome() {
    // Preparación: Armamos el paquetito de datos que vendría del HTML
    DatosPerfume datosNuevos = new DatosPerfume();
    datosNuevos.setNombre("Nocturnal Drift");
    datosNuevos.setMarca("Oud & Bergamot");

    // Ejecución: Simulamos que el usuario hizo click en "Guardar"
    ModelAndView mav = controladorPerfume.guardarPerfume(datosNuevos);

    // Validación 1: ¿El controlador le avisó al servicio que guarde? (TDD puro)
    verify(servicioColeccionMock, times(1)).guardar(datosNuevos);

    // Validación 2: ¿Nos redirigió a la pantalla principal?
    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/home"));
  }
}
