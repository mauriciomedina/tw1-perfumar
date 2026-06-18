package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import java.util.List;
import javax.transaction.Transactional;
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
public class RepositorioColeccionTest {

  @Autowired
  private RepositorioColeccion repositorioColeccion;

  @Test
  @Transactional
  @Rollback
  public void queAlGuardarUnaColeccionSePuedaListarSuPerfume() {
    // 1. Preparación
    Perfume perfumeSimulado = new Perfume();
    perfumeSimulado.setNombre("Tom Ford Ombré Leather");
    repositorioColeccion.guardarPerfume(perfumeSimulado); // Guardamos en el catálogo

    Coleccion nuevaColeccion = new Coleccion();
    nuevaColeccion.setPerfume(perfumeSimulado);

    // 2. Ejecución
    repositorioColeccion.guardarColeccion(nuevaColeccion); // Guardamos el vínculo
    List<Perfume> listadoDb = repositorioColeccion.listar();

    // 3. Validación
    assertThat(listadoDb, is(not(empty())));
    assertThat(listadoDb.size(), is(1));
    assertThat(listadoDb.get(0).getNombre(), equalToIgnoringCase("Tom Ford Ombré Leather"));
  }
}
