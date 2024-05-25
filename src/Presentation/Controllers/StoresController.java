package Presentation.Controllers;
import Presentation.FrameController;
import Presentation.MainController;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoresController implements ActionListener, ListSelectionListener {
    private final FrameController mainController;

    private boolean store;

    public StoresController (FrameController mainController) {
        this.mainController = mainController;
        store = true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "back":
                break;
            case "upgrades":
                mainController.swapStore("upgrades");
                store = false;
                break;
            case "generators":
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
                mainController.buyGenerator("Redbull");
            }
            else if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(1)) {
                mainController.buyGenerator("Notes");
            }
            else if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(2)) {
                mainController.buyGenerator("CEUS");
            }
        }else {
            if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(0)) {
                mainController.updateImprovement("Pills");
            }
            else if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(1)) {
                mainController.updateImprovement("Glasses");
            }
            else if (((DefaultListSelectionModel)e.getSource()).isSelectedIndex(2)) {
                mainController.updateImprovement("Carlos");
            }
        }
        ((DefaultListSelectionModel)e.getSource()).clearSelection();
    }
}