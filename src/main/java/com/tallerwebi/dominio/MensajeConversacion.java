package com.tallerwebi.dominio;

public class MensajeConversacion {

  private String rol;
  private String texto;

  public MensajeConversacion() {}

  public MensajeConversacion(String rol, String texto) {
    this.rol = rol;
    this.texto = texto;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }
}
