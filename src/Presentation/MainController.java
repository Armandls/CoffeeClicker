package Presentation;

import Business.GameManager;
import Business.GeneratorManager;
import Business.ImprovementManager;
import Business.UserManager;
import Presentation.Controllers.LoginController;
import Presentation.Controllers.RegisterController;
import Presentation.Views.LoginView;
import Presentation.Views.MyView;
import Presentation.Views.RegisterView;

import java.util.Hashtable;

/*Class to manage the interactions between the user interface (UI, the View) and the Manager classes*/
public class MainController implements FrameController {

    private GameManager gameManager;
    private GeneratorManager generatorManager;
    private ImprovementManager improvementManager;
    private UserManager userManager;
    private MainFrame mainFrame;
    private MyView currentView;
    private Hashtable<String, MyView> views;

    public MainController(GameManager gameManager, GeneratorManager generatorManager, ImprovementManager improvementManager, UserManager userManager) {
        this.gameManager = gameManager;
        this.generatorManager = generatorManager;
        this.improvementManager = improvementManager;
        this.userManager = userManager;
        init();
    }

    @Override
    public void swapPanel(String panelName) {
        mainFrame.showPanel(panelName);
        currentView.stop();
        currentView = views.get(panelName);
        currentView.start();
    }

    public void run() {

    }

    void init() {
        mainFrame = new MainFrame();
        views = new Hashtable<>();

        LoginController loginController = new LoginController(this);
        LoginView loginView = new LoginView(loginController);
        loginController.setView(loginView);

        RegisterController registerController = new RegisterController(this);
        RegisterView registerView = new RegisterView(registerController);
        registerController.setView(registerView);

        mainFrame.addPanel(loginView, "login");
        mainFrame.addPanel(registerView, "register");
        mainFrame.setVisible(true);

        views.put("login", loginView);
        views.put("register", registerView);

        currentView = loginView;
    }
}
