package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.FamiliaOlfativa;
import com.tallerwebi.dominio.Marca;
import com.tallerwebi.dominio.Perfume;
import com.tallerwebi.dominio.RepositorioColeccion;
import com.tallerwebi.dominio.RepositorioResena;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Resena;
import com.tallerwebi.dominio.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CargadorDeDatos implements ApplicationListener<ContextRefreshedEvent> {

  private static final String[][] RESENADORES = {
    { "Martina Gómez", "martina.gomez@perfumar-demo.com" },
    { "Lucas Fernández", "lucas.fernandez@perfumar-demo.com" },
    { "Valentina Ríos", "valentina.rios@perfumar-demo.com" },
  };

  private static final String[] PLANTILLAS_RESENA = {
    "Compré %s hace poco y ya se convirtió en mi fragancia favorita, dura muchísimo en la piel.",
    "La proyección de %s es excelente, recibí varios cumplidos apenas me la puse.",
    "Me encantó %s, es ideal para el uso diario.",
    "%s tiene una salida increíble, aunque se suaviza un poco rápido igual lo recomiendo.",
    "Un clásico. %s nunca falla para una salida de noche.",
    "Esperaba un poco más de %s, la duración no es la mejor pero el aroma es agradable.",
    "Excelente relación calidad-precio, %s es de los que más uso.",
    "El frasco de %s es hermoso y el aroma dura todo el día.",
    "Regalé %s y fue un éxito total, quedaron todos encantados.",
    "Para mi gusto %s es un poco fuerte al principio, pero se asienta muy bien después de un rato.",
  };

  // Alineado índice a índice con PLANTILLAS_RESENA para que el puntaje sea coherente con el comentario.
  private static final int[] PUNTUACIONES = { 5, 5, 5, 4, 5, 3, 4, 5, 5, 4 };

  private final RepositorioColeccion repositorioColeccion;
  private final RepositorioResena repositorioResena;
  private final RepositorioUsuario repositorioUsuario;
  private final List<Perfume> perfumesCreados = new ArrayList<>();
  private List<Usuario> resenadores;

  @Autowired
  public CargadorDeDatos(
    RepositorioColeccion repositorioColeccion,
    RepositorioResena repositorioResena,
    RepositorioUsuario repositorioUsuario
  ) {
    this.repositorioColeccion = repositorioColeccion;
    this.repositorioResena = repositorioResena;
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (repositorioColeccion.listar().isEmpty()) {
      crearPerfume(
        "Sauvage",
        Marca.DIOR,
        "/spring/img/sauvage.png",
        FamiliaOlfativa.HELECHO,
        "Una composición rotundamente fresca, dictada por un nombre que suena como un manifiesto.",
        "Bergamota de Calabria, Pimienta de Sichuan, Ambroxan"
      );
      crearPerfume(
        "Acqua di Giò",
        Marca.GIORGIO_ARMANI,
        "/spring/img/acqua-gio.png",
        FamiliaOlfativa.CITRICA,
        "Fragancia mítica, fresca y acuática que evoca la brisa del mar Mediterráneo.",
        "Notas Marinas, Bergamota, Jazmín, Caqui"
      );
      crearPerfume(
        "1 Million",
        Marca.PACO_RABANNE,
        "/spring/img/one-million.png",
        FamiliaOlfativa.ORIENTAL,
        "El perfume del éxito. Una fragancia atrevida con notas de cuero y oro.",
        "Mandarina sangrienta, Canela, Cuero, Ámbar"
      );
      crearPerfume(
        "Eros",
        Marca.VERSACE,
        "/spring/img/eros.jpg",
        FamiliaOlfativa.HELECHO,
        "Fragancia para un hombre fuerte, apasionado y dueño de sí mismo, inspirada en la mitología griega.",
        "Menta, Manzana Verde, Tonka, Vainilla"
      );
      crearPerfume(
        "Good Girl",
        Marca.CAROLINA_HERRERA,
        "/spring/img/good-girl.png",
        FamiliaOlfativa.ORIENTAL,
        "Una fragancia audaz y sofisticada, inspirada en la visión de la dualidad de la mujer moderna.",
        "Almendra, Café, Nardo, Habá Tonka, Cacao"
      );
      crearPerfume(
        "Le Male",
        Marca.JEAN_PAUL_GAULTIER,
        "/spring/img/le-male.jpg",
        FamiliaOlfativa.ORIENTAL,
        "Tan viril como sexy, rinde homenaje a la figura mítica que siempre ha inspirado al diseñador: el marinero.",
        "Lavanda, Menta, Canela, Vainilla"
      );
      crearPerfume(
        "Invictus",
        Marca.PACO_RABANNE,
        "/spring/img/invictus.jpg",
        FamiliaOlfativa.AMADERADA,
        "El aroma de la victoria, la esencia de los héroes. Una descarga de frescura extrema y calidez amaderada.",
        "Toronja, Notas Marinas, Hoja de Laurel, Madera de Guayaco"
      );
      crearPerfume(
        "Black Orchid",
        Marca.TOM_FORD,
        "/spring/img/black-orchid.jpg",
        FamiliaOlfativa.ORIENTAL,
        "Una fragancia lujosa y sensual de notas oscuras con una atractiva poción de orquídeas negras y especias.",
        "Trufa Negra, Orquídea Negra, Parchelí, Ciruela"
      );
      crearPerfume(
        "Aventus",
        Marca.CREED,
        "/spring/img/aventus.jpg",
        FamiliaOlfativa.FRUTAL,
        "Una fragancia excepcional que celebra la fuerza, el poder y el éxito, inspirada en la vida de un emperador.",
        "Piña, Bergamota, Abedul, Almizcle"
      );
      crearPerfume(
        "Y Eau de Parfum",
        Marca.YVES_SAINT_LAURENT,
        "/spring/img/ysl-y.jpg",
        FamiliaOlfativa.HELECHO,
        "Una interpretación intensa de la icónica camiseta blanca y la chaqueta negra de YSL.",
        "Manzana, Jengibre, Salvia, Madera de Ámbar"
      );
      crearPerfume(
        "Armani Code",
        Marca.GIORGIO_ARMANI,
        "/spring/img/armani-code.jpg",
        FamiliaOlfativa.ORIENTAL,
        "Una fragancia sofisticada y seductora para un hombre carismático.",
        "Limón, Flor de Olivo, Habá Tonka, Cuero"
      );
      crearPerfume(
        "La Nuit de L'Homme",
        Marca.YVES_SAINT_LAURENT,
        "/spring/img/la-nuit.jpg",
        FamiliaOlfativa.ORIENTAL,
        "Una historia de seducción, intensidad y audaz sensualidad en una noche urbana.",
        "Cardamomo, Bergamota, Cedro, Alcaravea"
      );
      crearPerfume(
        "The One For Men",
        Marca.DOLCE_Y_GABBANA,
        "/spring/img/the-one.jpg",
        FamiliaOlfativa.ORIENTAL,
        "Una fragancia elegante, sensual, decididamente moderna pero también un clásico único.",
        "Cilantro, Albahaca, Jengibre, Tabaco"
      );
      crearPerfume(
        "Light Blue",
        Marca.DOLCE_Y_GABBANA,
        "/spring/img/light-blue.png",
        FamiliaOlfativa.CITRICA,
        "La alegría de vivir la vida idílica del mar Mediterráneo plasmada en una esencia fresca.",
        "Limón de Sicilia, Manzana, Bambú, Almizcle"
      );
      crearPerfume(
        "Bad Boy",
        Marca.CAROLINA_HERRERA,
        "/spring/img/bad-boy.jfif",
        FamiliaOlfativa.ORIENTAL,
        "Una expresión fragante de la dualidad del hombre contemporáneo, audaz y sensible.",
        "Pimienta Blanca, Pimienta Negra, Bergamota, Cacao"
      );
      crearPerfume(
        "Ultra Male",
        Marca.JEAN_PAUL_GAULTIER,
        "/spring/img/ultra-male.jpg",
        FamiliaOlfativa.ORIENTAL,
        "El marinero pícaro con el que se va a pique el corazón. Una batalla olfativa entre poder y gourmandise.",
        "Pera, Menta, Canela, Vainilla Negra"
      );
      crearPerfume(
        "Dylan Blue",
        Marca.VERSACE,
        "/spring/img/dylan-blue.jpg",
        FamiliaOlfativa.HELECHO,
        "Una fragancia intensamente masculina que encapsula los aromas sensuales del Mediterráneo.",
        "Notas Acuáticas, Bergamota, Hojas de Higuera, Papiro"
      );
      crearPerfume(
        "Oud Wood",
        Marca.TOM_FORD,
        "/spring/img/oud-wood.jpg",
        FamiliaOlfativa.AMADERADA,
        "Una de las maderas más raras, preciosas y caras del arsenal de un perfumista, a menudo quemada en los templos.",
        "Madera de Oud, Palo de Rosa, Cardamomo, Sándalo"
      );

      crearPerfume(
        "Narciso For Her",
        Marca.NARCISO_RODRIGUEZ,
        "/spring/img/narciso-for-her.png",
        FamiliaOlfativa.AMADERADA,
        "Un homenaje a la sensualidad femenina a través del almizcle puro y la madera envolvente.",
        "Rosa, Almizcle, Cedro, Ámbar, Pachulí"
      );
      crearPerfume(
        "Si",
        Marca.GIORGIO_ARMANI,
        "/spring/img/armani-si.png",
        FamiliaOlfativa.ORIENTAL,
        "Un perfume que dice sí a la vida, sensual y moderno, inspirado en la mujer contemporánea.",
        "Grosella Negra, Néctar de Rosa, Vainilla, Pachulí, Almizcle"
      );
      crearPerfume(
        "Idôle",
        Marca.LANCOME,
        "/spring/img/lancome-idole.png",
        FamiliaOlfativa.AMADERADA,
        "Una fragancia para las mujeres que inspiran al mundo: limpia, poderosa y llena de esperanza.",
        "Rosa, Jazmín, Musgo, Madera de Cedro, Almizcle"
      );
      crearPerfume(
        "Guilty Pour Femme",
        Marca.GUCCI,
        "/spring/img/gucci-guilty.png",
        FamiliaOlfativa.ORIENTAL,
        "Una fragancia audaz y seductora que celebra la libertad de seguir los propios deseos.",
        "Mandarina, Rosa, Durazno, Pachulí, Ámbar"
      );
      crearPerfume(
        "Bloom",
        Marca.GUCCI,
        "/spring/img/gucci-bloom.png",
        FamiliaOlfativa.FRUTAL,
        "Un jardín blanco en plena floración: una fragancia rica, intensa y femenina.",
        "Tuberosa, Jazmín, Ranúnculo, Gardenia, Almizcle"
      );
      crearPerfume(
        "Angel",
        Marca.THIERRY_MUGLER,
        "/spring/img/angel-mugler.png",
        FamiliaOlfativa.ORIENTAL,
        "Un perfume mítico y revolucionario, el primero en introducir el concepto gourmand en la perfumería.",
        "Frutos Rojos, Miel, Chocolate, Pachulí, Vainilla"
      );
      crearPerfume(
        "Alien",
        Marca.THIERRY_MUGLER,
        "/spring/img/alien-mugler.png",
        FamiliaOlfativa.AMADERADA,
        "Una fragancia de otro mundo: mineral, luminosa y magnética, para una mujer única.",
        "Jazmín Sambac, Madera de Cachemira, Ámbar Blanco"
      );
      crearPerfume(
        "Scandal",
        Marca.JEAN_PAUL_GAULTIER,
        "/spring/img/scandal-jpgaultier.png",
        FamiliaOlfativa.ORIENTAL,
        "Una fragancia provocadora y glamorosa inspirada en el lujo dorado y la noche parisina.",
        "Naranja, Gardenia, Miel, Pachulí, Vetiver"
      );
      crearPerfume(
        "Bright Crystal",
        Marca.VERSACE,
        "/spring/img/bright-crystal.png",
        FamiliaOlfativa.FRUTAL,
        "Una mezcla refrescante y sensual de reflejos cristalinos y flores frescas y elegantes.",
        "Granada, Yuzu, Peonía, Magnolia, Almizcle"
      );
      crearPerfume(
        "Terre d'Hermès",
        Marca.HERMES,
        "/spring/img/terre-hermes.png",
        FamiliaOlfativa.AMADERADA,
        "Un diálogo entre la tierra y el cielo, entre lo mineral y lo vegetal. Un perfume de raíces profundas.",
        "Naranja, Pomelo, Pimienta, Vetiver, Cedro"
      );
      crearPerfume(
        "Voyage d'Hermès",
        Marca.HERMES,
        "/spring/img/voyage-hermes.png",
        FamiliaOlfativa.AMADERADA,
        "Una invitación al viaje interior, fresca y profunda, para el hombre que lleva su libertad consigo.",
        "Cardamomo, Vetiver, Sándalo, Cedro, Madera"
      );
      crearPerfume(
        "Chanel N°5",
        Marca.CHANEL,
        "/spring/img/chanel-n5.png",
        FamiliaOlfativa.AMADERADA,
        "El perfume más famoso del mundo: intemporal, sofisticado y sinónimo de feminidad absoluta.",
        "Aldehídos, Neroli, Ylang Ylang, Jazmín, Sándalo"
      );
      crearPerfume(
        "L'Interdit",
        Marca.GIVENCHY,
        "/spring/img/linterdit-givenchy.png",
        FamiliaOlfativa.AMADERADA,
        "Lo prohibido tiene su propio perfume: oscuro, floral y seductor, para una mujer que desafía las reglas.",
        "Naranja Amarga, Jazmín, Tuberosa, Cedro, Vainilla"
      );
      crearPerfume(
        "Gentleman Eau de Parfum",
        Marca.GIVENCHY,
        "/spring/img/gentleman-givenchy.png",
        FamiliaOlfativa.HELECHO,
        "La dualidad del hombre moderno: la suavidad del iris y la fuerza del cuero en perfecta armonía.",
        "Bergamota, Iris, Pachulí, Cuero, Vainilla"
      );
      crearPerfume(
        "Born in Roma",
        Marca.VALENTINO,
        "/spring/img/born-in-roma.png",
        FamiliaOlfativa.HELECHO,
        "Nacido en la ciudad eterna: un perfume que fusiona la tradición romana con la energía urbana.",
        "Café, Cardamomo, Habá Tonka, Vetiver, Cedro"
      );
      crearPerfume(
        "Valentino Uomo",
        Marca.VALENTINO,
        "/spring/img/valentino-uomo.png",
        FamiliaOlfativa.AMADERADA,
        "La esencia del hombre Valentino: elegante, sensual y con el refinamiento propio de la alta costura italiana.",
        "Bergamota, Neroli, Iris, Cuero, Cedro Virginia"
      );
      crearPerfume(
        "Club de Nuit Intense Man",
        Marca.ARMAF,
        "/spring/img/club-de-nuit.png",
        FamiliaOlfativa.FRUTAL,
        "Una fragancia intensa y magnética que evoca las noches de lujo y la energía de los grandes eventos.",
        "Piña, Abedul, Jazmín, Pachulí, Almizcle"
      );
      crearPerfume(
        "Baccarat Rouge 540",
        Marca.MAISON_FRANCIS_KURKDJIAN,
        "/spring/img/baccarat-rouge.png",
        FamiliaOlfativa.AMADERADA,
        "Una fragancia celestial nacida del encuentro entre el cristal y el fuego, cálida y etérea a la vez.",
        "Azafrán, Jazmín, Ambroxan, Cedro, Eritroxilona"
      );
      crearPerfume(
        "Sì Passione",
        Marca.GIORGIO_ARMANI,
        "/spring/img/si-passione.png",
        FamiliaOlfativa.ORIENTAL,
        "La intensidad de la pasión convertida en perfume: más oscura, más intensa y más seductora.",
        "Pera, Rosa Negra, Cedro, Pachulí, Vainilla"
      );
      crearPerfume(
        "CH Men",
        Marca.CAROLINA_HERRERA,
        "/spring/img/ch-men.png",
        FamiliaOlfativa.AMADERADA,
        "La sofisticación del hombre contemporáneo capturada en un frasco icónico de cristal biselado.",
        "Bergamota, Lavanda, Cedro, Ámbar, Almizcle"
      );
      crearPerfume(
        "Crystal Noir",
        Marca.VERSACE,
        "/spring/img/crystal-noir.png",
        FamiliaOlfativa.ORIENTAL,
        "La feminidad misteriosa y oscura de Versace: sensual, especiada y de seductora profundidad.",
        "Pimienta, Nuez Moscada, Jengibre, Gardenia, Coco"
      );
      crearPerfume(
        "212 VIP",
        Marca.CAROLINA_HERRERA,
        "/spring/img/212-vip.png",
        FamiliaOlfativa.FRUTAL,
        "El aroma de la ciudad que nunca duerme: fresco, moderno y con el ritmo de Manhattan.",
        "Gardenia, Almizcle Blanco, Azahar, Cachemira"
      );
      crearPerfume(
        "Hypnôse",
        Marca.LANCOME,
        "/spring/img/hypnose-lancome.png",
        FamiliaOlfativa.ORIENTAL,
        "Una fragancia hipnótica y envolvente que despierta los sentidos con su calor y suavidad.",
        "Castaña de Agua, Gardenia, Heliotropo, Praliné, Vainilla"
      );
    }
    /*de aca */
    crearPerfume(
      "Coco Mademoiselle",
      Marca.CHANEL,
      "/spring/img/coco-mademoiselle.png",
      FamiliaOlfativa.ORIENTAL,
      "Una fragancia fresca y atrevida para una mujer libre, decidida y llena de gracia moderna.",
      "Naranja, Bergamota, Rosa, Pachulí, Almizcle Blanco"
    );

    crearPerfume(
      "Bleu de Chanel",
      Marca.CHANEL,
      "/spring/img/bleu-chanel.png",
      FamiliaOlfativa.AMADERADA,
      "Elogio de la libertad masculina en un perfume amaderado aromático de estela cautivadora.",
      "Toronja, Menta, Incienso, Jengibre"
    );
    crearPerfume(
      "Fahrenheit",
      Marca.DIOR,
      "/spring/img/fahrenheit.png",
      FamiliaOlfativa.AMADERADA,
      "Un perfume caracterizado por un encuentro de extremos, con notas florales y de cuero.",
      "Violeta, Cuero, Mandarina, Cedro"
    );
    crearPerfume(
      "La Vie Est Belle",
      Marca.LANCOME,
      "/spring/img/la-vie-est-belle.png",
      FamiliaOlfativa.ORIENTAL,
      "Una declaración de libertad y felicidad: la vida es bella y hay que elegir el propio camino.",
      "Grosella Negra, Pera, Jazmín, Naranja, Praliné, Vainilla"
    );
    crearPerfume(
      "Libre",
      Marca.YVES_SAINT_LAURENT,
      "/spring/img/libre.png",
      FamiliaOlfativa.AMADERADA,
      "Una fragancia de contrastes sensuales, donde la lavanda francesa se encuentra con el jazmín marroquí.",
      "Lavanda, Naranja, Jazmín Sambac, Cedro, Almizcle"
    );
    crearPerfume(
      "Flowerbomb",
      Marca.VIKTOR_Y_ROLF,
      "/spring/img/flowerbomb.png",
      FamiliaOlfativa.ORIENTAL,
      "Una explosión floral que transforma el mundo en algo más positivo y hermoso.",
      "Bergamota, Jazmín, Rosa Centifolia, Freesia, Pachulí"
    );
    crearPerfume(
      "Chance Eau Tendre",
      Marca.CHANEL,
      "/spring/img/chance-eau-tendre.png",
      FamiliaOlfativa.CITRICA,
      "Una fragancia floral fresca y luminosa que captura la suavidad del azar y la ternura.",
      "Pomelo, Jacinto, Jazmín, Iris, Cedro"
    );
    crearPerfume(
      "Dior Homme Intense",
      Marca.DIOR,
      "/spring/img/dior-homme-intense.png",
      FamiliaOlfativa.AMADERADA,
      "Una reinterpretación profunda e intensa del icónico Dior Homme, dominada por el iris y la madera.",
      "Iris, Lavanda, Pera, Cedro, Vainilla"
    );
    crearPerfume(
      "Stronger With You",
      Marca.GIORGIO_ARMANI,
      "/spring/img/stronger-with-you.png",
      FamiliaOlfativa.HELECHO,
      "Una fragancia moderna y cálida para un hombre que se vuelve más fuerte con cada experiencia vivida.",
      "Cardamomo, Salvia, Castaña, Vainilla, Madera"
    );
    crearPerfume(
      "Polo Blue",
      Marca.RALPH_LAUREN,
      "/spring/img/polo-blue.png",
      FamiliaOlfativa.HELECHO,
      "Una fragancia fresca e inspirada en el cielo abierto, el agua y la libertad del espíritu aventurero.",
      "Melón, Pepino, Salvia, Suede, Almizcle"
    );

    crearPerfume(
      "CK One",
      Marca.CALVIN_KLEIN,
      "/spring/img/ck-one.png",
      FamiliaOlfativa.CITRICA,
      "Un revolucionario perfume unisex que marcó una generación con su frescura limpia y desenfadada.",
      "Bergamota, Limón, Piña, Jazmín, Sándalo"
    );

    crearPerfume(
      "Acqua di Giò Profondo",
      Marca.GIORGIO_ARMANI,
      "/spring/img/acqua-gio-profondo.png",
      FamiliaOlfativa.HELECHO,
      "Una inmersión en las profundidades del mar: más oscuro, más misterioso e intenso que su antecesor.",
      "Menta, Pimienta de Madagascar, Salvia Marina, Algas"
    );
    crearPerfume(
      "Prada L'Homme",
      Marca.PRADA,
      "/spring/img/prada-lhomme.png",
      FamiliaOlfativa.HELECHO,
      "Una composición pulida y refinada que define la masculinidad intelectual y contemporánea.",
      "Iris, Semilla de Ambrette, Cedro, Pachulí, Musgo"
    );
    crearPerfume(
      "Miss Dior",
      Marca.DIOR,
      "/spring/img/miss-dior.jpg",
      FamiliaOlfativa.FRUTAL,
      "Un ramo de amor para la mujer actual: fresca, romántica y llena de vida.",
      "Fresa, Rosa de Grasse, Lily of the Valley, Pachulí, Almizcle"
    );
    crearPerfume(
      "J'adore",
      Marca.DIOR,
      "/spring/img/jadore.jpg",
      FamiliaOlfativa.FRUTAL,
      "Una oda a la feminidad absoluta, luminosa y floral, sinónimo de belleza y elegancia.",
      "Ylang Ylang, Rosa de Damasco, Jazmín, Sándalo"
    );
    /*seguir aca */

    sembrarResenas();
  }

  private void crearPerfume(
    String nombre,
    Marca marca,
    String imagenUrl,
    FamiliaOlfativa familiaOlfativa,
    String descripcion,
    String notas
  ) {
    Perfume perfume = new Perfume();
    perfume.setNombre(nombre);
    perfume.setMarca(marca);
    perfume.setImagenUrl(imagenUrl);
    perfume.setFamiliaOlfativa(familiaOlfativa);
    perfume.setDescripcion(descripcion);
    perfume.setNotas(notas);

    this.repositorioColeccion.guardarPerfume(perfume);
    this.perfumesCreados.add(perfume);
  }

  private void sembrarResenas() {
    this.resenadores = obtenerOCrearResenadores();

    for (int i = 0; i < perfumesCreados.size(); i++) {
      sembrarResenasDePerfume(perfumesCreados.get(i), i);
    }
  }

  private void sembrarResenasDePerfume(Perfume perfume, int indice) {
    if (!repositorioResena.listarPorPerfume(perfume.getId()).isEmpty()) {
      return;
    }

    crearResena(
      resenadores.get(indice % resenadores.size()),
      perfume,
      String.format(PLANTILLAS_RESENA[indice % PLANTILLAS_RESENA.length], perfume.getNombre()),
      PUNTUACIONES[indice % PUNTUACIONES.length],
      LocalDate.now().minusDays((indice * 3 + 5) % 60)
    );

    int segundaPlantilla = (indice + 4) % PLANTILLAS_RESENA.length;
    crearResena(
      resenadores.get((indice + 1) % resenadores.size()),
      perfume,
      String.format(PLANTILLAS_RESENA[segundaPlantilla], perfume.getNombre()),
      PUNTUACIONES[segundaPlantilla],
      LocalDate.now().minusDays((indice * 5 + 11) % 45)
    );
  }

  private List<Usuario> obtenerOCrearResenadores() {
    List<Usuario> resenadores = new ArrayList<>();
    for (String[] datos : RESENADORES) {
      Usuario existente = repositorioUsuario.buscar(datos[1]);
      if (existente != null) {
        resenadores.add(existente);
        continue;
      }

      Usuario nuevo = new Usuario();
      nuevo.setNombre(datos[0]);
      nuevo.setEmail(datos[1]);
      nuevo.setRol("USUARIO");
      nuevo.setActivo(true);
      repositorioUsuario.guardar(nuevo);
      resenadores.add(nuevo);
    }
    return resenadores;
  }

  private void crearResena(
    Usuario usuario,
    Perfume perfume,
    String comentario,
    Integer puntuacion,
    LocalDate fecha
  ) {
    Resena resena = new Resena();
    resena.setUsuario(usuario);
    resena.setPerfume(perfume);
    resena.setComentario(comentario);
    resena.setPuntuacion(puntuacion);
    resena.setFecha(fecha);
    repositorioResena.guardar(resena);
  }
}
