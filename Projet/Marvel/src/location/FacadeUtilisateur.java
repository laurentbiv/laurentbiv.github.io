package location;

import java.util.Set;


/**
 * Facade Utilisateur.
 *
 * @author liseauffret
 *
 */
public class FacadeUtilisateur implements InterUtilisateur {
  /**
   * Gestion utilisateur.
   */
  public GestionUtilisateur utilisateurs;
  /**
   * Utilisateur.
   */
  public Utilisateur utilisateur;
  /**
   * Gestion film.
   */
  GestionFilm films;
  /**
   * Gestion artiste.
   */
  GestionArtiste artistes;
  
  
  /**
   * Constructeur de la facade utlisateur.
   *
   * @param film Gestionnaire de film.
   */
  public FacadeUtilisateur(GestionFilm film) {
    this.utilisateurs = new GestionUtilisateur();
    this.films = film;
    this.artistes = new GestionArtiste();
    
    
  }
  
  /**
   * Inscription d'un utilisateur. Le pseudo choisi ne doit pas déjà exister
   * parmi les utilisateurs déjà inscrits.
   *
   * @param pseudo le pseudo (unique) de l'utilisateur
   * @param mdp le mot de passe de l'utilisateur (ne pas doit pas être vide ou
   *        <code>null</code>)
   * @param info les informations personnelles sur l'utilisateur
   * @return un code précisant le résultat de l'inscription : 0 si l'inscription
   *         s'est bien déroulée, 1 si le pseudo était déjà utilisé, 2 si le
   *         pseudo ou le mot de passe était vide, 3 si les informations
   *         personnelles ne sont pas bien précisées
   */
  public int inscription(String pseudo, String mdp,
      InformationPersonnelle info) {
    return utilisateurs.inscription(pseudo, mdp, info);
  }
  
  /**
   * Connexion de l'utilisateur. Une fois connecté, l'utilisateur pourra accéder
   * aux services de location et déposer des commentaires sur les films qu'il a
   * loués.
   *
   * @param pseudo le pseudo de l'utilisateur
   * @param mdp le mot de passe de l'utilsateur
   * @return <code>true</code> si la connexion s'est bien déroulée,
   *         <code>false</code> en cas de couple pseudo/mot de passe invalide
   */
  public boolean connexion(String pseudo, String mdp) {
    for (Utilisateur u : utilisateurs.getUtilisateurs()) {
      if (u.getPseudo().equals(pseudo)) {
        this.utilisateur = u;
      }
    }
    return utilisateurs.connexion(pseudo, mdp);
    
  }
  
  /**
   * Déconnecte l'utilisateur actuellement connecté.
   *
   * @throws NonConnecteException si aucun utilisateur n'est connecté.
   */
  public void deconnexion() {
    try {
      utilisateur.deconnexion();
      utilisateur = null;
    } catch (NonConnecteException e) {
      return;
    }
  }
  
  /**
   * L'utilisateur connecté loue un film. Il peut le louer s'il a moins de 3
   * films en cours de location et s'il a l'âge suffisant pour voir le film.
   *
   * @param film le film à louer
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException en cas de refus de location, l'exception
   *         contiendra un message précisant le problème (déjà 3 films loués,
   *         âge insuffisant ou autre)
   */
  public void louerFilm(Film film) {
    try {
      utilisateur.louerFilm(film);
    } catch (NonConnecteException e) {
      return;
    } catch (LocationException e) {
      return;
    }
  }
  
  /**
   * Termine la location d'un film.
   *
   * @param film le film dont la location est terminée
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   * @throws LocationException en cas de problème, notamment si l'utilisateur
   *         n'avait pas ce film en location, l'exception contiendra un message
   *         précisant le problème
   */
  public void finLocationFilm(Film film) {
    try {
      utilisateur.finLocationFilm(film);
    } catch (NonConnecteException e) {
      return;
    } catch (LocationException e) {
      return;
    }
  }
  
