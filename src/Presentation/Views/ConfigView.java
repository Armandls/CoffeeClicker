package Presentation.Views;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfigView extends JPanel implements MyView {

    private ActionListener listener;

    private JButton save;
    private JButton options;
    private JButton close;

    public ConfigView(ActionListener listener) {
        this.listener = listener;
        setLayout(new GridLayout(2, 1));
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 200));
        setBorder(new StrokeBorder(new BasicStroke(2.0f)));
        init();
        mount();
    }

    private void init() {
        save = new JButton("Save");
        save.setActionCommand("save");

        options = new JButton("Options");
        options.setActionCommand("options");

        close = new JButton("x");
        close.setActionCommand("configClose");
    }

    private void mount() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel aux = new JPanel();
        aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));

        topPanel.add(close);
        aux.add(save);
        aux.add(options);
        bottomPanel.add(aux);
        add(topPanel);
        add(bottomPanel);
        setVisible(false);
        start();
    }

    @Override
    public void start() {
        save.addActionListener(listener);
        options.addActionListener(listener);
        close.addActionListener(listener);
    }

    @Override
    public void stop() {
        save.removeActionListener(listener);
        options.removeActionListener(listener);
        close.removeActionListener(listener);
    }

    @Override
    public void clear() {
        //unimplemented
    }
}
