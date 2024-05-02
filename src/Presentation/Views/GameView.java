package Presentation.Views;

import Presentation.Controllers.GameController;
import Presentation.Controllers.StoresController;
import Presentation.JImagePanel;
import Presentation.R;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

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
    private StoresView storesView;


    public GameView(ActionListener listener, StoresView storesView, int num) throws IOException {
        this.listener = listener;
        this.num = num;
        setLayout(new BorderLayout());
        this.storesView = storesView;
        init();
        mount();
    }

    private void init() throws IOException {
        num = 0;
        configButton = new JButton("Config");
        configButton.setActionCommand("config");

        profileButton = new JButton("Profile");
        profileButton.setActionCommand("profile");

        phoneButton = new JButton();
        phoneButton.setOpaque(false);
        phoneButton.setContentAreaFilled(false);
        phoneButton.setBorderPainted(false);
        phoneButton.setActionCommand("phone");

        clickButton = new JButton();
        clickButton.setActionCommand("click");
        clickButton.setOpaque(false);
        clickButton.setContentAreaFilled(false);
        clickButton.setBorderPainted(false);

        counter = new JLabel("Credit Counter: " + num);

        profileView = new ProfileView(listener);
        profileView.setVisible(false);

        configView = new ConfigView(listener);
        storesView = new StoresView(listener);
        start();
    }

    private void mount() throws IOException {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

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
        bottomPanel.setOpaque(false);

        JLayeredPane phonePanel = new JLayeredPane();
        phonePanel.setPreferredSize(new Dimension(100, 100));
        phonePanel.setLayout(new OverlayLayout(phonePanel));

        JImagePanel phone = new JImagePanel(R.STORES_BACKGROUND);
        phone.setResolution(JImagePanel.EXTEND_RES_WIDTH);
        JPanel phoneButtonPanel = new JPanel(new BorderLayout());
        phoneButtonPanel.setOpaque(false);
        phoneButtonPanel.add(phoneButton, BorderLayout.CENTER);

        phonePanel.setLayer(phone, 0);
        phonePanel.setLayer(phoneButtonPanel, 1);

        phonePanel.add(phone);
        phonePanel.add(phoneButtonPanel);

        bottomPanel.add(phonePanel);
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

        storesView.setVisible(false);
        storesView.setOpaque(false);
        panel.add(storesView);

        panel.add(rightPanel);
        panel.setOpaque(false);

        JImagePanel background = new JImagePanel(R.GAME_BACKGROUND);
        background.setResolution(JImagePanel.EXTEND_RES_WIDTH);

        layeredPane.setLayer(background, 0);
        layeredPane.setLayer(mainPanel, 1);
        layeredPane.setLayer(panel, 2);
        layeredPane.setLayer(storesView, 3);

        layeredPane.add(panel);
        layeredPane.add(mainPanel);
        layeredPane.add(storesView);
        layeredPane.add(background);
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

    public void toggleStore() {
        storesView.setVisible(!storesView.isVisible());
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
