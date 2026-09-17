package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import location.Artiste;
import location.Evaluation;
import location.Film;
import location.InformationPersonnelle;
import location.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link location.Film
 * Film}.
 *
 * @author Andias Ames
 * @see location.Film
 */

class TestFilm {
  /**
   * Un réalisateur .
   */
  private Artiste realisateur1;


  /**
   * Un film  : Titre, date de sortie, age limite et réalisteur.
   */
  private Film film1;
  
  /**
   * Une evaluation. 
   */
  private Evaluation eval1;
  
  /**
   * Une deuxieme evaluation. 
   */
  private Evaluation eval2;
  
  /**
   * Un utilisateur. 
   */
  private Utilisateur utilisateur1;

  /**
   * Une exception.
   *
   * @throws Exception ne peut pas être levée ici.
   */
  @BeforeEach
  void setUp() throws Exception {

    realisateur1 = new Artiste("Will", "Roe", "Americain");
    film1 = new Film("Blacklist", 2013, 13, realisateur1);
    
    InformationPersonnelle infos = new InformationPersonnelle("John", "Doe");
    utilisateur1 = new Utilisateur("johndoe", "password123", infos);
    
    String commentaire = "Film captivant avec une intrigue passionnante.";
    int note1 = 4;
    int note2 = 2;
    eval1 = new Evaluation(note1, commentaire, utilisateur1, film1);
    eval2 = new Evaluation(note2, commentaire, utilisateur1, film1);
  } 

  /**
   * Ne fait rien après les tests : à modifier au besoin.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @AfterEach
  void tearDown() throws Exception {}
  
  /**
   * Vérifie que l'on peut positionner une date de sortie de 2024 sur un film.
   */
  @Test
  void testannee2024() {
    film1.setAnnee(2024);
    assertEquals(film1.getAnnee(), 2024);
  }

  /**
   * Vérifie qu'on ne peut pas positionner une date de sortie négatif sur un film.
   */
  @Test
  void testAnneeNegatif() {
    film1.setAnnee(-2024);
    assertTrue(film1.getAnnee() != -2024);
  }
  
  /**
   * Vérifie que l'on peut positionner un age de limite 18 sur un film. 
   */
  @Test
  void testAgeLimite18() {
    film1.setAgemin(18);
    assertEquals(film1.getAgemin(), 18);
  }

  /**
   * Vérifie qu'on ne peut pas positionner une age négatif sur un film basique.
   */
  @Test
  void testAgeLimiteNegatif() {
    film1.setAgemin(-2);
    assertTrue(film1.getAgemin() != -2);
  }

  /**
   * Vérifie que l'on peux positionne l'etat du film a ouvert(true).
   */
  @Test
  void testEtat() {
    film1.setEtat(true);
    assertTrue(film1.getEtat() == true);
  }
  
  /**
   * Vérifie que l'on peux positionne l'etat du film a ferme(false).
   */
  @Test
  void testEtatMauvais() {
    film1.setEtat(false);
    assertTrue(film1.getEtat() == false);
  }
  
  /**
   * Vérifie que l'on peux recuperer la moyenne d'un film.
   */
  @Test
  void testMoyenne() {
    film1.addEval(eval1);
    film1.addEval(eval2);
    assertTrue(film1.getMoyenne() == 3.0);
  }
  
  /**
   * Vérifie que l'on peux ajouter une évaluation à un film.
   */
  @Test
  void testAjoutEvaluation() {
    film1.addEval(eval1);
    assertTrue(film1.getEvaluationsfilm().size() == 1);
  }
  
  /**
   * Vérifie que aucune evaulation n'est rajoutée si l'evaluation est nulle.
   */
  @Test
  void testAjoutEvaluation2() {
    Evaluation eval3 = null;
    film1.addEval(eval3);
    assertTrue(film1.getEvaluationsfilm().size() == 0);
  }
  
  /**
   * Verifie que les informations du contructeur sont bien gérées.
   */
  @Test
  void testConstructeurFilm() {
    
    Artiste realisateur = new Artiste("Will", "Smith", "Américain");
    
    Film film = new Film("Men in Black", 1997, 12, realisateur);
    
    assertEquals(film.getTitre(), "Men in Black");
    assertEquals(film.getAnnee(), 1997);
    assertEquals(film.getAgemin(), 12);
    assertTrue(film.getActeursfilm().isEmpty());
    assertEquals(film.getRealisateurfilm(), realisateur);
    assertTrue(film.getGenresfilm().isEmpty());
    assertTrue(film.getEvaluationsfilm().isEmpty());
    assertEquals(film.getEtat(), false);
    assertEquals(film.getMoyenne(), 0.0);
    assertEquals(film.getAffiche(), null);
  }


  
}