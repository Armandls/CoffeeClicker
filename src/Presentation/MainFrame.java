package Presentation;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        add(cardPanel, BorderLayout.CENTER);
        cardPanel.setVisible(true);
    }

    void addPanel(JPanel panel, String name) {
        cardPanel.add(name, panel);
        //cardLayout.show(cardPanel, ");
    }

    void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}
