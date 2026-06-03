package com.tallerwebi.infraestructura;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.presentacion.DatosPerfume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioColeccionTest {

  private ServicioColeccionImpl servicioColeccion;
  private RepositorioColeccion repositorioMock;

  @BeforeEach
  public void init() {
    // 1. Mockeamos el Repositorio
    repositorioMock = mock(RepositorioColeccion.class);
    // 2. Le pasamos el mock al Servicio real
    servicioColeccion = new ServicioColeccionImpl(repositorioMock);
  }

  // --- TEST 1: El camino feliz completo ---
  @Test
  public void queSePuedaGuardarUnPerfumeConMaceracionYFecha() {
    // Preparación: Armamos la libreta con todos los datos llenos
    DatosPerfume datos = new DatosPerfume();
    datos.setNombre("Bleecker Street");
    datos.setMarca("Bond No. 9");
    datos.setEnMaceracion(true);
    datos.setFechaInicio("2024-10-25");

    // Ejecución: El servicio cocina los datos
    servicioColeccion.guardar(datos);

    // Validación: Verificamos que el Servicio le haya entregado al Repositorio las Entidades
    verify(repositorioMock, times(1)).guardarPerfume(any(Perfume.class));
    verify(repositorioMock, times(1)).guardarColeccion(any(Coleccion.class));
  }

  // --- TEST 2: El switch apagado y fecha vacía
  @Test
  public void queAlGuardarUnPerfumeSinMaceracionSeManejenLosNulosCorrectamente() {
    // Preparación: Simulamos lo que manda el HTML cuando el switch está apagado
    DatosPerfume datos = new DatosPerfume();
    datos.setNombre("Aventus");
    datos.setMarca("Creed");
    datos.setEnMaceracion(null); // El HTML manda null cuando no se tilda
    datos.setFechaInicio(""); // El HTML manda texto vacío en la fecha

    // Ejecución
    servicioColeccion.guardar(datos);

    // Validación: A pesar de los nulos, el proceso no debe explotar y debe guardar igual
    verify(repositorioMock, times(1)).guardarPerfume(any(Perfume.class));
    verify(repositorioMock, times(1)).guardarColeccion(any(Coleccion.class));
  }
}
