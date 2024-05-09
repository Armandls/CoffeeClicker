import Business.Exception.BusinessException;
import Business.GameManager;
import Business.GeneratorManager;
import Business.ImprovementManager;
import Business.UserManager;
import Persistance.DAO.GameDAO;
import Persistance.DAO.GeneratorDAO;
import Persistance.SQL.SQLGameDAO;
import Persistance.SQL.SQLGenerator;
import Persistance.SQL.SQLUserDAO;
import Persistance.DAO.UserDAO;
import Presentation.MainController;

public class Main {
    public static void main(String[] args) {
        // Create the different DAOs
        UserDAO userDAO = new SQLUserDAO();
        GeneratorDAO generatorDAO = new SQLGenerator();

        
        // Create the different managers and the controller's instance
        GameManager gameManager = new GameManager(new SQLGameDAO());

        GeneratorManager generatorManager = null;
        try {
            generatorManager = new GeneratorManager(new GameManager(new SQLGameDAO()), generatorDAO);
        } catch (BusinessException e) {
            // Handle the exception
            System.err.println("Failed to create GeneratorManager: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for detailed error information
        }
        ImprovementManager improvementManager = new ImprovementManager();
        UserManager userManager = new UserManager(userDAO);

        // Create the controller and run it
        try {
            MainController controller = new MainController(gameManager, generatorManager, improvementManager, userManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}