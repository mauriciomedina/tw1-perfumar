package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPerfume {
  Perfume consultarPerfume(String nombre);
  void grabar(Perfume perfume) throws Exception;
  List<Perfume> listar();
}
