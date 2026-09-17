package location;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Services de gestion des films, du point de vue de l'administrateur de
 * l'application.
 *
 * @author Andias Ames
 */
public class GestionFilm {
  
  /**
   * Liste de tous les films de l'application.
   */
  
  private Set<Film> films;
  
  /**
   * Creer un gestionnaire de film avec sa liste de film.
   */
  public GestionFilm() {
    super();
    films = new HashSet<Film>();
  }
  
  /**
   * La liste des films du gestionnaire.
   *
   * @return la liste des films du gestionnaire.
   */
  public Set<Film> getFilms() {
    return films;
  }
  
  /**
   * Renvoie l'ensemble des films.
   *
   * @return l'ensemble des films ou <code>null</code> si aucun film n'existe
   */
  public Set<Film> ensembleFilms() {
    if (this.films.isEmpty()) {
      return null;
    } else {
      return this.films;
    }
  }
  
  /**
   * Cherche un film à partir de son titre.
   *
   * @param titre le titre du film
   * @return le film s'il a été trouvé ou <code>null</code> sinon
   */
  public Film getFilm(String titre) {
    for (Film film : this.films) {
      if (film.getTitre().equalsIgnoreCase(titre)) {
        return film;
      }
    }
    return null;
  }
  
  /**
   * Renvoie l'ensemble des films d'un certain genre.
   *
   * @param genre le genre du film
   * @return l'ensemble des films du genre ou <code>null</code> si aucun film
   *         n'a été trouvé
   */
  public Set<Film> ensembleFilmsGenre(Genre genre) {
    Set<Film> filmDuGenre = new HashSet<Film>();
    // Parcours des films dans films
    for (Film film : this.films) {
      
      if (film.getGenresfilm().contains(genre)) {
        filmDuGenre.add(film);
      }
    }
    
    if (filmDuGenre.isEmpty()) {
      return null;
    } else {
      return filmDuGenre;
    }
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
    Genre genreRecherche = null;
    for (Genre g : Genre.values()) {
      if (g.name().equalsIgnoreCase(genre)) {
        genreRecherche = g;
      }
    }
    
    if (genreRecherche == null) {
      return null;
    } else {
      return ensembleFilmsGenre(genreRecherche);
    }
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
    boolean filmExiste = false;
    for (Film f : this.films) {
      if (f.getTitre().equals(film.getTitre())) {
        filmExiste = true;
        break;
      }
    }
    if (filmExiste && !(film.getEvaluationsfilm().isEmpty())) {
      return film.getEvaluationsfilm();
    } else {
      return null;
    }
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
    if (titre == null) {
      return null;
    }
    // Parcours des films
    for (Film film : this.films) {
      if (film.getTitre().equalsIgnoreCase(titre)) {
        if (film.getEvaluationsfilm() != null
            && !film.getEvaluationsfilm().isEmpty()) {
          return film.getEvaluationsfilm();
        } else {
          return null;
        }
      }
    }
    return null;
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
    boolean filmExiste = false;
    for (Film f : this.films) {
      if (f.getTitre().equals(film.getTitre())) {
        filmExiste = true;
        break;
      }
    }
    if (filmExiste) {
      if (!film.getEvaluationsfilm().isEmpty()) {
        return film.getMoyenne();
      } else {
        return -1.0;
      }
    } else {
      return -2.0;
    }
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
    if (titre == null) {
      return -2.0;
    }
    // Parcours des films
    for (Film film : this.films) {
      if (film.getTitre().equalsIgnoreCase(titre)) {
        if (film.getEvaluationsfilm() != null
            && !film.getEvaluationsfilm().isEmpty()) {
          return film.getMoyenne();
        } else {
          return -1.0;
        }
      }
    }
    return -2.0;
  }
  
  /**
   * Création d'un nouveau film. Il ne doit pas déjà exister un film avec le
   * même titre.
   *
   * @param titre le titre du film (chaine non vide)
   * @param realisateur le réalisateur du film (non <code>null</code>)
   * @param annee l'année de réalisation du film
   * @param ageLimite l'âge minimum pour pouvoir regarder le film (0 si pas
   *        d'âge limite)
   * @return le film créé ou <code>null</code> en cas de problème (il existe
   *         déjà un film au même titre ou des paramètres n'étaient pas valides)
   */
  public Film creerFilm(String titre, Artiste realisateur, int annee,
      int ageLimite) {
    
    // Verification des paramètres
    if (titre == null || titre.trim().isEmpty()) {
      return null;
    }
    if (realisateur == null) {
      return null;
    }
    if (annee <= 0) {
      return null;
    }
    
    if (getFilm(titre) != null) {
      return null;
    }
    
    Film filmAcreer = new Film(titre, annee, ageLimite, realisateur);
    realisateur.ajouterFilm(filmAcreer);
    this.films.add(filmAcreer);
    return filmAcreer;
  }
  
