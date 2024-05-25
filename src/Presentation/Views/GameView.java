package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.JImagePanel;
import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends JPanel implements MyView {
    private final ActionListener listener;
    private JTexturedButton configButton;
    private JTexturedButton profileButton;
    private JButton phoneButton;
    private JButton clickButton;
    private float num;
    private JLabel counter;
    private ProfileView profileView;
    private ConfigView configView;
    private JTexturedButton logout;
    private JTexturedButton deleteAccount;
    private final StoresView storesView;
    private JPanel overPanel;
    public GameView(ActionListener listener, StoresView storesView, int num) throws IOException {
        this.listener = listener;
        this.num = num;
        setLayout(new BorderLayout());
        this.storesView = storesView;
        init();
        mount();
    }

    private void init() {
        initButtons();
        initOverPanel();
        initCounter();
        initProfileAndConfigViews();
        start();
    }

    private void initButtons() {
        logout = createTexturedButton("Logout", "logout");
        deleteAccount = createTexturedButton("Delete Account", "deleteAccount");

        configButton = createIconButton(R.SETTINGS_BUTTON, R.SETTINGS_BUTTON_PRESSED, "config", 35, 35);
        profileButton = createIconButton(R.GAME_BUTTON, R.GAME_BUTTON_PRESSED, "profile", 35, 35);

        phoneButton = createInvisibleButton("phone");
        clickButton = createInvisibleButton("click");
    }

    private JTexturedButton createTexturedButton(String text, String actionCommand) {
        JTexturedButton button = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        button.setText(text);
        button.addActionListener(this.listener);
        button.setActionCommand(actionCommand);
        return button;
    }

    private JTexturedButton createIconButton(String defaultIcon, String pressedIcon, String actionCommand, int width, int height) {
        JTexturedButton button = new JTexturedButton(defaultIcon, pressedIcon);
        button.setActionCommand(actionCommand);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    private JButton createInvisibleButton(String actionCommand) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setActionCommand(actionCommand);
        return button;
    }

    private void initOverPanel() {
        overPanel = new JPanel();
        overPanel.setLayout(null);
        overPanel.setOpaque(false);
        overPanel.setVisible(true);
    }

    private void initCounter() {
        num = 0;
        counter = new JLabel("Credits: " + num);
        counter.setFont(MinecraftFont.getFont());
        counter.setForeground(new Color(24, 176, 0));
    }

    private void initProfileAndConfigViews() {
        profileView = new ProfileView(listener);
        profileView.setVisible(false);

        configView = new ConfigView(listener);
    }

    private void mount() throws IOException {
        JLayeredPane layeredPane = createLayeredPane();

        JPanel mainPanel = createMainPanel();
        JPanel topPanel = createTopPanel();
        JPanel bottomPanel = createBottomPanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(clickButton, BorderLayout.CENTER);

        JPanel configProfilePanel = createConfigProfilePanel();

        JImagePanel background = new JImagePanel(R.GAME_BACKGROUND);
        background.setResolution(JImagePanel.EXTEND_RES_WIDTH);

        layeredPane.setLayer(background, 0);
        layeredPane.setLayer(mainPanel, 1);
        layeredPane.setLayer(overPanel, 2);
        layeredPane.setLayer(configProfilePanel, 3);
        layeredPane.setLayer(storesView, 4);

        layeredPane.add(background);
        layeredPane.add(mainPanel);
        layeredPane.add(overPanel);
        layeredPane.add(configProfilePanel);
        layeredPane.add(storesView);

        add(layeredPane, BorderLayout.CENTER);
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        return layeredPane;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        return mainPanel;
    }

    private JPanel createTopPanel() throws IOException {
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.setOpaque(false);

        JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftTopPanel.setOpaque(false);
        leftTopPanel.add(configButton);

        JPanel centerTopPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerTopPanel.setOpaque(false);
        JLayeredPane layeredPane1 = createCounterPanel();
        centerTopPanel.add(layeredPane1);

        JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightTopPanel.setOpaque(false);
        rightTopPanel.add(deleteAccount);
        rightTopPanel.add(logout);

        topPanel.add(leftTopPanel);
        topPanel.add(centerTopPanel);
        topPanel.add(rightTopPanel);

        return topPanel;
    }

    private JLayeredPane createCounterPanel() throws IOException {
        JLayeredPane layeredPane1 = new JLayeredPane();
        layeredPane1.setPreferredSize(new Dimension(200, 40));
        layeredPane1.setLayout(new OverlayLayout(layeredPane1));
        layeredPane1.setOpaque(false);

        JPanel counterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        counterPanel.setOpaque(false);
        counterPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        counterPanel.add(counter);

        layeredPane1.setLayer(counterPanel, 1);
        layeredPane1.add(counterPanel);

        JImagePanel lcd = new JImagePanel(R.LCD);
        lcd.setResolution(JImagePanel.EXTEND_RES_WIDTH);

        layeredPane1.setLayer(lcd, 0);
        layeredPane1.add(lcd);

        return layeredPane1;
    }

    private JPanel createBottomPanel() throws IOException {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JLayeredPane phonePanel = createPhonePanel();

        bottomPanel.add(phonePanel);
        return bottomPanel;
    }

    private JLayeredPane createPhonePanel() throws IOException {
        JLayeredPane phonePanel = new JLayeredPane();
        phonePanel.setPreferredSize(new Dimension(100, 100));
        phonePanel.setLayout(new OverlayLayout(phonePanel));
        phonePanel.setOpaque(false);

        JImagePanel phone = new JImagePanel(R.STORES_BACKGROUND);
        phone.setResolution(JImagePanel.EXTEND_RES_WIDTH);
        phone.setOpaque(false);

        JPanel phoneButtonPanel = new JPanel(new BorderLayout());
        phoneButtonPanel.setOpaque(false);
        phoneButtonPanel.add(phoneButton, BorderLayout.CENTER);

        phonePanel.setLayer(phone, 0);
        phonePanel.setLayer(phoneButtonPanel, 1);
        phonePanel.add(phone);
        phonePanel.add(phoneButtonPanel);

        return phonePanel;
    }

    private JPanel createConfigProfilePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setOpaque(false);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(0, 0, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(configView);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(profileView);

        storesView.setVisible(false);
        storesView.setOpaque(false);
        panel.add(leftPanel);
        panel.add(storesView);
        panel.add(rightPanel);

        return panel;
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
        this.storesView.setVisible(false);
        this.configView.setVisible(false);
        if (configButton.getActionListeners().length <= 0) {
            start();
        }
    }


    public void startRedPanelAnimation(Point mousePosition) {
        // Create red panel
        try {
            JImagePanel redPanel = new JImagePanel(R.COIN);
            redPanel.setOpaque(false);

            redPanel.setSize(50, 50);
            redPanel.setLocation(mousePosition.x - 10 - this.getLocationOnScreen().x, mousePosition.y - 50 - this.getLocationOnScreen().y); // Set its initial position
            overPanel.add(redPanel);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                private float alpha = 1.0f;

                @Override
                public void run() {
                    alpha -= 0.05f;
                    if (alpha <= 0.0f) {
                        overPanel.remove(redPanel);
                        overPanel.repaint();
                        overPanel.revalidate();
                        cancel();
                    } else {
                        redPanel.setLocation(redPanel.getLocation().x, redPanel.getLocation().y - 5);
                        redPanel.setAlpha(alpha);
                    }
                }
            }, 0, 30);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(float currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp) {
        this.num = currency;
        counter.setText("Credits: " + currency);
        storesView.initialize(basicGenerator, midGenerator, highGenerator, lvlBasicImp, lvlMidImp, lvlHighImp);
    }

    public void updateTable(int[] quantities, float[] totalCreditsPerSecond, float[] globalProductionPercentages, int[] generatorAmount, int[] improvements_lvl) {
        storesView.updateGeneratorsView(quantities, totalCreditsPerSecond, globalProductionPercentages, generatorAmount);
        storesView.updateImprovementsView(improvements_lvl);
    }

    public void updateCurrency(float gameCurrencies) {
        counter.setText("Credits: " + gameCurrencies);
    }
}
