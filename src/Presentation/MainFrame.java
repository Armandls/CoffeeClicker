package Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * The MainFrame class is a JFrame that uses a CardLayout to switch between different panels.
 * It sets up the main frame of the application, including the card layout for panel switching.
 */
public class MainFrame extends JFrame {

    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    /**
     * Constructs the MainFrame with default settings.
     * Sets the default close operation, size, and resizable property.
     * Initializes the CardLayout and the main panel for switching between different views.
     */
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        add(cardPanel, BorderLayout.CENTER);
        cardPanel.setVisible(true);
    }

    /**
     * Adds a JPanel to the card panel with the specified name.
     *
     * @param panel the JPanel to add
     * @param name the name to associate with the panel for later retrieval
     */
    void addPanel(JPanel panel, String name) {
        cardPanel.add(name, panel);
        //cardLayout.show(cardPanel, ");
    }

    /**
     * Adds a JLayeredPane to the card panel with the specified name.
     *
     * @param panel the JLayeredPane to add
     * @param name the name to associate with the panel for later retrieval
     */
    void addPanel(JLayeredPane panel, String name) {
        cardPanel.add(name, panel);
        //cardLayout.show(cardPanel, ");
    }

    /**
     * Shows the panel associated with the specified name.
     *
     * @param panelName the name of the panel to display
     */
    void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}
