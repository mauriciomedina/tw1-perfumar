package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioColeccion {
  void guardarPerfume(Perfume perfume);

  void guardarColeccion(Coleccion coleccion);
  List<Perfume> listar();

  Perfume buscarPerfume(Long id);
}
