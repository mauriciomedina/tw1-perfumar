package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPerfume {
  Perfume buscar(String nombre);
  void guardar(Perfume perfume);
  void modificar(Perfume perfume);
  List<Perfume> listar();
}
