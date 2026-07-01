package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.integracion.config.TestRestTemplateConfig;
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
@ContextConfiguration(
  classes = { SpringWebTestConfig.class, HibernateTestConfig.class, TestRestTemplateConfig.class }
)
public class RepositorioColeccionTest {

  @Autowired
  private RepositorioColeccion repositorioColeccion;

  @Autowired
  private SessionFactory sessionFactory;

  @Test
  @Transactional
  @Rollback
  public void queAlGuardarUnaColeccionSePuedaListarSuPerfume() {
    Usuario usuarioPrueba = new Usuario();
    usuarioPrueba.setEmail("test@test.com");

    this.sessionFactory.getCurrentSession().save(usuarioPrueba);

    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setNombre("Tom Ford Ombré Leather");
    repositorioColeccion.guardarPerfume(perfumeSimulado);

    Coleccion nuevaColeccion = new Coleccion();
    nuevaColeccion.setPerfume(perfumeSimulado);
    nuevaColeccion.setUsuario(usuarioPrueba);

    repositorioColeccion.guardarColeccion(nuevaColeccion);

    List<Perfume> listadoDb = repositorioColeccion.listar(usuarioPrueba.getId());

    assertThat(listadoDb.size(), is(1));
    assertThat(listadoDb.get(0).getNombre(), equalToIgnoringCase("Tom Ford Ombré Leather"));
  }
}
