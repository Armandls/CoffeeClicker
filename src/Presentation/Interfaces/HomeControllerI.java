package Presentation.Interfaces;

import Business.Exception.BusinessException;
import Persistance.Exception.PersistenceException;

public interface HomeControllerI {
    void createNewGame() throws PersistenceException;
    void resumeGameButton() throws PersistenceException;
    void resumeGame(int gameId) throws BusinessException;
    void swapPanel(String panel);
}
