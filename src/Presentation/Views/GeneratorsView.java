package Presentation.Views;

import Presentation.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GeneratorsView extends JPanel {

    private ActionListener actionListener;
    private JScrollPane scrollPane;
    private JPanel mainPanel;

    public GeneratorsView(ActionListener listener) {
        this.actionListener = listener;
        init();
        mount();
        setVisible(true);
    }

    private void init() {
        setLayout(new BorderLayout());
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setBackground(Color.BLACK);
    }

    private void mount() {

        JPanel generatorsPanel = new JPanel(new FlowLayout());
        generatorsPanel.add(new JLabel("Name"));
        generatorsPanel.add(new JLabel("Price"));
        generatorsPanel.add(new JLabel("Amount"));
        mainPanel.add(generatorsPanel);
        this.scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void reset() {
        this.mainPanel.removeAll();
        scrollPane.revalidate();
    }
    public void addGenerator(String picture, String name, String price, String amount) {
        JPanel generatorPanel = new JPanel(new FlowLayout());
        try {
            generatorPanel.add(new JImagePanel(picture));
        } catch (Exception ignored) {
            //ignore
        }
        generatorPanel.add(new JLabel(name));
        generatorPanel.add(new JLabel(price));
        generatorPanel.add(new JLabel(amount));
        scrollPane.revalidate();
    }
}
