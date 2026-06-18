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
    // 1. Mockea el Repositorio
    repositorioMock = mock(RepositorioColeccion.class);
    // 2. Le pasam el mock al Servicio real
    servicioColeccion = new ServicioColeccionImpl(repositorioMock);
  }


  @Test
  public void queSePuedaGuardarUnPerfumeConMaceracionYFecha() {

    DatosPerfume datos = new DatosPerfume();
    datos.setNombre("Bleecker Street");
    datos.setMarca("Bond No. 9");
    datos.setEnMaceracion(true);
    datos.setFechaInicio("2024-10-25");


    servicioColeccion.guardar(datos);

    verify(repositorioMock, times(1)).guardarPerfume(any(Perfume.class));
    verify(repositorioMock, times(1)).guardarColeccion(any(Coleccion.class));
  }


  @Test
  public void queAlGuardarUnPerfumeSinMaceracionSeManejenLosNulosCorrectamente() {

    DatosPerfume datos = new DatosPerfume();
    datos.setNombre("Aventus");
    datos.setMarca("Creed");
    datos.setEnMaceracion(null); 
    datos.setFechaInicio(""); 


    servicioColeccion.guardar(datos);


    verify(repositorioMock, times(1)).guardarPerfume(any(Perfume.class));
    verify(repositorioMock, times(1)).guardarColeccion(any(Coleccion.class));
  }
}
