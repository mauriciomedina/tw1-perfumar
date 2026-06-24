package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosPerfume;
import java.util.List;

public interface ServicioColeccion {
  void guardarEnColeccion(Long idUsuario, Long idPerfume);
  List<Perfume> listar(); // Para traer todos (catálogo)
  List<Perfume> listar(Long idUsuario); // Para filtrar los de la colección
  void guardar(DatosPerfume datosPerfume);
  void eliminarPerfume(Long idUsuario, Long idPerfume);
}
