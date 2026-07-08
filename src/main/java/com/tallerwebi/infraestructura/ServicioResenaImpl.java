package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioResena;
import com.tallerwebi.dominio.Resena;
import com.tallerwebi.dominio.ServicioResena;
import com.tallerwebi.dominio.Usuario;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioResena")
@Transactional
public class ServicioResenaImpl implements ServicioResena {

  private static final int PUNTUACION_MINIMA = 1;
  private static final int PUNTUACION_MAXIMA = 5;
  private static final int PUNTUACION_POR_DEFECTO = 5;

  private RepositorioResena repositorioResena;

  @Autowired
  public ServicioResenaImpl(RepositorioResena repositorioResena) {
    this.repositorioResena = repositorioResena;
  }

  @Override
  public void agregarResena(Long idUsuario, Long idPerfume, String comentario, Integer puntuacion) {
    if (idUsuario == null || idPerfume == null) return;
    if (comentario == null || comentario.trim().isEmpty()) return;

    Perfume perfumeEncontrado = repositorioResena.buscarPerfume(idPerfume);
    if (perfumeEncontrado == null) return;

    Resena nuevaResena = new Resena();
    nuevaResena.setPerfume(perfumeEncontrado);
    nuevaResena.setComentario(comentario.trim());
    nuevaResena.setPuntuacion(normalizarPuntuacion(puntuacion));
    nuevaResena.setFecha(LocalDate.now());

    Usuario autor = new Usuario();
    autor.setId(idUsuario);
    nuevaResena.setUsuario(autor);

    repositorioResena.guardar(nuevaResena);
  }

  private Integer normalizarPuntuacion(Integer puntuacion) {
    if (puntuacion == null) return PUNTUACION_POR_DEFECTO;
    if (puntuacion < PUNTUACION_MINIMA) return PUNTUACION_MINIMA;
    if (puntuacion > PUNTUACION_MAXIMA) return PUNTUACION_MAXIMA;
    return puntuacion;
  }

  @Override
  public List<Resena> listarPorPerfume(Long idPerfume) {
    return repositorioResena.listarPorPerfume(idPerfume);
  }

  @Override
  public Double promedioDePuntuacion(Long idPerfume) {
    return repositorioResena.promedioDePuntuacion(idPerfume);
  }
}
