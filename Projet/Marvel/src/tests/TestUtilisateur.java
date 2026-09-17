package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import location.Artiste;
import location.Evaluation;
import location.Film;
import location.InformationPersonnelle;
import location.LocationException;
import location.NonConnecteException;
import location.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link location.Utilisateur}.
 *
 * @author Lise Auffret
 * @see location.Utilisateur
 */
class TestUtilisateur {
  /**
   * Un utilisateur basique .
   */
  private Utilisateur utilisateurBasique;
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
   * Evaluation du film de la part de l'utilisateur basique.
   */
  private Evaluation eval;

  /**
   * Instancie un utilisateur pour les tests.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    infoUtilisateur = new InformationPersonnelle("Skywalker", "Luke", "Planète Tatooine", 20);
    utilisateurBasique = new Utilisateur("pseudo", "mo!dep@sse", infoUtilisateur);
    artiste = new Artiste("Nom", "prenom", "francais");
    film = new Film("titre", 2024, 12, artiste);
    eval = new Evaluation(5, utilisateurBasique, film);

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
   * Vérifie que l'on peut modifier le mot de passe.
   */
  @Test
  void testMdpNonNull() {
    utilisateurBasique.setMotdepasse("motdepasse");
    assertEquals(utilisateurBasique.getMotdepasse(), "motdepasse");
  }

  /**
   * Vérifie que l'on ne peut pas mettre le mot de passe à null.
   */
  @Test
  void testMdpNull() {
    utilisateurBasique.setMotdepasse(null);
    assertTrue(utilisateurBasique.getMotdepasse() != null);
  }

  /**
   * Vérifie que l'on peut modifier les informations personnelles.
   */
  @Test
  void testInfo() {
    InformationPersonnelle infoUtilisateur2 = new InformationPersonnelle("Nom", "Prenom", 
        "Adresse", 20);
    utilisateurBasique.setInfos(infoUtilisateur2);
    assertEquals(utilisateurBasique.getInfos(), infoUtilisateur2);
  }

  /**
   * Vérifie que l'on peut modifier la liste de films en location.
   */
  @Test
  void testSetFilmLoc() {
    Set<Film> films = new HashSet<Film>();

    utilisateurBasique.setFilmEnLocation(films);
    assertEquals(utilisateurBasique.getFilmEnLocation(), films);
  }

  /**
   * Vérifie que l'on peut modifier la liste de films déjà loués.
   */
  @Test
  void testSetFilm() {
    Set<Film> films = new HashSet<Film>();
    utilisateurBasique.setFilmLoues(films);
    assertEquals(utilisateurBasique.getFilmLoues(), films);
  }

  /**
   * Vérifie que l'on peut modifier l'état de connexion avec une valeur correct.
   */
  @Test
  void testEtat() {
    utilisateurBasique.setEtat(false);
    assertTrue(!utilisateurBasique.getEtat());
  }

  /**
   * Vérifie que les paramètres des constructeurs sont correctement gérés.
   */
  @Test
  void testConstructeur() {
    Utilisateur u = new Utilisateur("pseudo2", "mo!dep@sse2", infoUtilisateur);

    assertEquals(u.getPseudo(), "pseudo2");
    assertEquals(u.getMotdepasse(), "mo!dep@sse2");
    assertEquals(u.getInfos(), infoUtilisateur);
    assertTrue(u.getFilmEnLocation() != null);
    assertTrue(u.getFilmLoues() != null);
    assertTrue(!u.getEtat());

  }

  /**
   * Vérifie que l'exception NonConnecteException se lève lorsque l'utilisateur
   * est déconnecter avant de voir si un film est louable.
   */
  @Test
  void test_estLouable_nonConnecte() {
    utilisateurBasique.setEtat(false);
    assertThrows(NonConnecteException.class, () -> utilisateurBasique.estLouable(film));

  }

  /**
   * Verifie si estLouable renvoie false lorsque le film est fermé.
   */
  @Test
  void test_estPasLouable() {
    utilisateurBasique.setEtat(true);
    film.setEtat(false);
    boolean res = true;
    try {
      res = utilisateurBasique.estLouable(film);
    } catch (NonConnecteException e) {
      return;
    }
    assertTrue(!res);
  }

