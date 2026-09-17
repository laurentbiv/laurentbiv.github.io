package location;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Les informations concernant 1 film : Titre, annee de sortie, l'age minimum,
 * Les genres auxquelles il appartient, le rélaisateur, les acteurs présent dans
 * le film l'état du film, toutes ses evaluations et la moenne des évaluations.
 *
 * @author Andias Ames
 */


public class Film {
  /**
   * Titre du film.
   */
  String titre;
  /**
   * Année de sortie du film.
   */
  int annee;
  /**
   * Age minimum requis pour regarder le film.
   */
  int ageMin;
  /**
   * L'ensemble des genres auxquelle le filmm appartient.
   */
  Set<Genre> genresFilm;
  /**
   * Le realisateur du film.
   */
  Artiste realisateurFilm;
  /**
   * L'ensembles des acteurs jouant dans le film.
   */
  Set<Artiste> acteursFilm;
  /**
   * L'état du film (louable ou pas ).
   */
  boolean etat;
  /**
   * L'ensemble des évaluations donnéé par les utilisateur sur le film.
   */
  Set<Evaluation> evaluationsFilm;
  /**
   * La moyenne des évaluations données sur le film.
   */
  double moyenne;
  
  /**
   * Le chemin de l'affiche du film.
   */
  String affiche;
  
  /**
   * Renvoie le titre du film.
   *
   * @return le titre du film.
   */
  public String getTitre() {
    return titre;
  }
  
  /**
   * Renvoie l'année de sortie du film.
   *
   * @return l'année de sortie du film.
   */
  public int getAnnee() {
    return annee;
  }
  
  /**
   * Renvoie l'age minimum requis pour regarder le film.
   *
   * @return l'age minimum requis pour regarder le film.
   */
  public int getAgemin() {
    return ageMin;
  }
  
  /**
   * Renvoie la liste des genres du films.
   *
   * @return la liste des genres du films.
   */
  public Set<Genre> getGenresfilm() {
    return genresFilm;
  }
  
  /**
   * Renvoie le réalisateur du film.
   *
   * @return le réalisateur du film.
   */
  public Artiste getRealisateurfilm() {
    return realisateurFilm;
  }
  
  /**
   * Renvoie la liste des acteurs présent dans le film.
   *
   * @return la liste des acteurs présent dans le film.
   */
  public Set<Artiste> getActeursfilm() {
    return acteursFilm;
  }
  
  /**
   * Renvoie l'état du film.
   *
   * @return l'état du film.
   */
  public boolean getEtat() {
    return etat;
  }
  
  /**
   * Renvoie la liste des évaluations faites sur le film.
   *
   * @return la liste des évaluations faites sur le film.
   */
  public Set<Evaluation> getEvaluationsfilm() {
    return evaluationsFilm;
  }
  
  /**
   * Renvoie la moyenne des evaluations faites sur le film.
   *
   * @return la moyenne des evaluations faites sur le film.
   */
  public double getMoyenne() {
    return moyenne;
  }
  
  /**
   * Modifie le titre du film.
   *
   * @param titre le nouveau titre du film (doit etre different de null).
   */
  public void setTitre(String titre) {
    this.titre = titre;
  }
  
  /**
   * Modifie l'année de sortie du film.
   *
   * @param annee la nouvelle année de sortie du film définis (année courante
   *        par defaut).
   */
  public void setAnnee(int annee) {
    if (annee > 0) {
      this.annee = annee;
    }
  }
  
  /**
   * Modifie l'age minimum requis pour voir le film.
   *
   * @param agemin le nouvelle age minimum ( doit etre supérieur à 0).
   */
  public void setAgemin(int agemin) {
    if (agemin > 0) {
      this.ageMin = agemin;
    }
  }
  
  /**
   * Modifie la liste des genres du film.
   *
   * @param genresfilm nouvelle liste des genres du film mise a jour.
   */
  public void setGenresfilm(Set<Genre> genresfilm) {
    this.genresFilm = genresfilm;
  }
  
  /**
   * Modifie le réalisateur du film.
   *
   * @param realisateurfilm le nouveau réalisateur du film.
   */
  public void setRealisateurfilm(Artiste realisateurfilm) {
    this.realisateurFilm = realisateurfilm;
  }
  
  /**
   * Modifie la liste des acteurs du film.
   *
   * @param acteursfilm nouvelle liste des acteurs du film mise a jour.
   */
  public void setActeursfilm(Set<Artiste> acteursfilm) {
    this.acteursFilm = acteursfilm;
  }
  
  /**
   * Modifie les évaluations d'un film.
   *
   * @param evaluationsfilm nouvelle liste des évaluations d'un film mise à
   *        jour.
   */
  public void setEvaluationsfilm(Set<Evaluation> evaluationsfilm) {
    this.evaluationsFilm = evaluationsfilm;
  }
  
  /**
   * Modife l'état courant du film.
   *
   * @param etat nouvelle état du film (doit etre 1 ou 0 (Ouvert ou Fermé)).
   */
  public void setEtat(boolean etat) {
    this.etat = etat;
  }
  
  /**
   * Calcul la moyenne d'un film et le met a jour.
   *
   */
  public void setMoyenne() {
    double moy = 0.0;
    for (Evaluation evaluation : this.evaluationsFilm) {
      moy += evaluation.getNote();
    }
    this.moyenne = moy / this.evaluationsFilm.size();
  }
  
  /**
   * Renvoie le chemin de l'affiche du film.
   *
   * @return le chemin de l'affiche du film.
   */
  public String getAffiche() {
    return affiche;
  }
  
  /**
   * Modifie l'affiche du film.
   *
   * @param affiche le nouveau chemin de l'affiche du film.
   */
  public void setAffiche(String affiche) {
    this.affiche = affiche;
  }
  
  /**
   * Crée un film avec tout ses éléments.
   *
   * @param titre titre du film.
   * @param annee année de sortie du film.
   * @param agemin age minimum requis pour regarder le film.
   * @param realisateurfilm le réalisateur du film.
   */
  public Film(String titre, int annee, int agemin, Artiste realisateurfilm) {
    super();
    this.titre = titre;
    this.annee = annee;
    this.ageMin = agemin;
    this.genresFilm = new HashSet<Genre>();
    this.realisateurFilm = realisateurfilm;
    this.acteursFilm = new HashSet<Artiste>();
    this.etat = false;
    this.evaluationsFilm = new HashSet<Evaluation>();
    this.moyenne = 0.0;
    this.affiche = null;
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
    Film other = (Film) obj;
    return Objects.equals(titre, other.titre);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(titre);
  }
  
  
  
  @Override
  public String toString() {
    return "Le film " + titre + ", réalisé par " + realisateurFilm
        + " et sortie en " + annee + "est un film de genres : " + genresFilm
        + ". Actuellement le film est à l'état " + etat
        + " et il visible par tout les utlisateurs de plus de " + ageMin
        + ". Joués par les acteurs de renoms tels que : " + acteursFilm
        + ", ce film cumule une moyenne de " + moyenne
        + ". La liste des évaluations du films ci-dessous : " + evaluationsFilm
        + ".";
  }
  
  /**
   * Ajoute une evaluation au film.
   *
   * @param eval Une evaluation.
   * 
   * @return true si l'evaluation a été ajouté, false sinon dans le cas ou le
   *         l'eval serais null.
   */
  public boolean addEval(Evaluation eval) {
    if (eval == null) {
      return false;
    }
    this.evaluationsFilm.add(eval);
    this.setMoyenne();
    return true;
    
  }
  
}
