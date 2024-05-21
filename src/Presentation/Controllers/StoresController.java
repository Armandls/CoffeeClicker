package Presentation.Controllers;
import Presentation.MainController;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoresController implements ActionListener, ListSelectionListener {
    private MainController mainController;

    private boolean store;

    public StoresController (MainController mainController) {
        this.mainController = mainController;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()) {
            case "back":
                System.out.println("back");
                break;
            case "upgrades":
                System.out.println("upgrades");
                mainController.swapStore("upgrades");
                store = false;
                break;
            case "generators":
                System.out.println("generators");
                mainController.swapStore("generators");
                store = true;
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        if (store) {
            if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(0)) {
                System.out.println("redbull");
            }
            else if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(1)) {
                System.out.println("notes");
            }
            else if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(2)) {
                System.out.println("CEUS");
            }
        }else {
            if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(0)) {
                System.out.println("Pills");
            }
            else if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(1)) {
                System.out.println("Glasses");
            }
            else if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(2)) {
                System.out.println("Carlos");
            }
        }
    }
}