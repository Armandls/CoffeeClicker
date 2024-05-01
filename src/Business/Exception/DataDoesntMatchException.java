package Business.Exception;

import Business.Exception.UserException.UserException;

public class DataDoesntMatchException extends UserException {
    public DataDoesntMatchException(String message) {super(message);}
}
