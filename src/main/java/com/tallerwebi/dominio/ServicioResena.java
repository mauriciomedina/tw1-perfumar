package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioResena {
  void agregarResena(Long idUsuario, Long idPerfume, String comentario, Integer puntuacion);
  List<Resena> listarPorPerfume(Long idPerfume);
  Double promedioDePuntuacion(Long idPerfume);
}
