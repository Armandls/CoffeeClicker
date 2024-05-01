package Presentation.Controllers;

import Presentation.Views.GeneratorsView;
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
            case "buy1":
                System.out.println("buy1");
                break;
            case "buy2":
                System.out.println("buy2");
                break;
            case "buy3":
                System.out.println("buy3");
                break;
            case "buyI1":
                System.out.println("buyI1");
                break;
            case "buyI2":
                System.out.println("buyI2");
                break;
            case "buyI3":
                System.out.println("buyI3");
                break;
        }
    }

    public void addView(StoresView view) {
        this.view = view;
        view.swapPanel("generators");
    }
}