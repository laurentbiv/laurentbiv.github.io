package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import location.Artiste;
import location.Film;
import location.Genre;
import location.GestionFilm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link location.GestionFilm GestionFilm}.
 *
 * @author Andias Ames
 * @see location.GestionFilm
 */

class TestGestionFilm {
  /**
   * Un réalisateur.
   */
  private Artiste realisateur1;
  /**
   * Un premier acteur.
   */
  private Artiste acteur1;

  /**
   * Un deuxieme acteur.
   */
  private Artiste acteur2;
  /**
   * Un film : Titre, date de sortie, age limite et réalisteur.
   */
  private GestionFilm gestion;

  /**
   * une exception.
   *
   * @throws Exception ne peut pas être levée ici.
   */

  @BeforeEach
  void setUp() throws Exception {
    gestion = new GestionFilm();
    realisateur1 = new Artiste("Will", "Roe", "Americain");
    acteur1 = new Artiste("Andias", "Ames", "Francais");
    acteur2 = new Artiste("Jamal", "Musiala", "Allemand");
  }

  /**
   * Ne fait rien après les tests : à modifier au besoin.
   *
   * @throws Exception ne peut pas être levée ici.
   */
  @AfterEach
  void tearDown() throws Exception {
  }

  /**
   * Vérifie que l'on peut creer un film.
   */
  @Test
  void testCreationFilm1() {
    Film film1 = gestion.creerFilm("Robin des bois", realisateur1, 2005, 10);
    assertTrue(gestion.getFilms().contains(film1));
  }

  /**
   * Vérifie que l'on ne peux pas creer un film si le titre entree en parametre
   * est vide.
   */
  @Test
  void testCreationFilm2() {
    Film film1 = gestion.creerFilm("", realisateur1, 2005, 10);
    
    assertTrue(gestion.getFilms().size() != 1);
    assertTrue(!gestion.getFilms().contains(film1));
  }

  /**
   * Vérifie que l'on ne peux pas creer un film si le réalisateur est null.
   */
  @Test
  void testCreationFilm3() {
    Film film1 = gestion.creerFilm("Robin des bois", null, 2005, 10);
    assertTrue(gestion.getFilms().size() != 1);
    assertTrue(!gestion.getFilms().contains(film1));
  }

  /**
   * Vérifie que l'on ne peux pas creer un film si l'annéé entrée en paramètre est
   * négatif.
   */
  @Test
  void testCreationFilm4() {
    Film film1 = gestion.creerFilm("Robin des bois", realisateur1, -2005, 10);
    assertTrue(gestion.getFilms().size() != 1);
    assertTrue(!gestion.getFilms().contains(film1));
  }
  
  /**
   * Vérifie que l'on ne peux pas creer un film si il existe déja un film avec le
   * meme nom.
   */
  @Test
  void testCreationFilm5() {
    Film film1 = gestion.creerFilm("Robin des bois", realisateur1, 2008, 10);
    Film film2 = gestion.creerFilm("Robin des bois", realisateur1, 2024, 18);
    assertTrue(gestion.getFilms().size() != 2);
    assertTrue(gestion.getFilms().contains(film1));
    assertTrue(!gestion.getFilms().contains(film2));
  }

  /**
   * Vérifie que l'on peux supprimer un film.
   */
  @Test
  void testSupressionFilm1() {
    Film film1 = gestion.creerFilm("Robin des bois", null, 2005, 10);
    gestion.supprimerFilm(film1);
    assertTrue(gestion.getFilms().size() == 0);
    assertTrue(!gestion.getFilms().contains(film1));
  }

  /**
   * Vérifie que l'on ne supprime rien si un paramètre null est passé.
   */
  @Test
  void testSupressionFilm2() {
    Film film1 = gestion.creerFilm("Robin des bois", realisateur1, 2005, 10);
    Film film2 = gestion.creerFilm("Spongebob", realisateur1, 2005, 10);
    gestion.supprimerFilm(null);
    assertTrue(gestion.getFilms().contains(film1));
    assertTrue(gestion.getFilms().contains(film2));

  }

  /**
   * Vérifie que l'on ne supprime rien si un paramètre passé en paramètre est un
   * film inexistant dans notre gestionnaire.
   */
  @Test
  void testSupressionFilm3() {
    Film film1 = new Film("Robin des bois", 2005, 10, realisateur1);
    Film film2 = gestion.creerFilm("Spongebob", realisateur1, 2005, 10);
    gestion.supprimerFilm(film1);
    assertTrue(!gestion.getFilms().contains(film1));
    assertTrue(gestion.getFilms().contains(film2));
  }

