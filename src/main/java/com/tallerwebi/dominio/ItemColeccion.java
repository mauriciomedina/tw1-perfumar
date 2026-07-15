package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * DTO de presentación para la vista "Mi Colección".
 * Combina el perfume guardado en la colección de un usuario con:
 * - si ese mismo perfume está también marcado como favorito
 * - el estado de maceración (fecha de inicio, días transcurridos y progreso)
 */
public class ItemColeccion {

  // Cantidad de días que se recomienda dejar macerar un perfume antes de usarlo.
  public static final int DIAS_RECOMENDADOS_MACERACION = 30;

  private Long idColeccion;
  private Perfume perfume;
  private boolean favorito;
  private boolean enMaceracion;
  private LocalDate fechaInicioMaceracion;
  private long diasTranscurridos;
  private int porcentajeMaceracion;
  private boolean listaParaUsar;

  public static ItemColeccion desde(Coleccion coleccion, boolean favorito) {
    ItemColeccion item = new ItemColeccion();
    item.idColeccion = coleccion.getId();
    item.perfume = coleccion.getPerfume();
    item.favorito = favorito;
    item.enMaceracion = Boolean.TRUE.equals(coleccion.getEnMaceracion());
    item.fechaInicioMaceracion = coleccion.getFechaInicioMaceracion();

    if (item.enMaceracion && item.fechaInicioMaceracion != null) {
      long dias = ChronoUnit.DAYS.between(item.fechaInicioMaceracion, LocalDate.now());
      item.diasTranscurridos = Math.max(0, dias);
      item.porcentajeMaceracion =
        (int) Math.min(100, (item.diasTranscurridos * 100) / DIAS_RECOMENDADOS_MACERACION);
      item.listaParaUsar = item.diasTranscurridos >= DIAS_RECOMENDADOS_MACERACION;
    } else {
      item.diasTranscurridos = 0;
      item.porcentajeMaceracion = 0;
      item.listaParaUsar = false;
    }

    return item;
  }

  public Long getIdColeccion() {
    return idColeccion;
  }

  public Perfume getPerfume() {
    return perfume;
  }

  public boolean isFavorito() {
    return favorito;
  }

  public boolean isEnMaceracion() {
    return enMaceracion;
  }

  public LocalDate getFechaInicioMaceracion() {
    return fechaInicioMaceracion;
  }

  public String getFechaInicioMaceracionFormateada() {
    if (fechaInicioMaceracion == null) return "";
    return fechaInicioMaceracion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

  public long getDiasTranscurridos() {
    return diasTranscurridos;
  }

  public int getPorcentajeMaceracion() {
    return porcentajeMaceracion;
  }

  public boolean isListaParaUsar() {
    return listaParaUsar;
  }

  public int getDiasRecomendados() {
    return DIAS_RECOMENDADOS_MACERACION;
  }

  public long getDiasRestantes() {
    return Math.max(0, DIAS_RECOMENDADOS_MACERACION - diasTranscurridos);
  }
}
