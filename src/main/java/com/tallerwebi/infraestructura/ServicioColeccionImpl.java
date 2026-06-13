package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.presentacion.DatosPerfume;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioColeccion")
@Transactional
public class ServicioColeccionImpl implements ServicioColeccion {

  private RepositorioColeccion repositorioColeccion;

  @Autowired
  public ServicioColeccionImpl(RepositorioColeccion repositorioColeccion) {
    this.repositorioColeccion = repositorioColeccion;
  }

  @Override
  public void guardar(DatosPerfume datosPerfume) {
    // 1. Instanciamos el perfume maestro
    Perfume perfume = new Perfume();
    perfume.setNombre(datosPerfume.getNombre());
    perfume.setMarca(datosPerfume.getMarca());

    // Guardamos el perfume en el catálogo general
    repositorioColeccion.guardarPerfume(perfume);

    // 2. Instanciamos el ítem de la colección del usuario
    Coleccion itemColeccion = new Coleccion();
    itemColeccion.setPerfume(perfume);

    // Manejamos el switch de maceración: si viene null (apagado), lo forzamos a false
    Boolean estaEnMaceracion = (datosPerfume.getEnMaceracion() != null)
      ? datosPerfume.getEnMaceracion()
      : false;
    itemColeccion.setEnMaceracion(estaEnMaceracion);

    // 3. Validación de la fecha
    // Solo intentamos parsear si está en maceración, si la fecha no es nula, y si el String NO está vacío.
    if (
      estaEnMaceracion &&
      datosPerfume.getFechaInicio() != null &&
      !datosPerfume.getFechaInicio().trim().isEmpty()
    ) {
      itemColeccion.setFechaInicioMaceracion(LocalDate.parse(datosPerfume.getFechaInicio()));
    }

    // Guardamos el registro en la tabla intermedia de inventario
    repositorioColeccion.guardarColeccion(itemColeccion);
  }

  @Override
  public void guardarEnColeccion(Long idPerfume) {
    if (idPerfume == null) {
      return; // Validación simple para usar la variable y que PMD no salte
    }
    // TODO: Implementar la lógica con el repositorio más adelante
  }

  @Override
  public List<Perfume> listar() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listar'");
  }
}
