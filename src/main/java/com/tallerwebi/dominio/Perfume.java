package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Perfume {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String imagenUrl;

  @Enumerated(EnumType.STRING)
  private FamiliaOlfativa familiaOlfativa;

  @Enumerated(EnumType.STRING)
  private Marca marca;

  @Column(length = 1000)
  private String descripcion;

  @Column(length = 500)
  private String notas;

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

  public Marca getMarca() {
    return marca;
  }

  public void setMarca(Marca marca) {
    this.marca = marca;
  }

  public String getImagenUrl() {
    return imagenUrl;
  }

  public void setImagenUrl(String imagenUrl) {
    this.imagenUrl = imagenUrl;
  }

  public FamiliaOlfativa getFamiliaOlfativa() {
    return familiaOlfativa;
  }

  public void setFamiliaOlfativa(FamiliaOlfativa familiaOlfativa) {
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
}