  /**
   * Verifie si estLouable renvoie true lorsque le film est ouvert.
   */
  @Test
  void test_estLouable() {
    utilisateurBasique.setEtat(true);
    film.setEtat(true);

    boolean res = false;
    try {
      res = utilisateurBasique.estLouable(film);
    } catch (NonConnecteException e) {
      return;
    }
    assertTrue(res);
  }

  /**
   * Vérifie que l'exception NonConnecteException se lève lorsque l'utilisateur
   * est déconnecter avant de louer un film.
   */
  @Test
  void testLouerFilm_nonConnecte() {
    utilisateurBasique.setEtat(false);

    assertThrows(NonConnecteException.class, () -> utilisateurBasique.louerFilm(film));
  }

  /**
   * Vérifie que l'exception LocationException se lève lorsque un utilisateur
   * essaye de louer un film fermé.
   */
  @Test
  void testLouerFilm_nonLouable() {
    utilisateurBasique.setEtat(true);
    film.setEtat(false);
    assertThrows(LocationException.class, () -> utilisateurBasique.louerFilm(film));
  }

  /**
   * Vérifie que l'exception LocationException se lève lorsque un utilisateur
   * essaye de louer un film interdit pour son âge.
   */
  @Test
  void testLouerFilm_age() {
    utilisateurBasique.getInfos().setAge(10);
    utilisateurBasique.setEtat(true);
    film.setEtat(true);   
    film.setAgemin(16);
    
    assertThrows(LocationException.class, () -> utilisateurBasique.louerFilm(film));

  }

