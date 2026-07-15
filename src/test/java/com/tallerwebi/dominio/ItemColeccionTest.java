package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class ItemColeccionTest {

  @Test
  public void queSiNoEstaEnMaceracionElProgresoSeaCero() {
    Coleccion coleccion = new Coleccion();
    coleccion.setPerfume(new Perfume());
    coleccion.setEnMaceracion(false);

    ItemColeccion item = ItemColeccion.desde(coleccion, false);

    assertThat(item.isEnMaceracion(), is(false));
    assertThat(item.getPorcentajeMaceracion(), is(0));
    assertThat(item.isListaParaUsar(), is(false));
  }

  @Test
  public void queALosQuinceDiasElProgresoSeaLaMitad() {
    Coleccion coleccion = new Coleccion();
    coleccion.setPerfume(new Perfume());
    coleccion.setEnMaceracion(true);
    coleccion.setFechaInicioMaceracion(LocalDate.now().minusDays(15));

    ItemColeccion item = ItemColeccion.desde(coleccion, false);

    assertThat(item.getDiasTranscurridos(), is(15L));
    assertThat(item.getPorcentajeMaceracion(), is(50));
    assertThat(item.isListaParaUsar(), is(false));
  }

  @Test
  public void queLuegoDeLosTreintaDiasQuedeListaParaUsarYElProgresoSeTopeeEnCien() {
    Coleccion coleccion = new Coleccion();
    coleccion.setPerfume(new Perfume());
    coleccion.setEnMaceracion(true);
    coleccion.setFechaInicioMaceracion(LocalDate.now().minusDays(45));

    ItemColeccion item = ItemColeccion.desde(coleccion, true);

    assertThat(item.getPorcentajeMaceracion(), is(100));
    assertThat(item.isListaParaUsar(), is(true));
    assertThat(item.isFavorito(), is(true));
    assertThat(item.getDiasRestantes(), is(0L));
  }
}
