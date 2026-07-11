package com.tallerwebi.dominio;

public enum Marca {
  ARMAF("Armaf"),
  CALVIN_KLEIN("Calvin Klein"),
  CAROLINA_HERRERA("Carolina Herrera"),
  CHANEL("Chanel"),
  CREED("Creed"),
  DIOR("Dior"),
  DOLCE_Y_GABBANA("Dolce & Gabbana"),
  GIORGIO_ARMANI("Giorgio Armani"),
  GIVENCHY("Givenchy"),
  GUCCI("Gucci"),
  HERMES("Hermès"),
  HUGO_BOSS("Hugo Boss"),
  JEAN_PAUL_GAULTIER("Jean Paul Gaultier"),
  LANCOME("Lancôme"),
  MAISON_FRANCIS_KURKDJIAN("Maison Francis Kurkdjian"),
  NARCISO_RODRIGUEZ("Narciso Rodriguez"),
  PACO_RABANNE("Paco Rabanne"),
  PRADA("Prada"),
  RALPH_LAUREN("Ralph Lauren"),
  THIERRY_MUGLER("Thierry Mugler"),
  TOM_FORD("Tom Ford"),
  VALENTINO("Valentino"),
  VERSACE("Versace"),
  VIKTOR_Y_ROLF("Viktor & Rolf"),
  YVES_SAINT_LAURENT("Yves Saint Laurent");

  private final String nombreVisible;

  Marca(String nombreVisible) {
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
