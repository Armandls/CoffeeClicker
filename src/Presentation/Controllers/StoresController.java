package Presentation.Controllers;

import Presentation.Interfaces.StoresControllerI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for managing store-related actions and interactions.
 */
public class StoresController implements ActionListener, ListSelectionListener {
    private final StoresControllerI mainController;

    private boolean store;

    /**
     * Constructor for StoresController.
     *
     * @param mainController The main controller interface.
     */
    public StoresController(StoresControllerI mainController) {
        this.mainController = mainController;
        store = true;
    }

    /**
     * Handles action events triggered by GUI components.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back":
                // Handle back action
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

    /**
     * Handles value change events triggered by GUI components.
     *
     * @param e The list selection event.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        if (store) {
            if (((DefaultListSelectionModel) e.getSource()).isSelectedIndex(0)) {
                mainController.buyGenerator("Redbull");
            } else if (((DefaultListSelectionModel) e.getSource()).isSelectedIndex(1)) {
                mainController.buyGenerator("Notes");
            } else if (((DefaultListSelectionModel) e.getSource()).isSelectedIndex(2)) {
                mainController.buyGenerator("CEUS");
            }
        } else {
            if (((DefaultListSelectionModel) e.getSource()).isSelectedIndex(0)) {
                mainController.updateImprovement("Pills");
            } else if (((DefaultListSelectionModel) e.getSource()).isSelectedIndex(1)) {
                mainController.updateImprovement("Glasses");
            } else if (((DefaultListSelectionModel) e.getSource()).isSelectedIndex(2)) {
                mainController.updateImprovement("Carlos");
            }
        }
        ((DefaultListSelectionModel) e.getSource()).clearSelection();
    }
}
