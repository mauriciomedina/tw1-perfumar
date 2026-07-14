package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.ServicioFavorito;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FavoritoApiControllerTest {

  private ServicioFavorito servicioFavoritoMock;
  private HttpServletRequest requestMock;
  private HttpSession sessionMock;
  private FavoritoApiController favoritoApiController;

  @BeforeEach
  public void init() {
    servicioFavoritoMock = mock(ServicioFavorito.class);
    requestMock = mock(HttpServletRequest.class);
    sessionMock = mock(HttpSession.class);

    when(requestMock.getSession()).thenReturn(sessionMock);

    favoritoApiController = new FavoritoApiController(servicioFavoritoMock);
  }

  @Test
  public void queSinUsuarioLogueadoDevuelvaListaVacia() {
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(null);

    List<PerfumeCatalogoDTO> resultado = favoritoApiController.obtenerFavoritos(requestMock);

    assertThat(resultado, hasSize(0));
  }

  @Test
  public void queDevuelvaLosFavoritosDelUsuarioLogueado() {
    when(sessionMock.getAttribute("USUARIO_ID")).thenReturn(1L);

    Perfume favorito = new Perfume();
    favorito.setId(5L);
    favorito.setNombre("Sauvage");

    when(servicioFavoritoMock.listar(1L)).thenReturn(Collections.singletonList(favorito));

    List<PerfumeCatalogoDTO> resultado = favoritoApiController.obtenerFavoritos(requestMock);

    assertThat(resultado, hasSize(1));
  }
}
