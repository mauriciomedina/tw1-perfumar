package com.tallerwebi.infraestructura;

import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioColeccionImplTest {

  private RepositorioColeccion repositorioColeccionMock;
  private ServicioColeccionImpl servicioColeccion;

  @BeforeEach
  public void init() {
    // Preparamos el mock y el servicio antes de cada test
    repositorioColeccionMock = mock(RepositorioColeccion.class);
    servicioColeccion = new ServicioColeccionImpl(repositorioColeccionMock);
  }

  @Test
  public void queAlGuardarEnColeccionSeArmeElObjetoYSeGuardeEnElRepositorio() {
    // 1. Preparación
    Long idPerfume = 1L;
    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setId(idPerfume);

    // Le enseñamos al mock qué hacer cuando el servicio le pida el perfume
    when(repositorioColeccionMock.buscarPerfume(idPerfume)).thenReturn(perfumeSimulado);

    // 2. Ejecución
    servicioColeccion.guardarEnColeccion(idPerfume);

    // 3. Validación
    // Verificamos que el servicio haya llamado al método guardarColeccion pasándole un objeto
    verify(repositorioColeccionMock, times(1)).guardarColeccion(any(Coleccion.class));
  }
}
