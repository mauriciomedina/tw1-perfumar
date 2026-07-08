package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioResena;
import com.tallerwebi.dominio.Resena;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import java.time.LocalDate;
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
public class RepositorioResenaTest {

  @Autowired
  private RepositorioResena repositorioResena;

  @Autowired
  private SessionFactory sessionFactory;

  @Test
  @Transactional
  @Rollback
  public void queAlGuardarUnaResenaAparezcaEnElListadoDelPerfume() {
    Usuario usuarioPrueba = new Usuario();
    usuarioPrueba.setEmail("resena1@test.com");
    this.sessionFactory.getCurrentSession().save(usuarioPrueba);

    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setNombre("Bleu de Chanel");
    this.sessionFactory.getCurrentSession().save(perfumeSimulado);

    Resena nuevaResena = new Resena();
    nuevaResena.setUsuario(usuarioPrueba);
    nuevaResena.setPerfume(perfumeSimulado);
    nuevaResena.setComentario("Me encantó, dura todo el día.");
    nuevaResena.setPuntuacion(5);
    nuevaResena.setFecha(LocalDate.now());

    repositorioResena.guardar(nuevaResena);

    List<Resena> resenasDb = repositorioResena.listarPorPerfume(perfumeSimulado.getId());

    assertThat(resenasDb.size(), is(1));
    assertThat(
      resenasDb.get(0).getComentario(),
      equalToIgnoringCase("Me encantó, dura todo el día.")
    );
  }

  @Test
  @Transactional
  @Rollback
  public void quePromedioDePuntuacionCalculeBienElPromedio() {
    Usuario usuarioPrueba = new Usuario();
    usuarioPrueba.setEmail("resena2@test.com");
    this.sessionFactory.getCurrentSession().save(usuarioPrueba);

    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setNombre("Sauvage");
    this.sessionFactory.getCurrentSession().save(perfumeSimulado);

    Resena resenaUno = new Resena();
    resenaUno.setUsuario(usuarioPrueba);
    resenaUno.setPerfume(perfumeSimulado);
    resenaUno.setComentario("Buenisimo");
    resenaUno.setPuntuacion(4);
    resenaUno.setFecha(LocalDate.now());
    repositorioResena.guardar(resenaUno);

    Resena resenaDos = new Resena();
    resenaDos.setUsuario(usuarioPrueba);
    resenaDos.setPerfume(perfumeSimulado);
    resenaDos.setComentario("Excelente");
    resenaDos.setPuntuacion(2);
    resenaDos.setFecha(LocalDate.now());
    repositorioResena.guardar(resenaDos);

    Double promedio = repositorioResena.promedioDePuntuacion(perfumeSimulado.getId());

    assertThat(promedio, is(3.0));
  }
}
