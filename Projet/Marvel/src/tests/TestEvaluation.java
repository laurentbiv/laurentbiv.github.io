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
 * Tests JUnit de la classe {@link location.Evaluation}.
 *
 * @author Lise Auffret
 * @see location.Utilisateur
 */
class TestEvaluation {
  /**
   * Une évaluation test.
   */
  private Evaluation evalTest;
  /**
   * Un utilisateur basique .
   */
  private Utilisateur utilisateur;
  /**
   * Informations associés à l'utilisateur test.
   */
  private InformationPersonnelle infoUtilisateur;
  /**
   * Film basique utile pour tester les méthodes.
   */
  private Film film;
  /**
   * Artiste basique utile pour tester les méthodes.
   */
  private Artiste artiste;

  /**
   * Instancie une évaluation pour les tests.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    infoUtilisateur = new InformationPersonnelle("Skywalker", "Luke", "Planète Tatooine", 20);
    utilisateur = new Utilisateur("pseudo", "mo!dep@sse", infoUtilisateur);

    artiste = new Artiste("Nom", "prenom", "francais");
    film = new Film("titre", 2024, 12, artiste);

    evalTest = new Evaluation(5, "commentaire", utilisateur, film);

  }

  /**
   * Ne fait rien après les tests : à modifier au besoin.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @AfterEach
  void tearDown() throws Exception {
  }

  /**
   * Vérifie qu'on ne peut pas mettre de note supérieur à 5.
   */
  @Test
  void testSupNote() {
    evalTest.setNote(6);
    assertTrue(evalTest.getNote() != 6);
  }

  /**
   * Vérifie qu'on ne peut pas mettre de note inférieur à 0.
   */
  @Test
  void testInfNote() {
    evalTest.setNote(-2);
    assertTrue(evalTest.getNote() != -2);
  }

  /**
   * Vérifie que l'on peut modifier la note avec une note compris entre 0 et 5.
   */
  @Test
  void testNote() {
    evalTest.setNote(2);
    assertEquals(evalTest.getNote(), 2);
  }

  /**
   * Vérifie que l'on peut modifier le commentaire .
   */
  @Test
  void testCommentaire() {
    evalTest.setCommentaire("commentaire2");
    assertEquals(evalTest.getCommentaire(), "commentaire2");
  }

  /**
   * Vérifie que l'on peut modifier l'auteur .
   */
  @Test
  void testAuteur() {
    InformationPersonnelle infoUtilisateur2 = new InformationPersonnelle("Nom", "Prenom", 
        "Adresse", 20);
    Utilisateur utilisateur2 = new Utilisateur("pseudo2", "mo!dep@sse", infoUtilisateur2);
    evalTest.setAuteur(utilisateur2);
    assertEquals(evalTest.getAuteur(), utilisateur2);
  }

  /**
   * Vérifie que l'on peut modifier le film .
   */
  @Test
  void testFilm() {
    Artiste artiste2 = new Artiste("Nom", "prenom2", "anglais");
    Film film2 = new Film("titre2", 2024, 12, artiste2);

    evalTest.setFilm(film2);
    assertEquals(evalTest.getFilm(), film2);
  }

  /**
   * Vérifie que les paramètres du constructeur avec commentaire sont correctement
   * gérés.
   */
  @Test
  void testConstructeurAvecCom() {
    Evaluation e = new Evaluation(5, "commentaire", utilisateur, film);

    assertEquals(e.getNote(), 5);
    assertEquals(e.getCommentaire(), "commentaire");
    assertEquals(e.getAuteur(), utilisateur);
    assertEquals(e.getFilm(), film);

  }

  /**
   * Vérifie que les paramètres du constructeur sans commentaire sont correctement
   * gérés.
   */
  @Test
  void testConstructeurSansCom() {
    Evaluation e = new Evaluation(5, utilisateur, film);

    assertEquals(e.getNote(), 5);
    assertEquals(e.getCommentaire(), null);
    assertEquals(e.getAuteur(), utilisateur);
    assertEquals(e.getFilm(), film);

  }

}
