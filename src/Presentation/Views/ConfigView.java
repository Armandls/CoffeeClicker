package Presentation.Views;

import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfigView extends JPanel implements MyView {

    private ActionListener listener;
    private JTexturedButton exit;
    private JTexturedButton options;
    private JTexturedButton close;

    public ConfigView(ActionListener listener) {
        this.listener = listener;
        setLayout(new GridLayout(2, 1));
        setBackground(Color.darkGray);
        setPreferredSize(new Dimension(200, 200));
        setBorder(new StrokeBorder(new BasicStroke(2.0f), Color.YELLOW));
        init();
        mount();
    }

    private void init() {
        exit = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        exit.setText("Exit");
        exit.setActionCommand("exit");

        options = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        options.setText("Options");
        options.setActionCommand("options");

        close = new JTexturedButton(R.SETTINGS_BUTTON, R.SETTINGS_BUTTON_PRESSED);
        close.setPreferredSize(new Dimension(35, 35));
        close.setActionCommand("configClose");
    }

    private void mount() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.darkGray);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.darkGray);
        JPanel aux = new JPanel();
        aux.setBackground(Color.darkGray);
        aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));

        topPanel.add(close);
        aux.add(exit);
        aux.add(options);
        bottomPanel.add(aux);
        add(topPanel);
        add(bottomPanel);
        setVisible(false);
        start();
    }

    @Override
    public void start() {
        exit.addActionListener(listener);
        options.addActionListener(listener);
        close.addActionListener(listener);
    }

    @Override
    public void stop() {
        exit.removeActionListener(listener);
        options.removeActionListener(listener);
        close.removeActionListener(listener);
    }

    @Override
    public void clear() {
        //unimplemented
    }
}
