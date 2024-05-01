package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ImprovementsView extends JPanel {

    private ActionListener actionListener;
    private JScrollPane scrollPane;
    private JPanel mainPanel;

    public ImprovementsView(ActionListener listener) {
        this.actionListener = listener;
        init();
        mount();
        setVisible(true);
    }

    private void init() {
        setLayout(new BorderLayout());
        this.mainPanel = new JPanel();
        this.mainPanel.setBackground(Color.BLACK);
    }

    private void mount() {
        this.scrollPane = new JScrollPane(mainPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void reset() {
        this.mainPanel.removeAll();
        scrollPane.revalidate();
    }
    public void addGenerator(/*Todo quedar de com volem agafar la info*/) {
        //Todo
        mainPanel.add(new JButton("Generator"));
        scrollPane.revalidate();
    }
}
