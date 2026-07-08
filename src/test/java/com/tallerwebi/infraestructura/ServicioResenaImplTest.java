package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioResena;
import com.tallerwebi.dominio.Resena;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioResenaImplTest {

  private RepositorioResena repositorioResenaMock;
  private ServicioResenaImpl servicioResena;

  @BeforeEach
  public void init() {
    repositorioResenaMock = mock(RepositorioResena.class);
    servicioResena = new ServicioResenaImpl(repositorioResenaMock);
  }

  @Test
  public void queAlAgregarUnaResenaValidaSeGuardeEnElRepositorio() {
    Long idPerfume = 1L;
    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setId(idPerfume);

    when(repositorioResenaMock.buscarPerfume(idPerfume)).thenReturn(perfumeSimulado);

    servicioResena.agregarResena(1L, idPerfume, "Muy buena fragancia", 5);

    verify(repositorioResenaMock, times(1)).guardar(any(Resena.class));
  }

  @Test
  public void queNoSeGuardeUnaResenaConComentarioVacio() {
    servicioResena.agregarResena(1L, 1L, "   ", 5);

    verify(repositorioResenaMock, never()).guardar(any(Resena.class));
  }

  @Test
  public void quePuntuacionesFueraDeRangoSeAcotenEntreUnoYCinco() {
    Long idPerfume = 1L;
    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setId(idPerfume);
    when(repositorioResenaMock.buscarPerfume(idPerfume)).thenReturn(perfumeSimulado);

    servicioResena.agregarResena(1L, idPerfume, "Comentario valido", 10);

    verify(repositorioResenaMock).guardar(argThat(resena -> resena.getPuntuacion() == 5));
  }
}
