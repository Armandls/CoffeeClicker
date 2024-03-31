import Business.GameManager;
import Business.GeneratorManager;
import Business.ImprovementManager;
import Business.UserManager;
import Persistance.SQL.SQLUserDAO;
import Persistance.DAO.UserDAO;
import Presentation.Controller;

public class Main {
    public static void main(String[] args) {

        // Create the different DAOs
        UserDAO userDAO = new SQLUserDAO();

        
        // Create the different managers and the controller's instance
        GameManager gameManager = new GameManager();
        GeneratorManager generatorManager = new GeneratorManager();
        ImprovementManager improvementManager = new ImprovementManager();
        UserManager userManager = new UserManager(userDAO);

        // Create the controller and run it
        Controller controller = new Controller(gameManager, generatorManager, improvementManager, userManager);
        controller.run();
    }
}