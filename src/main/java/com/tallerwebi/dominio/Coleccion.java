package com.tallerwebi.dominio;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Coleccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Usuario usuario;

  @ManyToOne
  private Perfume perfume;

  private Boolean enMaceracion;
  private LocalDate fechaInicioMaceracion;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Perfume getPerfume() {
    return perfume;
  }

  public void setPerfume(Perfume perfume) {
    this.perfume = perfume;
  }

  public Boolean getEnMaceracion() {
    return enMaceracion;
  }

  public void setEnMaceracion(Boolean enMaceracion) {
    this.enMaceracion = enMaceracion;
  }

  public LocalDate getFechaInicioMaceracion() {
    return fechaInicioMaceracion;
  }

  public void setFechaInicioMaceracion(LocalDate fechaInicioMaceracion) {
    this.fechaInicioMaceracion = fechaInicioMaceracion;
  }
}
