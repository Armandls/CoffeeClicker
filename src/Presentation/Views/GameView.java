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

/**
 * GameView represents the main game view in the user interface.
 */
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

    /**
     * Constructs a new GameView with the specified ActionListener and StoresView.
     *
     * @param listener   The ActionListener to handle events in the view.
     * @param storesView The StoresView associated with the game view.
     * @param num        The initial number of credits.
     * @throws IOException if there is an error loading resources.
     */
    public GameView(ActionListener listener, StoresView storesView, int num) throws IOException {
        this.listener = listener;
        this.num = num;
        setLayout(new BorderLayout());
        this.storesView = storesView;
        init();
        mount();
    }

    /**
     * Initializes the components of the view.
     */
    private void init() {
        initButtons();
        initOverPanel();
        initCounter();
        initProfileAndConfigViews();
        start();
    }

    /**
     * Initializes the buttons in the view.
     */
    private void initButtons() {
        logout = createTexturedButton("Logout", "logout");
        deleteAccount = createTexturedButton("Delete Account", "deleteAccount");

        configButton = createIconButton(R.SETTINGS_BUTTON, R.SETTINGS_BUTTON_PRESSED, "config", 35, 35);
        profileButton = createIconButton(R.GAME_BUTTON, R.GAME_BUTTON_PRESSED, "profile", 35, 35);

        phoneButton = createInvisibleButton("phone");
        clickButton = createInvisibleButton("click");
    }

    /**
     * Creates a JTexturedButton with the specified text and action command.
     *
     * @param text          The text of the button.
     * @param actionCommand The action command of the button.
     * @return The created JTexturedButton.
     */
    private JTexturedButton createTexturedButton(String text, String actionCommand) {
        JTexturedButton button = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        button.setText(text);
        button.addActionListener(this.listener);
        button.setActionCommand(actionCommand);
        return button;
    }

    /**
     * Creates a JTexturedButton with the specified default and pressed icons, action command, width, and height.
     *
     * @param defaultIcon  The default icon of the button.
     * @param pressedIcon  The pressed icon of the button.
     * @param actionCommand The action command of the button.
     * @param width         The width of the button.
     * @param height        The height of the button.
     * @return The created JTexturedButton.
     */
    private JTexturedButton createIconButton(String defaultIcon, String pressedIcon, String actionCommand, int width, int height) {
        JTexturedButton button = new JTexturedButton(defaultIcon, pressedIcon);
        button.setActionCommand(actionCommand);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    /**
     * Creates an invisible JButton with the specified action command.
     *
     * @param actionCommand The action command of the button.
     * @return The created JButton.
     */
    private JButton createInvisibleButton(String actionCommand) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setActionCommand(actionCommand);
        return button;
    }

    /**
     * Initializes the overlay panel used for animations.
     */
    private void initOverPanel() {
        overPanel = new JPanel();
        overPanel.setLayout(null);
        overPanel.setOpaque(false);
        overPanel.setVisible(true);
    }

    /**
     * Initializes the counter label for displaying credits.
     */
    private void initCounter() {
        num = 0;
        counter = new JLabel("Credits: " + num);
        counter.setFont(MinecraftFont.getFont());
        counter.setForeground(new Color(24, 176, 0));
    }

    /**
     * Initializes the profile and config views.
     */
    private void initProfileAndConfigViews() {
        profileView = new ProfileView(listener);
        profileView.setVisible(false);

        configView = new ConfigView(listener);
    }

    /**
     * Mounts the components in the view.
     *
     * @throws IOException if there is an error loading resources.
     */
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

    /**
     * Creates a JLayeredPane for managing layered components.
     *
     * @return The created JLayeredPane.
     */
    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        return layeredPane;
    }

    /**
     * Creates the main panel for holding components.
     *
     * @return The created main panel.
     */
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        return mainPanel;
    }

    /**
     * Creates the top panel containing buttons and labels.
     *
     * @return The created top panel.
     * @throws IOException if there is an error loading resources.
     */
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

    /**
     * Creates a JLayeredPane for displaying the counter label with an LCD background.
     *
     * @return The created JLayeredPane.
     * @throws IOException if there is an error loading resources.
     */
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

    /**
     * Creates the bottom panel containing buttons.
     *
     * @return The created bottom panel.
     * @throws IOException if there is an error loading resources.
     */
    private JPanel createBottomPanel() throws IOException {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JLayeredPane phonePanel = createPhonePanel();

        bottomPanel.add(phonePanel);
        return bottomPanel;
    }

    /**
     * Creates a JLayeredPane for displaying the phone button.
     *
     * @return The created JLayeredPane.
     * @throws IOException if there is an error loading resources.
     */
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

    /**
     * Creates a panel containing profile and config views.
     *
     * @return The created panel.
     */
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

    /**
     * Displays the profile view.
     */
    public void showProfile() {
        profileView.setVisible(true);
    }

    /**
     * Displays the config view.
     */
    public void showConfig() {
        configView.setVisible(true);
    }

    /**
     * Hides the config view.
     */
    public void hideConfig() {
        configView.setVisible(false);
    }

    /**
     * Hides the profile view.
     */
    public void hideProfile() {
        profileView.setVisible(false);
    }

    /**
     * Toggles the visibility of the stores view.
     */
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

    /** Initiates the red panel animation at the specified mouse position.
    *
    * @param mousePosition The position where the animation should start.
     */
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

    /**
     * Initializes the view with the specified currency and generator levels.
     *
     * @param currency        The initial currency value.
     * @param basicGenerator  The level of the basic generator.
     * @param midGenerator    The level of the mid-level generator.
     * @param highGenerator   The level of the high-level generator.
     * @param lvlBasicImp     The level of the basic improvement.
     * @param lvlMidImp       The level of the mid-level improvement.
     * @param lvlHighImp      The level of the high-level improvement.
     */
    public void initialize(float currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp) {
        this.num = currency;
        counter.setText("Credits: " + currency);
        storesView.initialize(basicGenerator, midGenerator, highGenerator, lvlBasicImp, lvlMidImp, lvlHighImp);
    }

    /**
     * Updates the table with new data.
     *
     * @param quantities               The quantities of each generator.
     * @param totalCreditsPerSecond    The total credits produced per second by each generator.
     * @param globalProductionPercentages The global production percentages of each generator.
     * @param generatorAmount          The amount of each generator owned.
     * @param improvements_lvl         The levels of improvements.
     */
    public void updateTable(int[] quantities, float[] totalCreditsPerSecond, float[] globalProductionPercentages, int[] generatorAmount, int[] improvements_lvl) {
        storesView.updateGeneratorsView(quantities, totalCreditsPerSecond, globalProductionPercentages, generatorAmount);
        storesView.updateImprovementsView(improvements_lvl);
    }

    /**
     * Updates the currency display with the specified amount.
     *
     * @param gameCurrencies The updated currency value.
     */
    public void updateCurrency(float gameCurrencies) {
        counter.setText("Credits: " + gameCurrencies);
    }
}
