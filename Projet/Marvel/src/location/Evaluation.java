package location;

/**
 * Informations d'une évaluation : note, commentaire (facultatif), auteur, film.
 *
 * @author liseauffret
 *
 */
public class Evaluation {
  /**
   * Note de l'évaluation.
   */
  int note;
  /**
   * Commentaire sur l'évaluation.
   */
  String commentaire;
  /**
   * Utilisateur qui attribut cette évaluation.
   */
  Utilisateur auteur;
  /**
   * Film auquel l'évaluation fait référence.
   */
  Film film;

  /**
   * Constructeur de la classe.
   *
   * @param note        la note du film
   * @param commentaire le commentaire sur le film
   * @param auteur      l'auteur de l'évaluation
   * @param film        le film auquel l'évaluation fait référence
   */
  public Evaluation(int note, String commentaire, Utilisateur auteur, Film film) {
    super();
    if (note >= 0 && note <= 5) {
      this.note = note;
    }
    this.commentaire = commentaire;
    this.auteur = auteur;
    this.film = film;
  }

  /**
   * Constructeur de la classe sans commentaire.
   *
   * @param note   la note du film
   * @param auteur l'auteur de l'évaluation
   * @param film   le film auquel l'évaluation fait référence
   */
  public Evaluation(int note, Utilisateur auteur, Film film) {
    super();
    if (note >= 0 && note <= 5) {
      this.note = note;
    }
    this.commentaire = null;
    this.auteur = auteur;
    this.film = film;
  }

  /**
   * Renvoie la note de l'évaluation.
   *
   * @return la note de l'évaluation
   */
  public int getNote() {
    return note;
  }

  /**
   * Modifie la note de l'évaluation.
   *
   * @param note la note de l'évaluation
   */
  public void setNote(int note) {
    if (note >= 0 && note <= 5) {
      this.note = note;
    }

  }

  /**
   * Renvoie le commentaire de l'évaluation.
   *
   * @return le commentaire de l'évaluation
   */
  public String getCommentaire() {
    return commentaire;
  }

  /**
   * Modifie le commentaire de l'évaluation.
   *
   * @param commentaire le commentaire de l'évaluation
   */
  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }

  /**
   * Renvoie l'auteur de l'évaluation.
   *
   * @return l'auteur de l'évaluation
   */
  public Utilisateur getAuteur() {
    return auteur;
  }

  /**
   * Modifie l'auteur de l'évaluation.
   *
   * @param auteur l'auteur de l'évaluation
   */
  public void setAuteur(Utilisateur auteur) {
    this.auteur = auteur;
  }

  /**
   * Renvoie le film auquel l'évaluation fait référence.
   *
   * @return le film auquel l'évaluation fait référence
   */
  public Film getFilm() {
    return film;
  }

  /**
   * Modifie le film auquel l'évaluation fait référence.
   *
   * @param film le film auquel l'évaluation fait référence
   */
  public void setFilm(Film film) {
    this.film = film;
  }

  @Override
  public String toString() {
    return "Evaluation [note=" + note + ", commentaire=" + commentaire 
        + ", auteur=" + auteur + ", film=" + film + "]";
  }

}
