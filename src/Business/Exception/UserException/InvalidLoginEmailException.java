package Business.Exception.UserException;

/**
 * S'utilitza per avisar si el format del email introduit no es correcte. Es diferent de l'exepcio que avisa si no troba l'usuari.
 */
public class InvalidLoginEmailException extends UserException {
    public InvalidLoginEmailException(String message){super(message);}
}
