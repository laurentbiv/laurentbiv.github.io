package location;

/**
 * Exception levée quand il y a un problème lors de la location d'un film par un
 * utilisateur.
 *
 * @author Eric Cariou
 */
public class LocationException extends Exception {

  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = -3365565475174636290L;

  /**
   * Constructeur de l'exception avec un message.
   *
   * @param message message qui explique la raison de l'exception
   */

  public LocationException(String message) {
    super(message);
  }

}
