package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

public class LocalTest {

  @Test
  public void testGettersYSetters() {
    Local local = new Local();

    local.setId(1L);
    local.setNombre("Perfumería Rouge");
    local.setDireccion("Avenida de Mayo 100");
    local.setLatitud(-34.6400);
    local.setLongitud(-58.5650);

    assertThat(local.getId(), equalTo(1L));
    assertThat(local.getNombre(), equalTo("Perfumería Rouge"));
    assertThat(local.getDireccion(), equalTo("Avenida de Mayo 100"));
    assertThat(local.getLatitud(), equalTo(-34.6400));
    assertThat(local.getLongitud(), equalTo(-58.5650));
  }
}
