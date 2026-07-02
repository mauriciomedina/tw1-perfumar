package com.tallerwebi.dominio;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface ServicioGemini {
  String preguntar(
    String mensajeUsuario,
    String reglaAdicional,
    boolean persistir,
    List<MensajeConversacion> historial
  ) throws JsonProcessingException;
  void configurar(String systemInstructions);
  void setSystemInstruction(String instruction);
  void appendSystemInstruction(String instruction);
  String getSystemInstructions();
  void limpiarContexto();
}
