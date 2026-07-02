package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioFavorito {
  Perfume buscarPerfume(Long id);
  void guardarFavorito(Favorito favorito);
  List<Perfume> listar(Long idUsuario);
  void eliminar(Long idUsuario, Long idPerfume);
  boolean existe(Long idUsuario, Long idPerfume);
}
