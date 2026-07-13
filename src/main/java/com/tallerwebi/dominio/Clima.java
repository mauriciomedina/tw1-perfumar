package com.tallerwebi.dominio;

public class Clima {

  private String ciudad;
  private Double temperatura;
  private Double sensacionTermica;
  private Integer humedad;
  private Double velocidadViento;
  private String condicion;
  private String descripcion;
  private Integer nubosidad;

  public Clima() {}

  public Clima(
    String ciudad,
    Double temperatura,
    Double sensacionTermica,
    Integer humedad,
    Double velocidadViento,
    String condicion,
    String descripcion,
    Integer nubosidad
  ) {
    this.ciudad = ciudad;
    this.temperatura = temperatura;
    this.sensacionTermica = sensacionTermica;
    this.humedad = humedad;
    this.velocidadViento = velocidadViento;
    this.condicion = condicion;
    this.descripcion = descripcion;
    this.nubosidad = nubosidad;
  }

  public String getCiudad() {
    return ciudad;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public Double getTemperatura() {
    return temperatura;
  }

  public void setTemperatura(Double temperatura) {
    this.temperatura = temperatura;
  }

  public Double getSensacionTermica() {
    return sensacionTermica;
  }

  public void setSensacionTermica(Double sensacionTermica) {
    this.sensacionTermica = sensacionTermica;
  }

  public Integer getHumedad() {
    return humedad;
  }

  public void setHumedad(Integer humedad) {
    this.humedad = humedad;
  }

  public Double getVelocidadViento() {
    return velocidadViento;
  }

  public void setVelocidadViento(Double velocidadViento) {
    this.velocidadViento = velocidadViento;
  }

  public String getCondicion() {
    return condicion;
  }

  public void setCondicion(String condicion) {
    this.condicion = condicion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Integer getNubosidad() {
    return nubosidad;
  }

  public String getTextoHeader() {
    return ciudad + ", " + Math.round(temperatura) + "°C";
  }

  public void setNubosidad(Integer nubosidad) {
    this.nubosidad = nubosidad;
  }
}
