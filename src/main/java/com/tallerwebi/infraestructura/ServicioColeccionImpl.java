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

  //borrar
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

    // Manejamos el switch de maceración: si viene null , lo forzamos a false
    Boolean estaEnMaceracion = (datosPerfume.getEnMaceracion() != null)
      ? datosPerfume.getEnMaceracion()
      : false;
    itemColeccion.setEnMaceracion(estaEnMaceracion);

    // 3. Validación de la fecha
    if (
      estaEnMaceracion &&
      datosPerfume.getFechaInicio() != null &&
      !datosPerfume.getFechaInicio().trim().isEmpty()
    ) {
      itemColeccion.setFechaInicioMaceracion(LocalDate.parse(datosPerfume.getFechaInicio()));
    }

    repositorioColeccion.guardarColeccion(itemColeccion);
  }

  @Override
  @Transactional
  public void guardarEnColeccion(Long idPerfume) {
    if (idPerfume == null) {
      return;
    }

    // Si lo tengo en la coleccion trae la lista actual de perfumes que ya agregaste
    List<Perfume> miColeccionActual = this.listar();

    // Recorremos la lista para ver si el ID ya existe adentro
    for (Perfume perfumeGuardado : miColeccionActual) {
      if (perfumeGuardado.getId().equals(idPerfume)) {
        return;
      }
    }

    // 1. Busca el perfume
    Perfume perfumeEncontrado = repositorioColeccion.buscarPerfume(idPerfume);

    // 2. arma la colección con el perfume que seguro existe
    Coleccion nuevaColeccion = new Coleccion();
    nuevaColeccion.setPerfume(perfumeEncontrado);
    nuevaColeccion.setEnMaceracion(false);

    // 3. Guarda la relación
    repositorioColeccion.guardarColeccion(nuevaColeccion);
  }

  @Override
  public List<Perfume> listar() {
    return repositorioColeccion.listar();
  }
}
