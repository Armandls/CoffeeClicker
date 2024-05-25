package Presentation.Interfaces;

import Business.Exception.UserException.InvalidLoginEmailException;
import Business.Exception.UserException.InvalidPasswordException;
import Business.Exception.UserException.UserException;
import Business.Exception.UserException.UserNotFoundException;

public interface LoginControllerI {
    void swapPanel(String name);
    void clearForm(String name);
    String[] getLoginInfo();
    void loginEnterValid(String email);
    void loginUser(String email, String password) throws UserException;
    void adviceMessage(String message, String type, String title);

}
