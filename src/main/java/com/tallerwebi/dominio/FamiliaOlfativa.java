package com.tallerwebi.dominio;

public enum FamiliaOlfativa {
  AMADERADA("Amaderada", "70%"),
  CITRICA("Cítrica", "20%"),
  FRUTAL("Frutal", "35%"),
  HELECHO("Helecho", "50%"),
  ORIENTAL("Oriental", "85%");

  private final String nombreVisible;
  private final String intensidad;

  FamiliaOlfativa(String nombreVisible, String intensidad) {
    this.nombreVisible = nombreVisible;
    this.intensidad = intensidad;
  }

  public String getNombreVisible() {
    return nombreVisible;
  }

  public String getIntensidad() {
    return intensidad;
  }

  @Override
  public String toString() {
    return nombreVisible;
  }
}
