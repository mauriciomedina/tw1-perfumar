package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioResena {
  Perfume buscarPerfume(Long id);
  void guardar(Resena resena);
  List<Resena> listarPorPerfume(Long idPerfume);
  Double promedioDePuntuacion(Long idPerfume);
}
