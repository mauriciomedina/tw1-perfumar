package com.tallerwebi.dominio;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioLocal")
@Transactional
public class ServicioLocalImpl implements ServicioLocal {

  private RepositorioLocal repositorioLocal;

  @Autowired
  public ServicioLocalImpl(RepositorioLocal repositorioLocal) {
    this.repositorioLocal = repositorioLocal;
  }

  @Override
  public List<Local> obtenerLocalesMasCercanos(
    Double latUsuario,
    Double lonUsuario,
    Integer cantidadDeLocales
  ) {
    //  Va a la base de datos y trae los locales que existen
    List<Local> todosLosLocales = repositorioLocal.listarTodos();

    // 2. Ordena la lista completa.
    // Compara la distancia de cada local contra la ubicación del usuario.
    // Los que tengan menor distancia (los más cercanos) van a quedar primeros en la lista.
    todosLosLocales.sort(
      Comparator.comparingDouble(local ->
        calcularDistancia(latUsuario, lonUsuario, local.getLatitud(), local.getLongitud())
      )
    );

    return todosLosLocales.stream().limit(cantidadDeLocales).collect(Collectors.toList());
  }

  private Double calcularDistancia(
    Double latUsuario,
    Double lonUsuario,
    Double latLocal,
    Double lonLocal
  ) {
    final int RADIO_TIERRA = 6371;

    Double latDistancia = Math.toRadians(latLocal - latUsuario);
    Double lonDistancia = Math.toRadians(lonLocal - lonUsuario);

    Double factorA =
      Math.sin(latDistancia / 2) * Math.sin(latDistancia / 2) +
      Math.cos(Math.toRadians(latUsuario)) *
        Math.cos(Math.toRadians(latLocal)) *
        Math.sin(lonDistancia / 2) *
        Math.sin(lonDistancia / 2);

    Double factorC = 2 * Math.atan2(Math.sqrt(factorA), Math.sqrt(1 - factorA));

    return RADIO_TIERRA * factorC;
  }
}
