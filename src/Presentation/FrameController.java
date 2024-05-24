package Presentation;

import Business.Exception.BusinessException;

public interface FrameController {

    void swapPanel(String panelName);
    void initializeGame(int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp);

    void swapStore(String name);

    void updateImprovement (String improvement);

    void buyGenerator(String generator);
}