  /**
   * Information sur le fait qu'un film est ouvert à la location.
   *
   * @param film le film dont on veut vérifier la possibilité de location
   * @return <code>true</code> si le film est ouvert à la location,
   *         <code>false</code> sinon
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */
  public boolean estLouable(Film film) {
    boolean b = false;
    try {
      b = utilisateur.estLouable(film);
    } catch (NonConnecteException e) {
      return b;
    }
    return b;
  }
  
  /**
   * Renvoie l'ensemble des films actuellement en location par l'utilisateur
   * connecté.
   *
   * @return les films en location par l'utilisateur connecté ou
   *         <code>null</code> si aucun film actuellement en location
   * @throws NonConnecteException si aucun utilisateur n'est connecté
   */
  public Set<Film> filmsEnLocation() throws NonConnecteException {
    if (!utilisateur.getEtat()) {
      throw new NonConnecteException("Utilisateur déconnecté");
    }
    Set<Film> res = utilisateur.getFilmEnLocation();
    if (res.isEmpty()) {
      return null;
    }
    return utilisateur.getFilmEnLocation();
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
   * @throws LocationException en cas d'erreur pour ajouter l'évaluation,
   *         l'exception contiendra un message précisant le problème
   */
  public void ajouterEvaluation(Film film, Evaluation eval) {
    try {
      utilisateur.ajouterEvaluation(film, eval);
    } catch (NonConnecteException e) {
      return;
    } catch (LocationException e) {
      return;
    }
  }
  
  /**
   * Modifie l'évaluation que l'utilisateur connecté avait déjà déposée sur un
   * film. Ne peut se faire que si l'utilisateur avait déjà évalué le film.
   *
   * @param film le film dont l'utilisateur modifie l'évaluation
   * @param eval la nouvelle évaluation qui remplace la précédente
   * @throws NonConnecteException si aucun utilisateur n'était connecté
   * @throws LocationException en cas d'erreur pour modifier l'évaluation,
   *         l'exception contiendra un message précisant le problème
   */
  public void modifierEvaluation(Film film, Evaluation eval) {
    try {
      utilisateur.modifierEvaluation(film, eval);
    } catch (NonConnecteException e) {
      return;
    } catch (LocationException e) {
      return;
    }
  }
  
  /**
   * Renvoie l'ensemble des films.
   *
   * @return l'ensemble des films ou <code>null</code> si aucun film n'existe
   */
  public Set<Film> ensembleFilms() {
    return films.ensembleFilms();
  }
  
  /**
   * Renvoie l'ensemble des acteurs.
   *
   * @return l'ensemble des acteurs ou <code>null</code> si aucun acteur
   *         n'existe
   */
  public Set<Artiste> ensembleActeurs() {
    return artistes.ensembleActeur();
  }
  
  /**
   * Renvoie l'ensemble des réalisateurs.
   *
   * @return l'ensemble des réalisateurs ou <code>null</code> si aucun
   *         réalisateur n'existe
   */
  public Set<Artiste> ensembleRealisateurs() {
    return artistes.ensembleRealisateur();
  }
  
  /**
   * Cherche un acteur à partir de son nom et son prénom.
   *
   * @param nom le nom de l'acteur
   * @param prenom le prénom de l'acteur
   * @return l'acteur s'il a été trouvé ou <code>null</code> sinon
   */
  public Artiste getActeur(String nom, String prenom) {
    return artistes.getActeur(nom, prenom);
  }
  
  /**
   * Cherche un réalisateur à partir de son nom et son prénom.
   *
   * @param nom le nom du réalisateur
   * @param prenom le prénom du réalisateur
   * @return le réalisateur s'il a été trouvé ou <code>null</code> sinon
   */
  public Artiste getRealisateur(String nom, String prenom) {
    return artistes.getRealisateur(nom, prenom);
  }
  
  /**
   * Cherche un film à partir de son titre.
   *
   * @param titre le titre du film
   * @return le film s'il a été trouvé ou <code>null</code> sinon
   */
  public Film getFilm(String titre) {
    return films.getFilm(titre);
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain réalisateur.
   *
   * @param realisateur le réalisateur
   * @return l'ensemble des films du réalisateur ou <code>null</code> si aucun
   *         film n'a été trouvé ou que le paramètre était invalide
   */
  public Set<Film> ensembleFilmsRealisateur(Artiste realisateur) {
    return artistes.ensembleFilmsRealisateur(realisateur);
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain réalisateur.
   *
   * @param nom le nom du réalisateur
   * @param prenom le prénom du réalisateur
   * @return l'ensemble des films du réalisateur ou <code>null</code> si aucun
   *         film n'a été trouvé ou que les paramètres étaient invalides
   */
  public Set<Film> ensembleFilmsRealisateur(String nom, String prenom) {
    return artistes
        .ensembleFilmsRealisateur(artistes.getRealisateur(nom, prenom));
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain acteur.
   *
   * @param acteur l'acteur
   * @return l'ensemble des films de l'acteur ou <code>null</code> si aucun film
   *         n'a été trouvé ou que le paramètre était invalide
   */
  public Set<Film> ensembleFilmsActeur(Artiste acteur) {
    return artistes.ensembleFilmsActeur(acteur);
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain acteur.
   *
   * @param nom le nom de l'acteur
   * @param prenom le prénom de l'acteur
   * @return l'ensemble des films de l'acteur ou <code>null</code> si aucun film
   *         n'a été trouvé ou que les paramètres étaient invalides
   */
  public Set<Film> ensembleFilmsActeur(String nom, String prenom) {
    return artistes.ensembleFilmsActeur(artistes.getActeur(nom, prenom));
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé
   */
  public Set<Film> ensembleFilmsGenre(Genre genre) {
    return films.ensembleFilmsGenre(genre);
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film (doit correspondre à un élément de
   *        l'énumération {@link location.Genre Genre})
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé ou que le genre était invalide
   * @see location.Genre
   */
  public Set<Film> ensembleFilmsGenre(String genre) {
    return films.ensembleFilmsGenre(genre);
  }
  
  /**
   * Renvoie l'ensemble des évaluations d'un film.
   *
   * @param film le film dont on veut les évaluations
   * @return toutes les évaluations d'un film ou <code>null</code> si aucune
   *         évaluation n'existe pour que le film ou que le film était invalide
   *         (valeur <code>null</code> par exemple)
   */
  public Set<Evaluation> ensembleEvaluationsFilm(Film film) {
    return films.ensembleEvaluationsFilm(film);
  }
  
  /**
   * Renvoie l'ensemble des évaluations d'un film.
   *
   * @param titre le titre du film dont on veut les évaluations
   * @return toutes les évaluations d'un film ou <code>null</code> si aucune
   *         évaluation n'existe pour le film ou que le titre du film était
   *         inconnu ou invalide (valeur <code>null</code> par exemple)
   */
  public Set<Evaluation> ensembleEvaluationsFilm(String titre) {
    return films.ensembleEvaluationsFilm(titre);
  }
  
  /**
   * Renvoie l'évaluation moyenne d'un film (la moyenne des notes de toutes les
   * évaluations sur le film).
   *
   * @param film le film dont on récupère l'évaluation moyenne
   * @return l'évaluation moyenne du film ou -1 si le film n'a aucune évaluation
   *         ou -2 en cas de film invalide (n'existant pas ou valeur
   *         <code>null</code>)
   */
  public double evaluationMoyenne(Film film) {
    return films.evaluationMoyenne(film);
  }
  
  /**
   * Renvoie l'évaluation moyenne d'un film (la moyenne des notes de toutes les
   * évaluations sur le film).
   *
   * @param titre le titre du film dont on récupère l'évaluation moyenne
   * @return l'évaluation moyenne du film ou -1 si le film n'a aucune évaluation
   *         ou -2 en cas de titre de film invalide (il n'existe pas de film
   *         avec ce titre ou valeur <code>null</code>)
   */
  public double evaluationMoyenne(String titre) {
    return films.evaluationMoyenne(titre);
  }
}
