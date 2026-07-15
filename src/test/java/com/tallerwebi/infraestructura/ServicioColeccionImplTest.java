package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.ItemColeccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.dominio.ServicioFavorito;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioColeccionImplTest {

  private RepositorioColeccion repositorioColeccionMock;
  private ServicioColeccionImpl servicioColeccion;
  private ServicioFavorito servicioFavoritoMock;

  @BeforeEach
  public void init() {
    // Preparamos el mock y el servicio antes de cada test
    repositorioColeccionMock = mock(RepositorioColeccion.class);
    servicioFavoritoMock = mock(ServicioFavorito.class);
    servicioColeccion = new ServicioColeccionImpl(repositorioColeccionMock, servicioFavoritoMock);
  }

  @Test
  public void queAlGuardarEnColeccionSeArmeElObjetoYSeGuardeEnElRepositorio() {
    // 1. Preparación
    Long idPerfume = 1L;
    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setId(idPerfume);

    when(repositorioColeccionMock.buscarPerfume(idPerfume)).thenReturn(perfumeSimulado);

    // 2. Ejecución
    servicioColeccion.guardarEnColeccion(1L, idPerfume);

    // 3. Validación
    verify(repositorioColeccionMock, times(1)).guardarColeccion(any(Coleccion.class));
  }

  @Test
  public void queAlListarConDetalleSeMarqueComoFavoritoSiCorresponde() {
    Long idUsuario = 1L;
    Perfume perfume = new Perfume();
    perfume.setId(5L);

    Coleccion coleccion = new Coleccion();
    coleccion.setId(10L);
    coleccion.setPerfume(perfume);
    coleccion.setEnMaceracion(false);

    when(repositorioColeccionMock.listarEntidades(idUsuario))
      .thenReturn(Collections.singletonList(coleccion));
    when(servicioFavoritoMock.esFavorito(idUsuario, 5L)).thenReturn(true);

    List<ItemColeccion> items = servicioColeccion.listarConDetalle(idUsuario);

    assertThat(items.size(), is(1));
    assertThat(items.get(0).isFavorito(), is(true));
    assertThat(items.get(0).isEnMaceracion(), is(false));
  }

  @Test
  public void queAlIniciarMaceracionSeActualiceLaColeccionEncontrada() {
    Long idUsuario = 1L;
    Long idPerfume = 5L;
    LocalDate fechaElegida = LocalDate.now().minusDays(3);

    Coleccion coleccion = new Coleccion();
    when(repositorioColeccionMock.buscarColeccion(idUsuario, idPerfume)).thenReturn(coleccion);

    servicioColeccion.iniciarMaceracion(idUsuario, idPerfume, fechaElegida);

    assertThat(coleccion.getEnMaceracion(), is(true));
    assertThat(coleccion.getFechaInicioMaceracion(), is(fechaElegida));
  }

  @Test
  public void queAlCancelarMaceracionSeLimpieLaFechaYElEstado() {
    Long idUsuario = 1L;
    Long idPerfume = 5L;

    Coleccion coleccion = new Coleccion();
    coleccion.setEnMaceracion(true);
    coleccion.setFechaInicioMaceracion(LocalDate.now());
    when(repositorioColeccionMock.buscarColeccion(idUsuario, idPerfume)).thenReturn(coleccion);

    servicioColeccion.cancelarMaceracion(idUsuario, idPerfume);

    assertThat(coleccion.getEnMaceracion(), is(false));
    assertThat(coleccion.getFechaInicioMaceracion(), is(nullValue()));
  }
}
