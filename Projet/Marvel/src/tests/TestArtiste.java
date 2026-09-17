package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import location.Artiste;
import location.Film;
import location.GestionArtiste;
import org.junit.jupiter.api.Test;



/**
 * Classe de tests unitaires pour Artiste et GestionArtiste.
 */
public class TestArtiste {

  /**
   * Test du constructeur de la classe Artiste.
   */
  @Test
  public void testConstructeurArtiste() {
    Artiste artiste = new Artiste("Spielberg", "Steven", "Américaine");
    assertEquals("Spielberg", artiste.getNom(), "Erreur : Le nom"
        + " n'est pas correctement initialisé.");
    assertEquals("Steven", artiste.getPrenom(), "Erreur : Le prénom "
        + "n'est pas correctement initialisé.");
    assertEquals("Américaine", artiste.getNationalite(), "Erreur :"
        + " La nationalité n'est pas correctement initialisée.");
   
  }

  /**
   * Tests de la méthode ajouterFilm dans la classe Artiste.
   */
  @Test
  public void testAjouterFilm() {
    Artiste artiste = new Artiste("Spielberg", "Steven", "Américaine");
    Film film = new Film("Jurassic Park", 1993, 12, artiste);
    assertTrue(artiste.ajouterFilm(film), "Erreur : Le film n'a pas été ajouté à l'ensemble.");
    assertFalse(artiste.ajouterFilm(film), "Erreur : "
        + "Le même film ne devrait pas être ajouté deux fois.");
   
  }

  /**
   * Test de la méthode equals dans la classe Artiste.
   */
  @Test
  public void testEqualsArtiste() {
    Artiste artiste1 = new Artiste("Nolan", "Christopher", "Britannique");
    Artiste artiste2 = new Artiste("Nolan", "Christopher", "Britannique");
    Artiste artiste3 = new Artiste("Spielberg", "Steven", "Américaine");

    assertEquals(artiste1, artiste2, "Erreur : Les artistes identiques devraient être égaux.");
    assertNotEquals(artiste1, artiste3, "Erreur :"
        + " Les artistes différents ne devraient pas être égaux.");
   
  }

  /**
   * Test de la méthode creerArtiste dans GestionArtiste.
   */
  @Test
  public void testCreerArtiste() {
    GestionArtiste gestion = new GestionArtiste();
    
    Artiste artiste = gestion.creerArtiste("Nolan", "Christopher", "Britannique");
  
    assertNotNull(gestion.getArtiste(artiste.getNom(), artiste.getPrenom()), 
        "Erreur : L'artiste devrait être ajouté à l'ensemble.");
    
  }

  /**
   * Test de la méthode supprimerArtiste dans GestionArtiste.
   */
  @Test
  public void testSupprimerArtiste() {
    GestionArtiste gestion = new GestionArtiste();
    Artiste artiste = gestion.creerArtiste("Spielberg", "Steven", "Américaine");
    gestion.creerArtiste("Nolan", "Christopher", "Britannique");

    assertTrue(gestion.supprimerArtiste(artiste), "Erreur : L'artiste devrait être supprimé.");
    assertFalse(gestion.supprimerArtiste(artiste),
        "Erreur : Un artiste déjà supprimé ne peut pas être supprimé à nouveau.");

  }

  /**
   * Test de la méthode ensembleFilmsActeur dans GestionArtiste.
   */
  @Test
  public void testEnsembleFilmsActeur() {
    GestionArtiste gestion = new GestionArtiste();
    Artiste acteur = gestion.creerArtiste("Depp", "Johnny", "Américaine");
    gestion.ensembleActeur().add(acteur);

    Film film = new Film("Pirates des Caraïbes", 2003, 12, acteur);
    acteur.ajouterFilm(film);

    assertNotNull(gestion.ensembleFilmsActeur(acteur),
        "Erreur : L'ensemble des films de l'acteur ne devrait pas être null.");
    assertEquals(1, gestion.ensembleFilmsActeur(acteur).size(),
        "Erreur : L'acteur devrait avoir un film dans son ensemble.");

  }

  /**
   * Test de la méthode getRealisateur dans GestionArtiste.
   */
  @Test
  public void testGetRealisateur() {
    GestionArtiste gestion = new GestionArtiste();
    Artiste realisateur = gestion.creerArtiste("Tarantino", "Quentin", "Américaine");
    gestion.ensembleRealisateur().add(realisateur);

    assertNotNull(gestion.getRealisateur("Tarantino", "Quentin"),
        "Erreur : Le réalisateur devrait être trouvé.");
    assertNull(gestion.getRealisateur("Scorsese", "Martin"),
        "Erreur : Un réalisateur inexistant ne devrait pas être trouvé.");
   
  }
}
