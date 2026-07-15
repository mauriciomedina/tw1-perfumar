package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface ServicioColeccion {
  void guardarEnColeccion(Long idUsuario, Long idPerfume);
  List<Perfume> listar(); // Para traer todos (catálogo)
  List<Perfume> listar(Long idUsuario); // Para filtrar los de la colección
  void eliminarPerfume(Long idUsuario, Long idPerfume);
  Perfume buscarPerfume(Long id);

  // Devuelve la colección del usuario con el detalle de favorito y maceración de cada perfume
  List<ItemColeccion> listarConDetalle(Long idUsuario);

  // Inicia (o reinicia) la maceración de un perfume de la colección con una fecha elegida por el usuario
  void iniciarMaceracion(Long idUsuario, Long idPerfume, LocalDate fechaInicio);

  // Cancela la maceración en curso de un perfume (vuelve a "sin macerar")
  void cancelarMaceracion(Long idUsuario, Long idPerfume);
}
