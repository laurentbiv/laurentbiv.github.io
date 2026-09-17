package location;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe pour gerer les artistes, y compris les realisateurs et les acteurs.
 */
public class GestionArtiste {

  /**
   * Ensemble des realisateur.
   */
  public Set<Artiste> realisateurs;
  /**
   * Ensemble des acteurs.
   */
  public Set<Artiste> acteurs;
  /**
   * Ensemble general des artistes.
   */
  public Set<Artiste> artistes;

  /**
   * Constructeur de la classe GestionArtiste. Initialise les ensembles des
   * artistes, acteurs et realisateur
   */
  public GestionArtiste() {
    this.realisateurs = new HashSet<>();
    this.acteurs = new HashSet<>();
    this.artistes = new HashSet<>();
  }

  /**
   * Renvoie l'ensemble des acteurs.
   *
   * @return Ensemble des acteurs
   */
  public Set<Artiste> ensembleActeur() {
    return acteurs;
  }

  /**
   * Renvoie l'ensemble des realisateurs.
   *
   * @return Ensemble des realisateur.
   */
  public Set<Artiste> ensembleRealisateur() {
    return realisateurs;
  }

  /**
   * Renvoie un acteur secifique en fonction de son nom et prenom.
   *
   * @param nom    Le nom de l'acteur
   * 
   * @param prenom Le prenom de l'acteur
   * @return L'acteur trouvee ou null s'il n'existe pas
   */
  public Artiste getActeur(String nom, String prenom) {
    for (Artiste acteur : acteurs) {
      if (acteur.getNom().equals(nom) && acteur.getPrenom().equals(prenom)) {
        return acteur;
      }
    }
    return null;
  }

  /**
   * Renvoie un realisateur specifique en fonction de son nom et prÃ©nom.
   *
   * @param nom    Le nom du rÃ©alisateur
   * @param prenom Le prÃ©nom du rÃ©alisateur
   * @return Le rÃ©alisateur trouvÃ© ou null s'il n'existe pas
   */
  public Artiste getRealisateur(String nom, String prenom) {
    for (Artiste realisateur : realisateurs) {
      if (realisateur.getNom().equals(nom) && realisateur.getPrenom().equals(prenom)) {
        return realisateur;
      }
    }
    return null;
  }

  /**
   * Ajoute un nouvel artiste Ã l'ensemble gÃ©nÃ©ral.
   *
   * @param nom         Le nom de l'artiste
   * @param prenom      Le prÃ©nom de l'artiste
   * @param nationalite La nationalitÃ© de l'artiste
   * @return L'artiste crÃ©Ã©
   */
  public Artiste creerArtiste(String nom, String prenom, String nationalite) {
    if (nom != null && prenom != null && nationalite != null) {
      for (Artiste artiste : this.artistes) {
        if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
          return null;
        }
      }
      Artiste artiste1 = new Artiste(nom, prenom, nationalite);
      artistes.add(artiste1);
      return artiste1;
    } else {
      return null;
    }
  }

  /**
   * Supprime un artiste de l'ensemble general.
   *
   * @param artiste L'artiste Ã supprimer
   * @return true si l'artiste a Ã©tÃ© supprimÃ©, false sinon
   */
  public boolean supprimerArtiste(Artiste artiste) {
    if (this.acteurs.contains(artiste) || this.realisateurs.contains(artiste)) {
      return false;
    } else {
      return artistes.remove(artiste);
    }
  }

  /**
   * Renvoie un artiste spÃ©cifique en fonction de son nom et prÃ©nom.
   *
   * @param nom    Le nom de l'artiste
   * @param prenom Le prÃ©nom de l'artiste
   * @return L'artiste trouvÃ© ou null s'il n'existe pas
   */
  public Artiste getArtiste(String nom, String prenom) {
    for (Artiste artiste : artistes) {
      if (artiste.getNom().equals(nom) && artiste.getPrenom().equals(prenom)) {
        return artiste;
      }
    }
    return null;
  }

  /**
   * Renvoie tous les films d'un acteur spÃ©cifique.
   *
   * @param acteur L'acteur dont on veut les films
   * @return Ensemble des films de cet acteur ou null s'il n'existe pas
   */
  public Set<Film> ensembleFilmsActeur(Artiste acteur) {
    if (acteurs.contains(acteur)) {
      return acteur.getFilms();
    }
    return null;
  }

  /**
   * Renvoie tous les films d'un Realisateur specifique.
   *
   * @param realisateur Le realisateur dont on veut les films
   * @return Ensemble des films de ce rÃ©alisateur ou null s'il n'existe pas
   */
  public Set<Film> ensembleFilmsRealisateur(Artiste realisateur) {
    if (realisateurs.contains(realisateur)) {
      return realisateur.getFilms();
    }
    return null;
  }
}
