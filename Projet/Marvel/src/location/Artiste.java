package location;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Classe representant un artiste avec des informations comme son nom, prenom.
 * et nationalite.
 */
public class Artiste {
  /**
   * le nom de l'Artistte.
   */
  private String nom;
  /**
   * le prenom de l'Artistte.
   */
  private String prenom;
  /**
   * la nationalite de l'artiste.
   */
  private String nationalite;
  /**
   * Ensemble de films dans lequels l'artiste a participé.
   */
  private Set<Film> films;

  /**
   * Constructeur de la classe Artiste.
   *
   * @param nom         Le nom de l'artiste.
   * @param prenom      Le prÃ©nom de l'artiste
   * @param nationalite La nationalitÃ© de l'artiste
   */
  public Artiste(String nom, String prenom, String nationalite) {
    this.nom = nom;
    this.prenom = prenom;
    this.nationalite = nationalite;
    this.films = new HashSet<>();
  }

  /**
   * Renvoie le nom de l'artiste.
   *
   * @Return le nom de l'artiste.
   */

  public String getNom() {
    return nom;
  }

  /**
   * modifie le nom de l'artiste.
   *
   * @param nom du nouveau parametre
   */
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * le renvoie le nom de l'artiste.
   *
   * @return le penom de l'artiste
   */
  public String getPrenom() {
    return prenom; // Retourne le prÃ©nom de l'artiste
  }

  /**
   * modifie le nom de l'artise.
   *
   *@param prenom renvoi le nouveau prenom de l'artiste.
   */
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  /**
   * le renvoie la nationalite de l'artiste.
   *
   * @return la nationalite de l'artiste
   */
  public String getNationalite() {
    return nationalite;
  }

  /**
   * modifie la nationalite de l'artiste.
   *
   * @param nationalite la nopuvellle nationalite
   */
  public void setNationalite(String nationalite) {
    this.nationalite = nationalite;
  }

  /**
   * Renvoi l'ensemble des films.
   *
   * @return l'ensemble de film
   */
  public Set<Film> getFilms() {
    return films;
  }

  /**
   * modifie l'ensemble des films.
   *
   * @param films le nouvel ensemble de films.
   */
  public void setFilms(Set<Film> films) {
    this.films = films;
  }

  /**
   * Methodes pour ajouter un film Ã l'ensemble des films de l'artiste.
   *
   * @param film Le film Ã ajouter
   * @return true si le film ete ajoute, false s'il est deja present
   */
  public boolean ajouterFilm(Film film) {
    return films.add(film);
  }

  /**
   * Redefinition de la mathode equals pour comparer deux artistes.
   *
   * @param obj L'objet Ã comparer
   * @return true si les artistes ont le meme nom,prenom et nationalitÃ©
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
      
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
     
    Artiste artiste = (Artiste) obj;
    return Objects.equals(nom, artiste.nom) && Objects.equals(prenom, artiste.prenom)
        && Objects.equals(nationalite, artiste.nationalite);
  }

  /**
   * Redefinition de la methode hashCode pour generer un code unique basee sur les
   * attributs.
   *
   * @return Le code de hachage
   */
  @Override
  public int hashCode() {
    return Objects.hash(nom, prenom, nationalite);
  }

  /**
   * methode toString pour afficher une description de l'artiste.
   *
   * @return Une chaine decrivant l'artiste
   */
  @Override
  public String toString() {
    return prenom + " " + nom + " - Nationalite : " + nationalite;
  }
}
