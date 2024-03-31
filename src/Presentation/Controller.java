package Presentation;

import Business.GameManager;
import Business.GeneratorManager;
import Business.ImprovementManager;
import Business.UserManager;

/*Class to manage the interactions between the user interface (UI, the View) and the Manager classes*/
public class Controller {

    private GameManager gameManager;
    private GeneratorManager generatorManager;
    private ImprovementManager improvementManager;
    private UserManager userManager;

    public Controller(GameManager gameManager, GeneratorManager generatorManager, ImprovementManager improvementManager, UserManager userManager) {
        this.gameManager = gameManager;
        this.generatorManager = generatorManager;
        this.improvementManager = improvementManager;
        this.userManager = userManager;
    }

    public void run() {
    }

}
