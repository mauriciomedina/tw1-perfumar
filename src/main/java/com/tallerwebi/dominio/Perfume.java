package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Perfume {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String marca;
  private String urlAfiliado;

  @JoinColumn(name = "familia_id")
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
