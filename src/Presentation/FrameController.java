package Presentation;

public interface FrameController {

    void swapPanel(String panelName);
    void initializeGame(int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp);
    void addHoverPanel(JHoverPanel panel);

    void removeHoverPanel(String name);
    void swapStore(String name);
}
