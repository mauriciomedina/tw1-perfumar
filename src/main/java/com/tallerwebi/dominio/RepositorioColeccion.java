package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioColeccion {
  void guardarPerfume(Perfume perfume);
  void guardarColeccion(Coleccion coleccion);
  List<Perfume> listar(); // Para traer todos (catálogo)
  List<Perfume> listar(Long idUsuario); // Para filtrar los de la colección
  Perfume buscarPerfume(Long id);
  void eliminar(Long idUsuario, Long idPerfume);
  // Para poder mostrar el detalle de maceración de cada ítem de la colección
  List<Coleccion> listarEntidades(Long idUsuario);
  Coleccion buscarColeccion(Long idUsuario, Long idPerfume);
}
