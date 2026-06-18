package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tallerwebi.infraestructura.RepositorioPerfumeImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioPerfumeTest {

  private RepositorioPerfumeImpl repositorioMock;
  private ServicioPerfumeImpl servicioPerfume;

  @BeforeEach
  public void init() {
    // Mock que se va a hacer pasar por la base de datos
    repositorioMock = mock(RepositorioPerfumeImpl.class);
    servicioPerfume = new ServicioPerfumeImpl(repositorioMock);
  }

  @Test
  public void queSePuedaConsultarUnPerfumePorNombre() {
    // Preparación
    String nombre = "Fahrenheit";
    Perfume perfumeEsperado = new Perfume();
    perfumeEsperado.setNombre(nombre);

    when(repositorioMock.buscar(nombre)).thenReturn(perfumeEsperado);


    Perfume perfumeObtenido = servicioPerfume.consultarPerfume(nombre);

    // Validación
    assertThat(perfumeObtenido, equalTo(perfumeEsperado));
    verify(repositorioMock, times(1)).buscar(nombre);
  }

  @Test
  public void queSePuedaGrabarUnPerfumeNuevo() throws Exception {
    // Preparación
    Perfume perfumeNuevo = new Perfume();
    perfumeNuevo.setNombre("Acqua di Gio");


    when(repositorioMock.buscar(perfumeNuevo.getNombre())).thenReturn(null);

    servicioPerfume.grabar(perfumeNuevo);

    // Validación: verificamos que el servicio haya mandado a guardar el perfume
    verify(repositorioMock, times(1)).guardar(perfumeNuevo);
  }

  @Test
  public void queLanceExcepcionAlIntentarGrabarUnPerfumeQueYaExiste() {
    // Preparación
    Perfume perfumeExistente = new Perfume();
    perfumeExistente.setNombre("Polo Red");


    when(repositorioMock.buscar(perfumeExistente.getNombre())).thenReturn(perfumeExistente);


    assertThrows(
      Exception.class,
      () -> {
        servicioPerfume.grabar(perfumeExistente);
      }
    );

    // Verificamos que NUNCA se haya llamado al método guardar, porque cortó antes
    verify(repositorioMock, never()).guardar(any(Perfume.class));
  }

  @Test
  public void queSePuedaListarLosPerfumes() {
    // Preparación
    List<Perfume> listaEsperada = new ArrayList<>();
    listaEsperada.add(new Perfume());
    listaEsperada.add(new Perfume());

    when(repositorioMock.listar()).thenReturn(listaEsperada);

    // Ejecución
    List<Perfume> listaObtenida = servicioPerfume.listar();

    // Validación
    assertThat(listaObtenida.size(), equalTo(2));
    verify(repositorioMock, times(1)).listar();
  }
}
