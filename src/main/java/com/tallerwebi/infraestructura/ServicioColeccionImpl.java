package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.ItemColeccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.dominio.ServicioFavorito;
import com.tallerwebi.dominio.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioColeccion")
@Transactional
public class ServicioColeccionImpl implements ServicioColeccion {

  private RepositorioColeccion repositorioColeccion;
  private ServicioFavorito servicioFavorito;

  @Autowired
  public ServicioColeccionImpl(
    RepositorioColeccion repositorioColeccion,
    ServicioFavorito servicioFavorito
  ) {
    this.repositorioColeccion = repositorioColeccion;
    this.servicioFavorito = servicioFavorito;
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

  @Override
  public Perfume buscarPerfume(Long id) {
    return repositorioColeccion.buscarPerfume(id);
  }

  @Override
  public List<ItemColeccion> listarConDetalle(Long idUsuario) {
    List<ItemColeccion> items = new ArrayList<>();
    if (idUsuario == null) return items;

    List<Coleccion> colecciones = repositorioColeccion.listarEntidades(idUsuario);
    for (Coleccion coleccion : colecciones) {
      boolean esFavorito =
        coleccion.getPerfume() != null &&
        servicioFavorito.esFavorito(idUsuario, coleccion.getPerfume().getId());
      items.add(ItemColeccion.desde(coleccion, esFavorito));
    }
    return items;
  }

  @Override
  public void iniciarMaceracion(Long idUsuario, Long idPerfume, LocalDate fechaInicio) {
    if (idUsuario == null || idPerfume == null) return;

    Coleccion coleccion = repositorioColeccion.buscarColeccion(idUsuario, idPerfume);
    if (coleccion == null) return;

    coleccion.setEnMaceracion(true);
    coleccion.setFechaInicioMaceracion(fechaInicio != null ? fechaInicio : LocalDate.now());
  }

  @Override
  public void cancelarMaceracion(Long idUsuario, Long idPerfume) {
    if (idUsuario == null || idPerfume == null) return;

    Coleccion coleccion = repositorioColeccion.buscarColeccion(idUsuario, idPerfume);
    if (coleccion == null) return;

    coleccion.setEnMaceracion(false);
    coleccion.setFechaInicioMaceracion(null);
  }
}
