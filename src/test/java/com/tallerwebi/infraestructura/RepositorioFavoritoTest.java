package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.Favorito;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioFavorito;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebTestConfig.class, HibernateTestConfig.class })
public class RepositorioFavoritoTest {

  @Autowired
  private RepositorioFavorito repositorioFavorito;

  @Autowired
  private SessionFactory sessionFactory;

  @Test
  @Transactional
  @Rollback
  public void queAlGuardarUnFavoritoSePuedaListarSuPerfume() {
    Usuario usuarioPrueba = new Usuario();
    usuarioPrueba.setEmail("test@test.com");
    this.sessionFactory.getCurrentSession().save(usuarioPrueba);

    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setNombre("Bleu de Chanel");
    this.sessionFactory.getCurrentSession().save(perfumeSimulado);

    Favorito nuevoFavorito = new Favorito();
    nuevoFavorito.setPerfume(perfumeSimulado);
    nuevoFavorito.setUsuario(usuarioPrueba);

    repositorioFavorito.guardarFavorito(nuevoFavorito);

    List<Perfume> listadoDb = repositorioFavorito.listar(usuarioPrueba.getId());

    assertThat(listadoDb.size(), is(1));
    assertThat(listadoDb.get(0).getNombre(), equalToIgnoringCase("Bleu de Chanel"));
  }

  @Test
  @Transactional
  @Rollback
  public void queExistaDevuelvaTrueSiElPerfumeYaEsFavoritoDelUsuario() {
    Usuario usuarioPrueba = new Usuario();
    usuarioPrueba.setEmail("test2@test.com");
    this.sessionFactory.getCurrentSession().save(usuarioPrueba);

    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setNombre("Sauvage");
    this.sessionFactory.getCurrentSession().save(perfumeSimulado);

    Favorito nuevoFavorito = new Favorito();
    nuevoFavorito.setPerfume(perfumeSimulado);
    nuevoFavorito.setUsuario(usuarioPrueba);
    repositorioFavorito.guardarFavorito(nuevoFavorito);

    boolean existe = repositorioFavorito.existe(usuarioPrueba.getId(), perfumeSimulado.getId());

    assertThat(existe, is(true));
  }

  @Test
  @Transactional
  @Rollback
  public void queAlEliminarUnFavoritoYaNoAparezcaEnElListado() {
    Usuario usuarioPrueba = new Usuario();
    usuarioPrueba.setEmail("test3@test.com");
    this.sessionFactory.getCurrentSession().save(usuarioPrueba);

    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setNombre("Aventus");
    this.sessionFactory.getCurrentSession().save(perfumeSimulado);

    Favorito nuevoFavorito = new Favorito();
    nuevoFavorito.setPerfume(perfumeSimulado);
    nuevoFavorito.setUsuario(usuarioPrueba);
    repositorioFavorito.guardarFavorito(nuevoFavorito);

    repositorioFavorito.eliminar(usuarioPrueba.getId(), perfumeSimulado.getId());

    List<Perfume> listadoDb = repositorioFavorito.listar(usuarioPrueba.getId());

    assertThat(listadoDb.size(), is(0));
  }
}
