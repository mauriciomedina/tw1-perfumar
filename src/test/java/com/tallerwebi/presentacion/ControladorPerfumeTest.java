package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.ServicioColeccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPerfumeTest {

  private ControladorPerfume controladorPerfume;
  private ServicioColeccion servicioColeccionMock;

  @BeforeEach
  public void init() {
    servicioColeccionMock = mock(ServicioColeccion.class);

    controladorPerfume = new ControladorPerfume(servicioColeccionMock);
  }

  @Test
  public void queAlNavegarALaEspecificacionMeLleveALaVistaEspecificacion() {
    // Ejecución

    Long idDePrueba = 1L;
    ModelAndView mav = controladorPerfume.mostrarEspecificacion(idDePrueba);

    // Validación
    assertThat(mav.getViewName(), equalToIgnoringCase("especificacion"));
  }

  @Test
  public void queAlNavegarAlFormularioMeLleveALaVistaFormularioAltaPerfume() {
    // Ejecución
    ModelAndView modelAndView = controladorPerfume.irAFormularioAlta();

    // Validación
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("formularioAltaPerfume"));
  }

  @Test
  public void queAlGuardarUnPerfumeExitosamenteSeInvoqueAlServicioYRedirijaAlHome() {
    DatosPerfume datosNuevos = new DatosPerfume();
    datosNuevos.setNombre("Nocturnal Drift");
    datosNuevos.setMarca("Oud & Bergamot");

    ModelAndView mav = controladorPerfume.guardarPerfume(datosNuevos);

    verify(servicioColeccionMock, times(1)).guardar(datosNuevos);

    assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/home"));
  }
}
