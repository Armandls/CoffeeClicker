import Business.Exception.BusinessException;
import Business.GameManager;
import Business.GeneratorManager;
import Business.UserManager;
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

        GeneratorManager generatorManager = null;
        generatorManager = new GeneratorManager(generatorDAO);


        // Create the different managers and the controller's instance
        GameManager gameManager = new GameManager(new SQLGameDAO(), generatorManager);

        UserManager userManager = new UserManager(userDAO);

        // Create the controller and run it
        try {
            MainController controller = new MainController(generatorManager, userManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}