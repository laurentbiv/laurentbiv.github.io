package location;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


/**
 * Toutes les informations d'un utlisateur : pseudo, mot de passe, informations
 * personnelles, les films en location, l'historique des films, l'état de
 * connexion.
 *
 * @author liseauffret
 *
 */
public class Utilisateur {

  /**
   * Pseudo de l'utilisateur.
   */
  final String pseudo;

  /**
   * Mot de passe de l'utilisateur.
   */
  String motdepasse;
  /**
   * Informations personelles de l'utilisateur (nom, prenom, adresse, âge).
   */
  InformationPersonnelle infos;
  /**
   * Liste de films que l'utilisateur a actuellement en location.
   */
  Set<Film> filmEnLocation;
  /**
   * Liste de films que l'utilisateur a déjà loué.
   */
  Set<Film> filmLoues;
  /**
   * Etat de la connexion de l'utilisateur.
   */
  boolean etat;

  /**
   * Constructeur de la classe.
   *
   * @param pseudo     le pseudo de l'utilisateur
   * @param motdepasse le mot de passe de l'utilisateur
   * @param infos      les informations personnelles de l'utilisateur
   */
  public Utilisateur(String pseudo, String motdepasse, InformationPersonnelle infos) {
    super();
    this.pseudo = pseudo;
    this.motdepasse = motdepasse;
    this.infos = infos;
    this.filmEnLocation = new HashSet<Film>();
    this.filmLoues = new HashSet<Film>();
    this.etat = false;
  }

  /**
   * Renvoie le pseudo de l'utilisateur.
   *
   * @return le pseudo de l'utilisateur
   */
  public String getPseudo() {
    return pseudo;
  }

  /**
   * Renvoie le mot de passe de l'utilisateur.
   *
   * @return le mot de passe de l'utilisateur
   */
  public String getMotdepasse() {
    return motdepasse;
  }

  /**
   * Modifie le mot de passe de l'utilisateur.
   *
   * @param motdepasse le mot de passe de l'utilisateur
   */
  public void setMotdepasse(String motdepasse) {
    if (motdepasse != null) {
      this.motdepasse = motdepasse;
    }

  }

  /**
   * Renvoie les informations personnelles de l'utilisateur.
   *
   * @return les informations personnelles de l'utilisateur
   */
  public InformationPersonnelle getInfos() {
    return infos;
  }

  /**
   * Modifie les informations personnelles de l'utilisateur.
   *
   * @param infos les informations personnelles de l'utilisateur
   */
  public void setInfos(InformationPersonnelle infos) {
    this.infos = infos;
  }

  /**
   * Renvoie la liste de films en location.
   *
   * @return set de films en location
   */
  public Set<Film> getFilmEnLocation() {
    return filmEnLocation;
  }

  /**
   * Modifie la liste de films en location.
   *
   * @param filmEnLocation la liste de films en location
   */
  public void setFilmEnLocation(Set<Film> filmEnLocation) {
    this.filmEnLocation = filmEnLocation;
  }

  /**
   * Renvoie la liste de films qui l'utilisateur a déjà loué.
   *
   * @return la liste de films qui l'utilisateur a déjà loué
   */
  public Set<Film> getFilmLoues() {
    return filmLoues;
  }

  /**
   * Modifie la liste de films qui l'utilisateur a déjà loué.
   *
   * @param filmLoues la liste de films qui l'utilisateur a déjà loué
   */
  public void setFilmLoues(Set<Film> filmLoues) {
    this.filmLoues = filmLoues;
  }

  /**
   * Renvoie l'état de connexion de l'utilisateur.
   *
   * @return l'état de connexion de l'utilisateur
   */
  public boolean getEtat() {
    return etat;
  }

  /**
   * Modifie l'état de connexion de l'utilisateur.
   *
   * @param etat l'état de connexion de l'utilisateur
   */
  public void setEtat(boolean etat) {
    this.etat = etat;   
  }

  @Override
  public int hashCode() {
    return Objects.hash(etat, filmEnLocation, filmLoues, infos, motdepasse, pseudo);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Utilisateur other = (Utilisateur) obj;
    return etat == other.etat && Objects.equals(filmEnLocation, other.filmEnLocation)
        && Objects.equals(filmLoues, other.filmLoues) && Objects.equals(infos, other.infos)
        && Objects.equals(motdepasse, other.motdepasse) && Objects.equals(pseudo, other.pseudo);
  }

