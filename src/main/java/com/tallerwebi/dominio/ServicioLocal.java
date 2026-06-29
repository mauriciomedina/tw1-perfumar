package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioLocal {
  List<Local> obtenerLocalesMasCercanos(
    Double latUsuario,
    Double lonUsuario,
    Integer cantidadDeLocales
  );
}