  /**
   * Vérifie que l'on peut ouvrir un film a la location.
   */
  @Test
  void testOuvertureLocation1() {
    Film film1 = gestion.creerFilm("Robin des bois", realisateur1, 2005, 10);
    assertTrue(gestion.getFilms().contains(film1));
    assertTrue(film1.getEtat() == false);
    gestion.ouvrirLocation(film1);
    assertTrue(film1.getEtat() == true);
  }

  /**
   * Vérifie que l'on peut ouvrir un film a la location meme si il est deja
   * ouvert.
   */
  @Test
  void testOuvertureLocation2() {
    Film film1 = gestion.creerFilm("Robin des bois", realisateur1, 2005, 10);
    assertTrue(gestion.getFilms().contains(film1));
    gestion.ouvrirLocation(film1);
    assertTrue(film1.getEtat() == true);
    gestion.ouvrirLocation(film1);
    assertTrue(film1.getEtat() == true);
  }

  /**
   * Vérifie que rien ne change si un film ne faisant pas partis de notre
   * gestionnaire est passé en parametre.
   */
  @Test
  void testOuvertureLocation3() {
    Film film1 = new Film("Robin des bois", 2005, 10, realisateur1);
    assertTrue(!gestion.getFilms().contains(film1));
    gestion.ouvrirLocation(film1);
    assertTrue(film1.getEtat() == false);
  }

  /**
   * Vérifie que l'on peut fermer un film a la location meme si il est deja fermé.
   */
  @Test
  void testFermetureLocation1() {
    Film film1 = gestion.creerFilm("Robin des bois", realisateur1, 2005, 10);
    assertTrue(gestion.getFilms().contains(film1));
    assertTrue(film1.getEtat() == false);
    gestion.fermerLocation(film1);
    assertTrue(film1.getEtat() == false);
  }

  /**
   * Vérifie que létat d'un film ne faisant pas partis de notre gestionnaire ne
   * change pas si il est passé en parametre.
   */
  @Test
  void testFermetureLocation2() {
    Film film1 = new Film("Robin des bois", 2005, 10, realisateur1);
    assertTrue(!gestion.getFilms().contains(film1));
    film1.setEtat(true);
    gestion.fermerLocation(film1);
    assertTrue(film1.getEtat() == true);
  }

