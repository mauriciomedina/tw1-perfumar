package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Favorito;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioFavorito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioFavoritoImplTest {

  private RepositorioFavorito repositorioFavoritoMock;
  private ServicioFavoritoImpl servicioFavorito;

  @BeforeEach
  public void init() {
    repositorioFavoritoMock = mock(RepositorioFavorito.class);
    servicioFavorito = new ServicioFavoritoImpl(repositorioFavoritoMock);
  }

  @Test
  public void queAlAgregarAFavoritosSeArmeElObjetoYSeGuardeEnElRepositorio() {
    Long idPerfume = 1L;
    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setId(idPerfume);

    when(repositorioFavoritoMock.existe(1L, idPerfume)).thenReturn(false);
    when(repositorioFavoritoMock.buscarPerfume(idPerfume)).thenReturn(perfumeSimulado);

    servicioFavorito.agregarAFavoritos(1L, idPerfume);

    verify(repositorioFavoritoMock, times(1)).guardarFavorito(any(Favorito.class));
  }

  @Test
  public void queNoSeDupliqueUnFavoritoQueYaExiste() {
    Long idPerfume = 1L;

    when(repositorioFavoritoMock.existe(1L, idPerfume)).thenReturn(true);

    servicioFavorito.agregarAFavoritos(1L, idPerfume);

    verify(repositorioFavoritoMock, never()).guardarFavorito(any(Favorito.class));
  }

  @Test
  public void queEsFavoritoConsulteAlRepositorio() {
    when(repositorioFavoritoMock.existe(1L, 2L)).thenReturn(true);

    boolean resultado = servicioFavorito.esFavorito(1L, 2L);

    assertThat(resultado, is(true));
  }
}
