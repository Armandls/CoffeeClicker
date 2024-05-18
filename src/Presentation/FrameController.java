package Presentation;

import Business.Exception.BusinessException;

public interface FrameController {

    void swapPanel(String panelName);
    void initializeGame(int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp) throws BusinessException;
    void addHoverPanel(JHoverPanel panel);

    void removeHoverPanel(String name);
    void swapStore(String name);
}
