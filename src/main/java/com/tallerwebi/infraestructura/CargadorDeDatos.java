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
  private static final String DIOR = "Dior";
  private static final String CHANEL = "Chanel";
  private static final String ARMANI = "Giorgio Armani";
  private static final String VERSACE = "Versace";
  private static final String HERRERA = "Carolina Herrera";

  @Autowired
  public CargadorDeDatos(RepositorioColeccion repositorioColeccion) {
    this.repositorioColeccion = repositorioColeccion;
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (repositorioColeccion.listar().isEmpty()) {
      crearPerfume("Sauvage", DIOR, "/spring/img/sauvage.png");
      crearPerfume("Fahrenheit", DIOR, "/spring/img/fahrenheit.png");
      crearPerfume("Bleu de Chanel", CHANEL, "/spring/img/bleu-chanel.png");
      crearPerfume("Acqua di Giò", ARMANI, "/spring/img/acqua-gio.png");
      crearPerfume("1 Million", "Paco Rabanne", "/spring/img/one-million.png");
      crearPerfume("Eros", VERSACE, "/spring/img/eros.jpg");
      crearPerfume("Good Girl", HERRERA, "/spring/img/good-girl.png");
      crearPerfume("Le Male", "Jean Paul Gaultier", "/spring/img/le-male.jpg");
      crearPerfume("Invictus", "Paco Rabanne", "/spring/img/invictus.jpg");
      crearPerfume("Black Orchid", "Tom Ford", "/spring/img/black-orchid.jpg");
      crearPerfume("Aventus", "Creed", "/spring/img/aventus.jpg");
      crearPerfume("Y Eau de Parfum", "Yves Saint Laurent", "/spring/img/ysl-y.jpg");
      crearPerfume("Armani Code", ARMANI, "/spring/img/armani-code.jpg");
      crearPerfume("La Nuit de L'Homme", "Yves Saint Laurent", "/spring/img/la-nuit.jpg");
      crearPerfume("The One For Men", "Dolce & Gabbana", "/spring/img/the-one.jpg");
      crearPerfume("Light Blue", "Dolce & Gabbana", "/spring/img/light-blue.jpg");
      crearPerfume("Bad Boy", HERRERA, "/spring/img/bad-boy.jfif");
      crearPerfume("Ultra Male", "Jean Paul Gaultier", "/spring/img/ultra-male.jpg");
      crearPerfume("Dylan Blue", VERSACE, "/spring/img/dylan-blue.jpg");
      crearPerfume("Oud Wood", "Tom Ford", "/spring/img/oud-wood.jpg");
      crearPerfume("Coco Mademoiselle", CHANEL, "/spring/img/coco-mademoiselle.jpg");
      crearPerfume("La Vie Est Belle", "Lancôme", "/spring/img/la-vie-est-belle.jpg");
      crearPerfume("Libre", "Yves Saint Laurent", "/spring/img/libre.jpg");
      crearPerfume("Flowerbomb", "Viktor & Rolf", "/spring/img/flowerbomb.jpg");
      crearPerfume("Chance Eau Tendre", CHANEL, "/spring/img/chance-eau-tendre.jpg");
      crearPerfume("Dior Homme Intense", DIOR, "/spring/img/dior-homme-intense.jpg");
      crearPerfume("Stronger With You", ARMANI, "/spring/img/stronger-with-you.jpg");
      crearPerfume("Polo Blue", "Ralph Lauren", "/spring/img/polo-blue.jpg");
      crearPerfume("Eternity For Men", "Calvin Klein", "/spring/img/eternity-men.jpg");
      crearPerfume("CK One", "Calvin Klein", "/spring/img/ck-one.jpg");
      crearPerfume("Olympéa", "Paco Rabanne", "/spring/img/olympea.jpg");
      crearPerfume("Boss Bottled", "Hugo Boss", "/spring/img/boss-bottled.jpg");
      crearPerfume("Acqua di Giò Profondo", ARMANI, "/spring/img/acqua-gio-profondo.jpg");
      crearPerfume("Prada L'Homme", "Prada", "/spring/img/prada-lhomme.jpg");
      crearPerfume("Miss Dior", DIOR, "/spring/img/miss-dior.jpg");
      crearPerfume("J'adore", DIOR, "/spring/img/jadore.jpg");
      crearPerfume("Narciso For Her", "Narciso Rodriguez", "/spring/img/narciso-for-her.jpg");
      crearPerfume("Si", ARMANI, "/spring/img/armani-si.jpg");
      crearPerfume("Dolce", "Dolce & Gabbana", "/spring/img/dg-dolce.jpg");
      crearPerfume("Idôle", "Lancôme", "/spring/img/lancome-idole.jpg");
      crearPerfume("Guilty Pour Femme", "Gucci", "/spring/img/gucci-guilty.jpg");
      crearPerfume("Bloom", "Gucci", "/spring/img/gucci-bloom.jpg");
      crearPerfume("Angel", "Thierry Mugler", "/spring/img/angel-mugler.jpg");
      crearPerfume("Alien", "Thierry Mugler", "/spring/img/alien-mugler.jpg");
      crearPerfume("Scandal", "Jean Paul Gaultier", "/spring/img/scandal-jpgaultier.jpg");
      crearPerfume("Bright Crystal", VERSACE, "/spring/img/bright-crystal.jpg");
      crearPerfume("Terre d'Hermès", "Hermès", "/spring/img/terre-hermes.jpg");
      crearPerfume("Voyage d'Hermès", "Hermès", "/spring/img/voyage-hermes.jpg");
      crearPerfume("Chanel N°5", CHANEL, "/spring/img/chanel-n5.jpg");
      crearPerfume("L'Interdit", "Givenchy", "/spring/img/linterdit-givenchy.jpg");
      crearPerfume("Gentleman Eau de Parfum", "Givenchy", "/spring/img/gentleman-givenchy.jpg");
      crearPerfume("Born in Roma", "Valentino", "/spring/img/born-in-roma.jpg");
      crearPerfume("Valentino Uomo", "Valentino", "/spring/img/valentino-uomo.jpg");
      crearPerfume("Club de Nuit Intense Man", "Armaf", "/spring/img/club-de-nuit.jpg");
      crearPerfume(
        "Baccarat Rouge 540",
        "Maison Francis Kurkdjian",
        "/spring/img/baccarat-rouge.jpg"
      );
      crearPerfume("Sì Passione", ARMANI, "/spring/img/si-passione.jpg");
      crearPerfume("CH Men", HERRERA, "/spring/img/ch-men.jpg");
      crearPerfume("Crystal Noir", VERSACE, "/spring/img/crystal-noir.jpg");
      crearPerfume("212 VIP", HERRERA, "/spring/img/212-vip.jpg");
      crearPerfume("Hypnôse", "Lancôme", "/spring/img/hypnose-lancome.jpg");
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
