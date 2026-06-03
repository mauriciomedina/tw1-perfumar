package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DatosPerfumeTest {

  @Test
  public void queSePuedanAsignarYObtenerLosDatosDelFormulario() {
    // Preparación
    DatosPerfume datos = new DatosPerfume();

    // Ejecución (Usamos los Setters)
    datos.setNombre("Sauvage");
    datos.setMarca("Dior");
    datos.setEnMaceracion(true);
    datos.setFechaInicio("2024-12-01");

    // Validación (Usamos los Getters)
    assertThat(datos.getNombre(), equalToIgnoringCase("Sauvage"));
    assertThat(datos.getMarca(), equalToIgnoringCase("Dior"));
    assertTrue(datos.getEnMaceracion());
    assertThat(datos.getFechaInicio(), equalToIgnoringCase("2024-12-01"));
  }
}
