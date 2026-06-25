package com.tallerwebi.infraestructura;

import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioColeccionTest {

  private RepositorioColeccion repositorioMock;
  private ServicioColeccionImpl servicioColeccion;

  @BeforeEach
  public void init() {
    repositorioMock = mock(RepositorioColeccion.class);
    servicioColeccion = new ServicioColeccionImpl(repositorioMock);
  }

  @Test
  public void queSePuedaGuardarUnPerfumeEnLaColeccionSiNoLoTeniaAntes() {
    Long idUsuario = 1L;
    Long idPerfume = 10L;

    when(repositorioMock.listar(idUsuario)).thenReturn(new ArrayList<>());

    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setId(idPerfume);

    when(repositorioMock.buscarPerfume(idPerfume)).thenReturn(perfumeSimulado);

    servicioColeccion.guardarEnColeccion(idUsuario, idPerfume);

    verify(repositorioMock, times(1)).guardarColeccion(any(Coleccion.class));
  }
}
