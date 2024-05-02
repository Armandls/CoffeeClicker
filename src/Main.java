import Business.Exception.BusinessException;
import Business.GameManager;
import Business.GeneratorManager;
import Business.ImprovementManager;
import Business.UserManager;
import Persistance.DAO.GameDAO;
import Persistance.SQL.SQLGameDAO;
import Persistance.SQL.SQLUserDAO;
import Persistance.DAO.UserDAO;
import Presentation.MainController;

public class Main {
    public static void main(String[] args) {
        GeneratorManager generatorManager = new GeneratorManager();;
        // Create the different DAOs
        UserDAO userDAO = new SQLUserDAO();

        
        // Create the different managers and the controller's instance
        GameManager gameManager = new GameManager(new SQLGameDAO());
        try {
            generatorManager = new GeneratorManager(new GameManager(new SQLGameDAO()));
        } catch (BusinessException e) {
            //Gestionar la excepció que seria d'una mala conexio amb la base de dades.


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