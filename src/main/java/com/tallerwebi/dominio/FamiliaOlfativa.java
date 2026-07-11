package com.tallerwebi.dominio;

public enum FamiliaOlfativa {
  AMADERADA("Amaderada"),
  CITRICA("Cítrica"),
  FRUTAL("Frutal"),
  HELECHO("Helecho"),
  ORIENTAL("Oriental");

  private final String nombreVisible;

  FamiliaOlfativa(String nombreVisible) {
    this.nombreVisible = nombreVisible;
  }

  public String getNombreVisible() {
    return nombreVisible;
  }

  @Override
  public String toString() {
    return nombreVisible;
  }
}
