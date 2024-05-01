package Business.Exception.UserException;

import Business.Exception.BusinessException;

public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(String message) {super(message);}
}
