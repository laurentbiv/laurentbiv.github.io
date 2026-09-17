package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import location.GestionUtilisateur;
import location.InformationPersonnelle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link location.GestionUtilisateur}.
 *
 * @author Lise Auffret
 * @see location.GestionUtilisateur
 */
class TestGestionUtilisateur {

  /**
   * Liste de tous les utilisateurs vide au début des tests.
   */
  private GestionUtilisateur utilisateurs;

  /**
   * Instancie un utilisateur pour les tests.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    utilisateurs = new GestionUtilisateur();

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
   * Test du constructeur.
   */
  @Test
  void testConstructeur() {
    GestionUtilisateur listeUtilisateurs = new GestionUtilisateur();
    assertTrue(listeUtilisateurs.getUtilisateurs() != null);
  }

  /**
   * Vérifie que l'on peut inscrire un nouvel utilisateur si toutes les
   * informations sont remplies.
   */
  @Test
  void testInscription() {
    InformationPersonnelle info = new InformationPersonnelle("Skywalker", "Luke", 
        "Planète Tatooine", 20);

    int res = utilisateurs.inscription("pseudo", "mdp", info);

    assertEquals(res, 0);
    assertEquals(utilisateurs.getUtilisateurs().size(), 1);

  }

  /**
   * Vérifie que l'on ne peut faire une inscription s'il manque une information
   * comme le mot de passe ou le pseudo.
   */
  @Test
  void testInscription2() {
    InformationPersonnelle info = new InformationPersonnelle("Skywalker", "Luke", 
        "Planète Tatooine", 20);

    int res = utilisateurs.inscription("pseudo", null, info);
    assertEquals(res, 2);
    assertEquals(utilisateurs.getUtilisateurs().size(), 0);
  }

  /**
   * Vérifie que l'on ne peut faire une inscription si les informations
   * personnelles ne sont pas complètes.
   */
  @Test
  void testInscription3() {
    InformationPersonnelle info = new InformationPersonnelle("Skywalker", null, 
        "Planète Tatooine", 20);

    int res = utilisateurs.inscription("pseudo", "mdp", info);
    assertEquals(res, 3);
    assertEquals(utilisateurs.getUtilisateurs().size(), 0);
  }

  /**
   * Vérifie que l'on ne peut faire une inscription si le pseudo est déjà utilisé.
   */
  @Test
  void testInscription1() {
    InformationPersonnelle info = new InformationPersonnelle("Skywalker", "Luke", 
        "Planète Tatooine", 20);
    InformationPersonnelle info2 = new InformationPersonnelle("NOM", "prénom",
        "Planète Terre", 20);

    utilisateurs.inscription("pseudo", "mdp", info);

    int res = utilisateurs.inscription("pseudo", "mdp2", info2);

    assertEquals(res, 1);
    assertEquals(utilisateurs.getUtilisateurs().size(), 1);
  }

  /**
   * Vérifie que l'utilisateur peut se connecter si il a le bon pseudo et mot de
   * passe.
   */
  @Test
  void testConnexion() {
    InformationPersonnelle info = new InformationPersonnelle("Skywalker", "Luke", 
        "Planète Tatooine", 20);

    utilisateurs.inscription("pseudo", "mdp", info);

    assertTrue(utilisateurs.connexion("pseudo", "mdp"));

  }

  /**
   * Vérifie que l'utilisateur ne peut pas se connecter s'il n'a pas le bon pseudo
   * ou mot de passe.
   */
  @Test
  void testConnexion_echec() {
    InformationPersonnelle info = new InformationPersonnelle("Skywalker", "Luke",
        "Planète Tatooine", 20);

    utilisateurs.inscription("pseudo", "mdp", info);

    assertTrue(!utilisateurs.connexion("pseudo", "mdp2"));

  }

  /**
   * Vérifie que l'utilisateur ne peut pas se connecter s'il n'a pas de compte.
   */
  @Test
  void testConnexion_pasCompte() {

    assertTrue(!utilisateurs.connexion("pseudo", "mdp2"));

  }

}