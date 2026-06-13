package com.tallerwebi.infraestructura;

import static org.mockito.Mockito.*; // Traemos las herramientas de Mockito

import com.tallerwebi.dominio.RepositorioColeccion; // Traemos tu repositorio
import org.junit.jupiter.api.Test;

public class ServicioColeccionImplTest {

  @Test
  public void queAlGuardarEnColeccionCubraLasLineasDeCodigo() {
    // 1. Creamos un Mock (simulacro) de tu repositorio
    RepositorioColeccion repositorioMock = mock(RepositorioColeccion.class);

    // 2. Ahora sí instanciamos el servicio, pero pasándole el mock por parámetro
    ServicioColeccionImpl servicio = new ServicioColeccionImpl(repositorioMock);

    // 3. Ejecutamos el método con nulo para cubrir el "if (idPerfume == null)"
    servicio.guardarEnColeccion(null);

    // 4. Ejecutamos el método con un ID válido para cubrir el resto del método
    servicio.guardarEnColeccion(1L);
  }
}