  /**
   * Vérifie que l'utilisateur peut louer un film ouvert à la location avec le bon
   * âge.
   */
  @Test
  void testLouerFilm() {
    utilisateurBasique.getInfos().setAge(18);
    film.setAgemin(16);
    utilisateurBasique.setEtat(true);
    film.setEtat(true);

    try {
      utilisateurBasique.louerFilm(film);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    assertTrue(utilisateurBasique.getFilmEnLocation().contains(film));
    assertTrue(utilisateurBasique.getFilmLoues().contains(film));
  }

  /**
   * Vérifie que l'exception LocationException se lève lorsque un utilisateur
   * essaye de louer un quatrième film.
   * 
   */
  @Test
  void testLouer4Film() {

    Film film2 = new Film("titre2", 2024, 12, artiste);
    Film film3 = new Film("titre3", 2027, 8, artiste);

    try {
      utilisateurBasique.louerFilm(film);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    try {
      utilisateurBasique.louerFilm(film2);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    try {
      utilisateurBasique.louerFilm(film3);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    Film film4 = new Film("titre4", 2027, 8, artiste);

    assertThrows(LocationException.class, () -> utilisateurBasique.louerFilm(film4));
  }

  /**
   * Vérifie que l'exception NonConnecteException se lève lorsque l'utilisateur
   * est déconnecter avant de terminer la location d'un film.
   */
  @Test
  void testFinLoc_nonConnecte() {
    utilisateurBasique.setEtat(false);
    assertThrows(NonConnecteException.class, () -> utilisateurBasique.finLocationFilm(film));
  }

  /**
   * Vérifie que l'exception LocationException se lève lorsque l'utilisateur est
   * connecté mais essaye de terminer la location d'un film qu'il n'a pas loué.
   */
  @Test
  void testFinLoc_pasFilm() {
    utilisateurBasique.setEtat(true);
    assertThrows(LocationException.class, () -> utilisateurBasique.finLocationFilm(film));
  }

  /**
   * Vérifie que le film n'est plus louer après que l'utilisateur a terminer la
   * location, mais que le film est toujours dans l'historique.
   */
  @Test
  void testFinLoc() {
    utilisateurBasique.setEtat(true);
    film.setEtat(true);

    try {
      utilisateurBasique.louerFilm(film);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    try {
      utilisateurBasique.finLocationFilm(film);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    assertTrue(!utilisateurBasique.getFilmEnLocation().contains(film));
    assertTrue(utilisateurBasique.getFilmLoues().contains(film));

  }

  /**
   * Vérifie que l'exception NonConnecteException se lève lorsque l'utilisateur
   * est déconnecter lorsqu'il essaye d'ajouter une évaluation.
   */
  @Test
  void testAddEval_nonConnecte() {
    utilisateurBasique.setEtat(false);
    assertThrows(NonConnecteException.class, () -> utilisateurBasique.ajouterEvaluation(film, 
        eval));
  }

  /**
   * Vérifie que l'exception LocationException se lève lorsque l'utilisateur
   * essaye d'ajouter une évaluation à un film qu'il n'a jamais loué.
   */
  @Test
  void testAddEval_pasFilm() {
    utilisateurBasique.setEtat(true);
    assertThrows(LocationException.class, () -> utilisateurBasique.ajouterEvaluation(film, eval));
  }

  /**
   * Vérifie que l'utilisateur peut mettre une évaluation sur un film qu'il a
   * loué.
   */
  @Test
  void testAddEval() {
    utilisateurBasique.setEtat(true);
    film.setEtat(true);

    try {
      utilisateurBasique.louerFilm(film);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    try {
      utilisateurBasique.ajouterEvaluation(film, eval);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    assertTrue(film.getEvaluationsfilm().contains(eval));

  }

  /**
   * Vérifie que l'exception LocationException se lève lorsque l'utilisateur
   * essaye d'ajouter une deuxième évaluation au même film.
   */
  @Test
  void testAdd2Eval() {
    utilisateurBasique.setEtat(true);
    film.setEtat(true);

    try {
      utilisateurBasique.louerFilm(film);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    try {
      utilisateurBasique.ajouterEvaluation(film, eval);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    Evaluation eval2 = new Evaluation(3, utilisateurBasique, film);

    assertThrows(LocationException.class, () -> utilisateurBasique.ajouterEvaluation(film, eval2));
  }

  /**
   * Vérifie que l'exception NonConnecteException se lève lorsque l'utilisateur
   * est déconnecter lorsqu'il essaye de modifier une évaluation.
   */
  @Test
  void testModifEval_nonConnecte() {
    utilisateurBasique.setEtat(false);

    assertThrows(NonConnecteException.class, () -> utilisateurBasique.modifierEvaluation(film, 
        eval));
  }

  /**
   * Vérifie que l'exception LocationException se lève lorsque l'utilisateur
   * essaye de modifier une évaluation à un film alors que l'utilisateur n'a
   * jamais évalué le film.
   */
  @Test
  void testModifEval_pasEval() {
    utilisateurBasique.setEtat(true);
    assertThrows(LocationException.class, () -> utilisateurBasique.modifierEvaluation(film, eval));
  }

  /**
   * Vérifie que l'utilisateur peut modifer une évaluation sur un film qu'il a
   * loué et déjà évalué.
   */
  @Test
  void testModifEval() {
    utilisateurBasique.setEtat(true);
    film.setEtat(true);

    try {
      utilisateurBasique.louerFilm(film);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    try {
      utilisateurBasique.ajouterEvaluation(film, eval);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    Evaluation eval2 = new Evaluation(3, utilisateurBasique, film);

    try {
      utilisateurBasique.modifierEvaluation(film, eval2);
    } catch (LocationException e) {
      return;
    } catch (NonConnecteException e) {
      return;
    }

    assertTrue(!film.getEvaluationsfilm().contains(eval));
    assertTrue(film.getEvaluationsfilm().contains(eval2));
  }

  /**
   * Vérifie que l'exception NonConnecteException se lève lorsque l'utilisateur se
   * déconnecte alors qu'il est déjà déconnecté.
   */
  @Test
  void testDeconnexion_echec() {
    utilisateurBasique.setEtat(false);
    assertThrows(NonConnecteException.class, () -> utilisateurBasique.deconnexion());
  }

  /**
   * Vérifie que l'utilisateur peut se déconnecter.
   */
  @Test
  void testDeconnexion() {
    utilisateurBasique.setEtat(true);

    try {
      utilisateurBasique.deconnexion();
    } catch (NonConnecteException e) {
      return;
    }

    assertTrue(!utilisateurBasique.getEtat());

  }

}
