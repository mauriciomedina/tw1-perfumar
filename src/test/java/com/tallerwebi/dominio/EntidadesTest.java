package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class EntidadesTest {

  @Test
  public void queSePuedanAsignarYObtenerDatosDePerfumeYColeccion() {
    // 1. Probamos la entidad Perfume
    Perfume perfume = new Perfume();
    perfume.setId(1L);
    perfume.setNombre("Fahrenheit");
    perfume.setMarca("Dior");

    assertEquals(1L, perfume.getId());
    assertThat(perfume.getNombre(), equalToIgnoringCase("Fahrenheit"));
    assertThat(perfume.getMarca(), equalToIgnoringCase("Dior"));

    // 2. Probamos la entidad Coleccion
    Coleccion coleccion = new Coleccion();
    coleccion.setId(1L);
    coleccion.setPerfume(perfume);
    coleccion.setEnMaceracion(true);
    coleccion.setFechaInicioMaceracion(LocalDate.parse("2024-01-01"));

    assertEquals(1L, coleccion.getId());
    assertNotNull(coleccion.getPerfume());
    assertTrue(coleccion.getEnMaceracion());
    assertEquals(LocalDate.parse("2024-01-01"), coleccion.getFechaInicioMaceracion());
  }
}
