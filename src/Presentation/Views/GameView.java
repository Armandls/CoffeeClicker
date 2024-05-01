package Presentation.Views;

import Presentation.Controllers.GameController;

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
    private ProfileView profileView;
    private ConfigView configView;

    public GameView(ActionListener listener, int num) {
        this.listener = listener;
        this.num = num;
        setLayout(new BorderLayout());
        init();
        mount();
    }

    private void init() {
        configButton = new JButton("Config");
        configButton.setActionCommand("config");

        profileButton = new JButton("Profile");
        profileButton.setActionCommand("profile");

        phoneButton = new JButton("Phone");
        phoneButton.setActionCommand("phone");

        clickButton = new JButton("Click");
        clickButton.setActionCommand("click");

        counter = new JLabel("Credit Counter: " + num);

        profileView = new ProfileView(listener);
        profileView.setVisible(false);

        configView = new ConfigView(listener);

        start();
    }

    private void mount() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

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
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(phoneButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(clickButton, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(0, 0, 0, 0));
        leftPanel.setOpaque(false);
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        panel.setBackground(new Color(0, 0, 0, 0));
        rightPanel.add(profileView);
        leftPanel.add(configView);
        panel.add(leftPanel);
        panel.add(rightPanel);
        panel.setOpaque(false);

        layeredPane.setLayer(mainPanel, 0);
        layeredPane.setLayer(panel, 1);

        layeredPane.add(panel);
        layeredPane.add(mainPanel);

        add(layeredPane, BorderLayout.CENTER);
    }

    public void increase() {
        counter.setText("Credit Counter: " + num++);
    }

    public void showProfile() {
        profileView.setVisible(true);
    }

    public void showConfig() {
        configView.setVisible(true);
    }

    public void hideConfig() {
        configView.setVisible(false);
    }

    public void hideProfile() {
        profileView.setVisible(false);
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

    @Override
    public void clear() {
        //unimplemented
    }
}
