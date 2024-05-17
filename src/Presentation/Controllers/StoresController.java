package Presentation.Controllers;
import Presentation.MainController;
import Presentation.Views.StoresView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoresController implements ActionListener {
    private MainController mainController;

    public StoresController (MainController mainController) {
        this.mainController = mainController;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().contains("generatorsBuy")) {
            System.out.println(e.getActionCommand());
            mainController.buyGenerator(e.getActionCommand().substring(14));
        }
        else if (e.getActionCommand().contains("improvementsBuy")) {
            String improvement = e.getActionCommand().substring(e.getActionCommand().indexOf("improvementsBuy:") + "improvementsBuy:".length());
            mainController.updateImprovement(improvement);
        }

        switch(e.getActionCommand()) {
            case "back":
                System.out.println("back");
                break;
            case "upgrades":
                System.out.println("upgrades");
                mainController.swapStore("upgrades");
                break;
            case "generators":
                System.out.println("generators");
                mainController.swapStore("generators");
                break;
        }
    }
}