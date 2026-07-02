package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Favorito;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioFavorito;
import com.tallerwebi.dominio.ServicioFavorito;
import com.tallerwebi.dominio.Usuario;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioFavorito")
@Transactional
public class ServicioFavoritoImpl implements ServicioFavorito {

  private RepositorioFavorito repositorioFavorito;

  @Autowired
  public ServicioFavoritoImpl(RepositorioFavorito repositorioFavorito) {
    this.repositorioFavorito = repositorioFavorito;
  }

  @Override
  public void agregarAFavoritos(Long idUsuario, Long idPerfume) {
    if (idUsuario == null || idPerfume == null) return;

    // Evitamos duplicados: si ya está en favoritos, no hacemos nada
    if (repositorioFavorito.existe(idUsuario, idPerfume)) return;

    Perfume perfumeEncontrado = repositorioFavorito.buscarPerfume(idPerfume);
    if (perfumeEncontrado == null) return;

    Favorito nuevoFavorito = new Favorito();
    nuevoFavorito.setPerfume(perfumeEncontrado);

    Usuario dueno = new Usuario();
    dueno.setId(idUsuario);
    nuevoFavorito.setUsuario(dueno);

    nuevoFavorito.setFechaAgregado(LocalDate.now());
    repositorioFavorito.guardarFavorito(nuevoFavorito);
  }

  @Override
  public List<Perfume> listar(Long idUsuario) {
    return repositorioFavorito.listar(idUsuario);
  }

  @Override
  public void eliminarFavorito(Long idUsuario, Long idPerfume) {
    repositorioFavorito.eliminar(idUsuario, idPerfume);
  }

  @Override
  public boolean esFavorito(Long idUsuario, Long idPerfume) {
    if (idUsuario == null || idPerfume == null) return false;
    return repositorioFavorito.existe(idUsuario, idPerfume);
  }
}
