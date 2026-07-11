package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class EntidadesTest {

  @Test
  public void queSePuedaAsignarYObtenerDatosDePerfume() {
    Perfume perfume = new Perfume();
    perfume.setId(1L);
    perfume.setNombre("Fahrenheit");
    perfume.setMarca(Marca.DIOR);
    perfume.setFamiliaOlfativa(FamiliaOlfativa.AMADERADA);

    assertEquals(1L, perfume.getId());
    assertThat(perfume.getNombre(), equalToIgnoringCase("Fahrenheit"));
    assertEquals(Marca.DIOR, perfume.getMarca());
    assertEquals(FamiliaOlfativa.AMADERADA, perfume.getFamiliaOlfativa());
  }

  @Test
  public void queSePuedaAsignarYObtenerDatosDeColeccion() {
    Perfume perfume = new Perfume();
    perfume.setId(1L);

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

  @Test
  public void queSePuedaAsignarYObtenerDatosDeUsuario() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNombre("Juan Perez");
    usuario.setEmail("test@unlam.edu.ar");
    usuario.setPassword("1234");
    usuario.setRol("ADMIN");
    usuario.setActivo(true);
    usuario.setCiudad("Buenos Aires");
    usuario.setPais("AR");

    assertEquals(1L, usuario.getId());
    assertThat(usuario.getNombre(), equalToIgnoringCase("Juan Perez"));
    assertThat(usuario.getEmail(), equalToIgnoringCase("test@unlam.edu.ar"));
    assertThat(usuario.getPassword(), equalToIgnoringCase("1234"));
    assertThat(usuario.getRol(), equalToIgnoringCase("ADMIN"));
    assertTrue(usuario.getActivo());
    assertThat(usuario.getCiudad(), equalToIgnoringCase("Buenos Aires"));
    assertThat(usuario.getPais(), equalToIgnoringCase("AR"));
  }
}
