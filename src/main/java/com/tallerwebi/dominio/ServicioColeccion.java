package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosPerfume;
import java.util.List;

public interface ServicioColeccion {
  void guardar(DatosPerfume datosPerfume);
  List<Perfume> listar();
}
