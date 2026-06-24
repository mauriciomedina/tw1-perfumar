package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.dominio.Usuario;
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
  public void guardarEnColeccion(Long idUsuario, Long idPerfume) {
    if (idUsuario == null || idPerfume == null) return;

    // Validamos que no esté ya agregado usando el listar del repositorio
    List<Perfume> miColeccion = this.listar(idUsuario);
    for (Perfume p : miColeccion) {
      if (p.getId().equals(idPerfume)) return;
    }

    // Buscamos el perfume maestro
    Perfume perfumeEncontrado = repositorioColeccion.buscarPerfume(idPerfume);

    // Creamos la relación
    Coleccion nuevaColeccion = new Coleccion();
    nuevaColeccion.setPerfume(perfumeEncontrado);

    Usuario dueno = new Usuario();
    dueno.setId(idUsuario);
    nuevaColeccion.setUsuario(dueno);

    nuevaColeccion.setEnMaceracion(false);
    repositorioColeccion.guardarColeccion(nuevaColeccion);
  }

  @Override
  public List<Perfume> listar(Long idUsuario) {
    return repositorioColeccion.listar(idUsuario);
  }

  @Override
  public List<Perfume> listar() {
    return repositorioColeccion.listar(); // El que ya tenías
  }

  @Override
  public void eliminarPerfume(Long idUsuario, Long idPerfume) {
    repositorioColeccion.eliminar(idUsuario, idPerfume);
  }
}
