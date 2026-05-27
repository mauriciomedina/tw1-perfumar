package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Perfume {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String marca;
  private String urlAfiliado;

  @ManyToOne
  private FamiliaOlfativa familia;

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

  public String getUrlAfiliado() {
    return urlAfiliado;
  }

  public void setUrlAfiliado(String urlAfiliado) {
    this.urlAfiliado = urlAfiliado;
  }

  public FamiliaOlfativa getFamilia() {
    return familia;
  }

  public void setFamilia(FamiliaOlfativa familia) {
    this.familia = familia;
  }
}
