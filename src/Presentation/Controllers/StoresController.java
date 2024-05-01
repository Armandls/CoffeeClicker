package Presentation.Controllers;
import Presentation.Views.StoresView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoresController implements ActionListener {

    private StoresView view;

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "back":
                System.out.println("back");
                break;
            case "upgrades":
                System.out.println("upgrades");
                view.swapPanel("upgrades");
                break;
            case "generators":
                System.out.println("generators");
                view.swapPanel("generators");
                break;
        }
    }

    public void addView(StoresView view) {
        this.view = view;
        view.swapPanel("generators");
    }
}