  /**
   * Information sur le fait qu'un film est ouvert à la location.
   *
   * @param film le film dont on veut vérifier la possibilité de location
   * @return <code>true</code> si le film est ouvert à la location,
   *         <code>false</code> sinon
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */
  public boolean estLouable(Film film) throws NonConnecteException {
    if (!this.etat) {
      throw new NonConnecteException("Utilisateur déconnecté");
    }
    return film.getEtat();
  }

  /**
   * L'utilisateur connecté loue un film. Il peut le louer s'il a moins de 3 films
   * en cours de location et s'il a l'âge suffisant pour voir le film.
   *
   * @param film le film à louer
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException    en cas de refus de location, l'exception
   *                              contiendra un message précisant le problème
   *                              (déjà 3 films loués, âge insuffisant ou autre)
   */
  public void louerFilm(Film film) throws NonConnecteException, LocationException {
    if (!this.etat) {
      throw new NonConnecteException("Utilisateur déconnecté");     
    }
    if (!this.estLouable(film)) {

      throw new LocationException("Le film n'est pas louable");     
    }
    if (this.filmEnLocation.size() >= 3) {
      throw new LocationException("L'utilisateur a déjà 3 films");      
    }
    if (this.infos.getAge() < film.getAgemin()) {
      throw new LocationException("L'utilisateur n'a pas l'âge minimum pour ce film");
    }
    this.filmEnLocation.add(film);
    this.filmLoues.add(film);
  }

  /**
   * Termine la location d'un film.
   *
   * @param film le film dont la location est terminée
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException    en cas de problème, notamment si l'utilisateur
   *                              n'avait pas ce film en location, l'exception
   *                              contiendra un message précisant le problème
   */
  public void finLocationFilm(Film film) throws NonConnecteException, LocationException {
    if (!this.etat) {
      throw new NonConnecteException("Utilisateur déconnecté");
    }
    if (!this.filmEnLocation.contains(film)) {
      throw new LocationException("L'utilisateur n'a pas loué ce film");
    }
    this.filmEnLocation.remove(film);
  }

  /**
   * Ajoute à un film une évaluation de la part de l'utilisateur connecté.
   * L'utilisateur doit avoir loué le film pour le commenter (que le film soit
   * actuellement en sa location ou qu'il ait été loué puis retourné
   * préalablement). L'utilisateur ne doit pas déjà avoir déposé une évaluation
   * pour ce film.
   *
   * @param film le film à évaluer
   * @param eval l'évaluation du film
   * @throws NonConnecteException si aucun utilisateur n'était connecté
   * @throws LocationException    en cas d'erreur pour ajouter l'évaluation,
   *                              l'exception contiendra un message précisant le
   *                              problème
   */
  public void ajouterEvaluation(Film film, Evaluation eval) 
      throws NonConnecteException, LocationException {
    if (!this.etat) {
      throw new NonConnecteException("Utilisateur déconnecté");
    }
    if (!this.filmLoues.contains(film)) {
      throw new LocationException("L'utilisateur n'a jamais loué ce film");
    }
    for (Iterator<Evaluation> it = film.getEvaluationsfilm().iterator(); it.hasNext();) {
      Evaluation e = it.next();
      if (e.getAuteur().equals(this)) {
        throw new LocationException("L'utilisateur a déjà mis une évaluation");
      }
    }
    film.addEval(eval);

  }

  /**
   * Modifie l'évaluation que l'utilisateur connecté avait déjà déposée sur un
   * film. Ne peut se faire que si l'utilisateur avait déjà évalué le film.
   *
   * @param film le film dont l'utilisateur modifie l'évaluation
   * @param eval la nouvelle évaluation qui remplace la précédente
   * @throws NonConnecteException si aucun utilisateur n'était connecté
   * @throws LocationException    en cas d'erreur pour modifier l'évaluation,
   *                              l'exception contiendra un message précisant le
   *                              problème
   */
  public void modifierEvaluation(Film film, Evaluation eval) 
      throws NonConnecteException, LocationException {
    if (!this.etat) {
      throw new NonConnecteException("Utilisateur déconnecté");
    }
    int i = 0;
    for (Iterator<Evaluation> it = film.getEvaluationsfilm().iterator(); it.hasNext();) {
      Evaluation e = it.next();
      if (e.getAuteur().equals(this)) {
        i = 1;
        film.getEvaluationsfilm().remove(e);
        film.addEval(eval);

      }
    }
    if (i == 0) {
      throw new LocationException("L'utilisateur n'a déjà mis une évaluation");
    }
  }

  /**
   * Déconnecte l'utilisateur actuellement connecté.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   */
  public void deconnexion() throws NonConnecteException {
    if (!this.etat) {
      throw new NonConnecteException("Utilisateur déjà déconnecté");
    }
    this.setEtat(false);
  }

}
