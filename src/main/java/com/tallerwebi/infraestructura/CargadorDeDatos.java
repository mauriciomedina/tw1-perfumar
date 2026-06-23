package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CargadorDeDatos implements ApplicationListener<ContextRefreshedEvent> {

  private RepositorioColeccion repositorioColeccion;

  @Autowired
  public CargadorDeDatos(RepositorioColeccion repositorioColeccion) {
    this.repositorioColeccion = repositorioColeccion;
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // Verifica si el catálogo ya está cargado para no duplicar datos
    if (repositorioColeccion.listar().isEmpty()) {
      crearPerfume("Sauvage", "Dior", "/spring/img/sauvage.png");
      crearPerfume("Fahrenheit", "Dior", "/spring/img/fahrenheit.png");
      crearPerfume("Bleu de Chanel", "Chanel", "/spring/img/bleu-chanel.png");
      crearPerfume("Acqua di Giò", "Giorgio Armani", "/spring/img/acqua-gio.png");
      crearPerfume("1 Million", "Paco Rabanne", "/spring/img/one-million.png");
      crearPerfume("Eros", "Versace", "/spring/img/eros.jpg");
      crearPerfume("Good Girl", "Carolina Herrera", "/spring/img/good-girl.png");
      crearPerfume("Le Male", "Jean Paul Gaultier", "/spring/img/le-male.jpg");
      crearPerfume("Invictus", "Paco Rabanne", "/spring/img/invictus.jpg");
      crearPerfume("Black Orchid", "Tom Ford", "/spring/img/black-orchid.jpg");
      crearPerfume("Aventus", "Creed", "/spring/img/aventus.jpg");
      crearPerfume("Y Eau de Parfum", "Yves Saint Laurent", "/spring/img/ysl-y.jpg");
      crearPerfume("Armani Code", "Giorgio Armani", "/spring/img/armani-code.jpg");
      crearPerfume("La Nuit de L'Homme", "Yves Saint Laurent", "/spring/img/la-nuit.jpg");
      crearPerfume("The One For Men", "Dolce & Gabbana", "/spring/img/the-one.jpg");
      crearPerfume("Light Blue", "Dolce & Gabbana", "/spring/img/light-blue.jpg");
      crearPerfume("Bad Boy", "Carolina Herrera", "/spring/img/bad-boy.jfif");
      crearPerfume("Ultra Male", "Jean Paul Gaultier", "/spring/img/ultra-male.jpg");
      crearPerfume("Dylan Blue", "Versace", "/spring/img/dylan-blue.jpg");
      crearPerfume("Oud Wood", "Tom Ford", "/spring/img/oud-wood.jpg");
    }
  }

  private void crearPerfume(String nombre, String marca, String imagenUrl) {
    Perfume perfume = new Perfume();
    perfume.setNombre(nombre);
    perfume.setMarca(marca);
    perfume.setImagenUrl(imagenUrl);

    this.repositorioColeccion.guardarPerfume(perfume);
  }
}
