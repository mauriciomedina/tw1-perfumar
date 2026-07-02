package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.MensajeConversacion;
import java.util.List;

public class GeminiDto {

  private String pregunta;
  private String respuesta;
  private String reglaAdicional;
  private String contextoActual;
  private List<MensajeConversacion> historial;

  public String getPregunta() {
    return pregunta;
  }

  public void setPregunta(String pregunta) {
    this.pregunta = pregunta;
  }

  public String getRespuesta() {
    return respuesta;
  }

  public void setRespuesta(String respuesta) {
    this.respuesta = respuesta;
  }

  public String getReglaAdicional() {
    return reglaAdicional;
  }

  public void setReglaAdicional(String reglaAdicional) {
    this.reglaAdicional = reglaAdicional;
  }

  public String getContextoActual() {
    return contextoActual;
  }

  public void setContextoActual(String contextoActual) {
    this.contextoActual = contextoActual;
  }

  public List<MensajeConversacion> getHistorial() {
    return historial;
  }

  public void setHistorial(List<MensajeConversacion> historial) {
    this.historial = historial;
  }
}