  /**
   * Vérifie que l'on peut acceder a un film en renseignant son titre.
   */
  @Test
  void testTitreFilm1() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    assertEquals(gestion.getFilm("Blacklist"), film1);
  }

  /**
   * Vérifie que l'on peut acceder a un film en renseignant son titre que en
   * majuscule.
   */
  @Test
  void testTitreFilm2() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    assertEquals(gestion.getFilm("BLACKLIST"), film1);
  }

  /**
   * Vérifie que l'on rien n'est retouné si le film est inexistant dans notre
   * gestionnaire.
   */
  @Test
  void testTitreFilm3() {
    Film film1 = new Film("Blacklist", 2005, 10, realisateur1);
    assertEquals(film1.getTitre(), "Blacklist");
    assertEquals(gestion.getFilm("Blacklist"), null);
  }

  /**
   * Vérifie que l'ensemble de film est vide meme qaund on créé un film hors
   * gestionnaire.
   */
  @Test
  void testEnsembleFilm1() {
    Film film1 = new Film("Blacklist", 2005, 10, realisateur1);
    assertEquals(film1.getTitre(), "Blacklist");
    assertEquals(gestion.ensembleFilms(), null);
  }

  /**
   * Vérifie que l'ensemble de film contient bien le film ajouté dans le
   * gestionnaire.
   */
  @Test
  void testEnsembleFilm2() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    assertTrue(gestion.ensembleFilms().contains(film1));
    assertTrue(gestion.ensembleFilms().size() == 1);
  }

  /**
   * Vérifie que l'on peux ajouter des acteurs a un film.
   */

  @Test
  void testAjouterActeur1() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    gestion.ajouterActeurs(film1, acteur1, acteur2);
    assertTrue(film1.getActeursfilm().contains(acteur1));
    assertTrue(film1.getActeursfilm().contains(acteur2));
  }

  /**
   * Vérifie qu'aucun acteurs n'est ajouté au film si il ne fait pas partie du
   * gestionnaire.
   */
  @Test
  void testAjouterActeur2() {
    Film film1 = new Film("Blacklist", 2005, 10, realisateur1);
    gestion.ajouterActeurs(film1, acteur1, acteur2);
    assertTrue(film1.getActeursfilm().size() == 0);
  }

  /**
   * Vérifie que l'on peux ajouter des genres a un film.
   */
  @Test
  void testAjouterGenre1() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    gestion.ajouterGenres(film1, Genre.Action, Genre.Drame);
    assertTrue(film1.getGenresfilm().contains(Genre.Action));
    assertTrue(film1.getGenresfilm().contains(Genre.Drame));
  }

  /**
   * Vérifie qu'aucun genre n'est ajouté au film si il ne fait pas partie du
   * gestionnaire.
   */
  @Test
  void testAjouterGenre2() {
    Film film1 = new Film("Blacklist", 2005, 10, realisateur1);
    gestion.ajouterGenres(film1, Genre.Action, Genre.Drame);
    assertTrue(film1.getGenresfilm().size() == 0);
  }

  /**
   * Vérifie que la moyenne d'un film par son titre est bien récupéré.
   */
  @Test
  void testEvaluationMoyenneTitre1() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    assertTrue(gestion.evaluationMoyenne(film1.getTitre()) == -1.0);
  }

  /**
   * Vérifie qu'aucune moyenne n'est obtenue quand le titre est null pour string
   * et Film.
   */
  @Test
  void testEvaluationMoyenneTitre2() {
    assertTrue(gestion.evaluationMoyenne("") == -2.0);
  }

  /**
   * Vérifie que all moyenne d'un film est bien recupéré.
   */
  @Test
  void testEvaluationMoyenneFilm() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    assertTrue(gestion.evaluationMoyenne(film1) == -1.0);
  }

  /**
   * Verifie que la liste des film d'un certains genre est récupéré.
   */
  @Test
  void testEnsembleGenres1() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    gestion.ajouterGenres(film1, Genre.Action, Genre.Drame);
    assertTrue(gestion.ensembleFilmsGenre(Genre.Action).contains(film1));
  }

  /**
   * Verifie que la liste des film d'un certains genre est récupéré et que le film
   * n'est pas dedans car ne correspond pas.
   */
  @Test
  void testEnsembleGenres2() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    gestion.ajouterGenres(film1, Genre.Action, Genre.Drame);
    assertTrue(gestion.ensembleFilmsGenre(Genre.Aventure) == null);
  }

  /**
   * Verifie que la liste des film d'un certains genre est récupéré quand c'est un
   * string passé en paramètre.
   */
  @Test
  void testEnsembleGenreString1() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    gestion.ajouterGenres(film1, Genre.Action, Genre.Drame);
    assertTrue(gestion.ensembleFilmsGenre("Action").contains(film1));
  }

  /**
   * Verifie que la liste des film d'un certains genre est récupéré et que le film
   * n'est pas dedans car ne correspond pas avec string en paramètre.
   */
  @Test
  void testEnsembleGenreString2() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    gestion.ajouterGenres(film1, Genre.Action, Genre.Drame);
    assertTrue(gestion.ensembleFilmsGenre("Aventure") == null);
  }

  /**
   * Verifie que l'on peux acceder a la liste des évaluation d'un film passé en
   * paramètre.
   */
  @Test
  void testEnsembleEvaluation1() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    assertTrue(gestion.ensembleEvaluationsFilm(film1) == null);
  }

  /**
   * Verifie que l'on peux acceder a la liste des évaluation avec le titre du film
   * passé en paramètre.
   */
  @Test
  void testEnsembleEvaluation2() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    assertTrue(gestion.ensembleEvaluationsFilm(film1.getTitre()) == null);
  }

  /**
   * Verifie que l'on recupère rien avec le titre vide (null) passé en paramètre.
   */
  @Test
  void testEnsembleEvaluation3() {
    Film film1 = gestion.creerFilm("Blacklist", realisateur1, 2005, 10);
    assertTrue(film1.getEvaluationsfilm().size() == 0);
    assertTrue(gestion.ensembleEvaluationsFilm("") == null);

  }

}