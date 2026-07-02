package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioFavorito {
  void agregarAFavoritos(Long idUsuario, Long idPerfume);
  List<Perfume> listar(Long idUsuario);
  void eliminarFavorito(Long idUsuario, Long idPerfume);
  boolean esFavorito(Long idUsuario, Long idPerfume);
}
