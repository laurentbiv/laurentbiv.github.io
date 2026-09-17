package location;

/**
 * Exception levée quand il y a un problème avec le compte d'un utilisateur ou
 * sa connexion.
 *
 * @author Eric Cariou
 */
public class NonConnecteException extends Exception {

  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = -2876441299971092712L;

  /**
   * Constructeur de l'exception avec un message.
   *
   * @param message message qui explique la raison de l'exception
   */

  public NonConnecteException(String message) {
    super(message);
  }

}
