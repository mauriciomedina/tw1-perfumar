package com.tallerwebi.presentacion;

public class PerfumeCatalogoDTO {

  private Long id;
  private String nombre;
  private String marca;
  private String familiaOlfativa;
  private String descripcion;
  private String notas;
  private String imagenUrl;

  public PerfumeCatalogoDTO() {}

  public PerfumeCatalogoDTO(
    Long id,
    String nombre,
    String marca,
    String familiaOlfativa,
    String descripcion,
    String notas,
    String imagenUrl
  ) {
    this.id = id;
    this.nombre = nombre;
    this.marca = marca;
    this.familiaOlfativa = familiaOlfativa;
    this.descripcion = descripcion;
    this.notas = notas;
    this.imagenUrl = imagenUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public String getFamiliaOlfativa() {
    return familiaOlfativa;
  }

  public void setFamiliaOlfativa(String familiaOlfativa) {
    this.familiaOlfativa = familiaOlfativa;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getNotas() {
    return notas;
  }

  public void setNotas(String notas) {
    this.notas = notas;
  }

  public String getImagenUrl() {
    return imagenUrl;
  }

  public void setImagenUrl(String imagenUrl) {
    this.imagenUrl = imagenUrl;
  }
}
