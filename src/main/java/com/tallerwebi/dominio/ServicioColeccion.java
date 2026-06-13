package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosPerfume;
import java.util.List;

public interface ServicioColeccion {
  void guardarEnColeccion(Long idPerfume);
  List<Perfume> listar();
  void guardar(DatosPerfume datosPerfume);
}
