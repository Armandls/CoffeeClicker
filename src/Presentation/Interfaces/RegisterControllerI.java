package Presentation.Interfaces;

import Business.Exception.BusinessException;
import Business.Exception.UserException.InvalidLoginEmailException;
import Business.Exception.UserException.UserException;
import Persistance.Exception.ConnectionErrorException;

public interface RegisterControllerI {
    void swapPanel(String panel);
    void clearForm(String form);
    String[] getRegisterInfo();
    void registerUser(String username, String email, String password, String confirmPassword) throws BusinessException, ConnectionErrorException;
    void adviceMessage(String message, String type, String title);
    boolean checkValidUsername(String username) throws ConnectionErrorException;
    void registerEnterValid(String username);
    boolean checkValidEmail(String email);
    boolean checkLowerCaseCaracter(String password);
    boolean checkUpperCaseCaracter(String password);
    boolean checkMinOneNumber(String password);
}
