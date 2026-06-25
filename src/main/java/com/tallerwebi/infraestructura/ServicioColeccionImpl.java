package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Coleccion;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.dominio.ServicioColeccion;
import com.tallerwebi.dominio.Usuario;
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
}
