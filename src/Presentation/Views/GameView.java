package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JPanel implements MyView {

    private ActionListener listener;
    private JButton configButton;
    private JButton profileButton;
    private JButton phoneButton;
    private JButton clickButton;
    private int num;
    private JLabel counter;

    public GameView(ActionListener listener) {
        this.listener = listener;
        setLayout(new BorderLayout());
        init();
        mount();
    }

    private void init() {
        num = 0;
        configButton = new JButton("Config");
        configButton.setActionCommand("config");

        profileButton = new JButton("Profile");
        profileButton.setActionCommand("profile");

        phoneButton = new JButton("Phone");
        phoneButton.setActionCommand("phone");

        clickButton = new JButton("Click");
        clickButton.setActionCommand("click");

        /*textArea.setAlignmentX(TextArea.RIGHT_ALIGNMENT);
        textArea.setText("Credit Counter");*/
        counter = new JLabel("Credit Counter: " + num);
        start();
    }

    private void mount() {
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel centerTopPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        leftTopPanel.add(configButton);

        centerTopPanel.add(counter);

        rightTopPanel.add(profileButton);
        topPanel.add(leftTopPanel);
        topPanel.add(centerTopPanel);
        topPanel.add(rightTopPanel);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(phoneButton);
        add(bottomPanel, BorderLayout.SOUTH);
        add(clickButton, BorderLayout.CENTER);
    }

    public void increase() {
        counter.setText("Credit Counter: " + num++);
    }

    @Override
    public void start() {
        configButton.addActionListener(listener);
        profileButton.addActionListener(listener);
        phoneButton.addActionListener(listener);
        clickButton.addActionListener(listener);
    }

    @Override
    public void stop() {
        configButton.removeActionListener(listener);
        profileButton.removeActionListener(listener);
        phoneButton.removeActionListener(listener);
        clickButton.removeActionListener(listener);
    }
}
