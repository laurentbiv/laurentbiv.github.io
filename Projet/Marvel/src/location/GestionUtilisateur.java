package location;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Gestion de tous les utilisateurs de l'application.
 *
 * @author liseauffret
 *
 */
public class GestionUtilisateur {
  /**
   * Liste de tous les utilisateurs de l'application.
   */
  Set<Utilisateur> utilisateurs;

  /**
   * Constructeur de la class GestionUtilisateur.
   * 
   */
  public GestionUtilisateur() {
    super();
    this.utilisateurs = new HashSet<Utilisateur>();
  }

  /**
   * Récupère la liste des utilisateurs.
   *
   * @return la liste des utilisateurs
   */
  public Set<Utilisateur> getUtilisateurs() {
    return utilisateurs;
  }

  /**
   * Modifie la liste des utilisateurs.
   *
   * @param utilisateurs la liste des utilisateurs
   */
  public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
    this.utilisateurs = utilisateurs;
  }

  /**
   * Inscription d'un utilisateur. Le pseudo choisi ne doit pas déjà exister parmi
   * les utilisateurs déjà inscrits.
   *
   * @param pseudo le pseudo (unique) de l'utilisateur
   * @param mdp    le mot de passe de l'utilisateur (ne pas doit pas être vide ou
   *               <code>null</code>)
   * @param info   les informations personnelles sur l'utilisateur
   * @return un code précisant le résultat de l'inscription : 0 si l'inscription
   *         s'est bien déroulée, 1 si le pseudo était déjà utilisé, 2 si le
   *         pseudo ou le mot de passe était vide, 3 si les informations
   *         personnelles ne sont pas bien précisées
   */
  public int inscription(String pseudo, String mdp, InformationPersonnelle info) {
    if (pseudo == null || mdp == null) {
      return 2;
    }
    if (info.getAdresse() == null || info.getAge() == 0 || info.getNom() == null 
        || info.getPrenom() == null) {
      return 3;
    }
    for (Iterator<Utilisateur> it = utilisateurs.iterator(); it.hasNext();) {
      Utilisateur u = it.next();
      if (u.getPseudo().equals(pseudo)) {
        return 1;
      }
    }
    Utilisateur newU = new Utilisateur(pseudo, mdp, info);
    this.utilisateurs.add(newU);
    return 0;
  }

  /**
   * Connexion de l'utilisateur. Une fois connecté, l'utilisateur pourra accéder
   * aux services de location et déposer des commentaires sur les films qu'il a
   * loués.
   *
   * @param pseudo le pseudo de l'utilisateur
   * @param mdp    le mot de passe de l'utilsateur
   * @return <code>true</code> si la connexion s'est bien déroulée,
   *         <code>false</code> en cas de couple pseudo/mot de passe invalide
   */
  public boolean connexion(String pseudo, String mdp) {
    for (Iterator<Utilisateur> it = utilisateurs.iterator(); it.hasNext();) {
      Utilisateur u = it.next();
      if (u.getPseudo().equals(pseudo) && u.getMotdepasse().equals(mdp)) {
        u.setEtat(true);
        return true;
      }
    }
    return false;
  }
}
