package com.tallerwebi.presentacion;

public class DatosPerfume {

  private String nombre;
  private String marca;
  private Long idFamilia;
  private Boolean enMaceracion;
  private String fechaInicio;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public Long getIdFamilia() {
    return idFamilia;
  }

  public void setIdFamilia(Long idFamilia) {
    this.idFamilia = idFamilia;
  }

  public Boolean getEnMaceracion() {
    return enMaceracion;
  }

  public void setEnMaceracion(Boolean enMaceracion) {
    this.enMaceracion = enMaceracion;
  }

  public String getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(String fechaInicio) {
    this.fechaInicio = fechaInicio;
  }
}