  /**
   * Ajoute des acteurs à un film.
   *
   * @param film le film à modifier
   * @param acteurs la liste des acteurs à ajouter. Si des acteurs de la liste
   *        sont déjà associés au film, ils ne sont pas ajoutés en double.
   * @return <code>true</code> si au moins un acteur de la liste a été ajouté
   *         aux acteurs du film, <code>false</code> sinon
   */
  public boolean ajouterActeurs(Film film, Artiste... acteurs) {
    boolean filmExiste = false;
    for (Film f : this.films) {
      if (f.getTitre().equals(film.getTitre())) {
        filmExiste = true;
        break;
      }
    }
    
    if (!filmExiste) {
      return false;
    }
    int tailleActeurs = film.getActeursfilm().size();
    for (Artiste artiste : acteurs) {
      // Ajouter l'artiste en tant qu'acteur si il n'est pas déjà présent
      if (!film.getActeursfilm().contains(artiste)) {
        film.getActeursfilm().add(artiste);
      }
      
      // Ajouter le film à la liste des films de l'artiste si ce n'est pas déjà
      // fait
      if (!artiste.getFilms().contains(film)) {
        artiste.getFilms().add(film);
      }
    }
    return tailleActeurs != film.getActeursfilm().size();
  }
  
  /**
   * Ajoute des genres à un film.
   *
   * @param film le film à modifier
   * @param genres la liste des genres à ajouter. Si des genres de la liste sont
   *        déjà associés au film, ils ne sont pas ajoutés en double.
   * @return <code>true</code> si au moins un genre de la liste a été ajouté aux
   *         genres du film, <code>false</code> sinon
   */
  public boolean ajouterGenres(Film film, Genre... genres) {

    // Vérifier si le film existe par titre
    boolean filmExiste = false;
    for (Film f : this.films) {
      if (f.getTitre().equals(film.getTitre())) {
        filmExiste = true;
      }
    }
    
    if (!filmExiste) {
      return false;
    }
    int tailleGenres = film.getGenresfilm().size();
    for (Genre g : genres) {
      if (!film.getGenresfilm().contains(g)) {
        // Peut etre verifier si le genre exitse dans l'enumeration Genre
        film.getGenresfilm().add(g);
      }
    }
    return tailleGenres != film.getGenresfilm().size();
  }
  
  /**
   * Supprime un film de l'ensemble des films.
   *
   * @param film le film à supprimer
   * @return <code>true</code> si le film a été supprimé ou <code>false</code>
   *         en cas de problème (le film n'existait pas ou le paramètre était
   *         égal à <code>null</code>)
   */
  public boolean supprimerFilm(Film film) {
    Film filmSupprimer = null;
    for (Film f : this.films) {
      if (f.equals(film)) {
        filmSupprimer = f;
        break;
      }
    }
    
    if (filmSupprimer != null) {
      this.films.remove(filmSupprimer);
      return true;
    } else {
      return false;
    }
  }
  
  
  /**
   * Ouvre un film à la location. Ne fait rien si le film était déjà ouvert à la
   * location.
   *
   * @param film le film à ouvrir à la location
   * @return <code>true</code> si le film est ouvert à la location,
   *         <code>false</code> en cas de problème (le film n'a pas été trouvé
   *         ou valeur <code>null</code>)
   */
  public boolean ouvrirLocation(Film film) {
    if (film.getEtat() == false) {
      if (film != null && this.films.contains(film)) {
        film.setEtat(true);
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }
  
  /**
   * Ferme la location d'un film. Ne fait rien si le film n'était pas ouvert à
   * la location.
   *
   * @param film le film dont il faut fermer la location
   * @return <code>true</code> si le film est fermé à la location,
   *         <code>false</code> en cas de problème (le film n'a pas été trouvé
   *         ou valeur <code>null</code>)
   */
  public boolean fermerLocation(Film film) {
    if (film.getEtat() == true) {
      if (film != null && this.films.contains(film)) {
        film.setEtat(false);
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }
  
  /**
   * Ajoute une affiche à un film. Si le film avait déjà une affiche, elle est
   * remplacée par la nouvelle.
   *
   * @param film le film auquel ajouter une affiche
   * @param file le chemin du fichier qui contient l'image de l'affiche (au
   *        format JPG, PNG ...)
   * @return <code>true</code> si l'affiche a été modifiée (le format et la
   *         taille étaient valides)
   * @throws IOException en cas d'erreur de lecture du fichier
   */
  public boolean ajouterAffiche(Film film, String file) throws IOException {
    boolean filmExiste = false;
    for (Film f : this.films) {
      if (f.getTitre().equals(film.getTitre())) {
        filmExiste = true;
      }
    }
    if (film != null && filmExiste == true) {
      // Verification si le fichier existe
      File imageFile = new File(file);
      if (imageFile.exists()) {
        throw new IOException("Erreur de lecture du fichier");
      }
      if (!imageFile.isFile()) {
        System.out.println(
            "Le fichier spécifié n'existe pas ou n'est pas un fichier valide.");
      }
      film.setAffiche(file);
      return true;
      
    } else {
      return false;
    }
  }
}
