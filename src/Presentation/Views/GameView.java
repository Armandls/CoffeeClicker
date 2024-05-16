package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.JHoverPanel;
import Presentation.JImagePanel;
import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends JPanel implements MyView {
    private ActionListener listener;
    private JTexturedButton configButton;
    private JTexturedButton profileButton;
    private JButton phoneButton;
    private JButton clickButton;
    private int num;
    private JLabel counter;
    private ProfileView profileView;
    private ConfigView configView;
    private StoresView storesView;
    private JPanel overPanel;
    private JPanel hoversPanel;

    private Hashtable<String, JPanel> hoverPanelList;


    public GameView(ActionListener listener, StoresView storesView, int num) throws IOException {
        this.listener = listener;
        this.num = num;
        hoverPanelList = new Hashtable<>();
        setLayout(new BorderLayout());
        this.storesView = storesView;
        init();
        mount();
    }

    private void init() {
        hoversPanel = new JPanel(null);
        hoversPanel.setOpaque(false);
        hoversPanel.setVisible(true);

        overPanel = new JPanel();
        overPanel.setLayout(null);
        overPanel.setOpaque(false);
        overPanel.setVisible(true);

        num = 0;
        configButton = new JTexturedButton(R.SETTINGS_BUTTON, R.SETTINGS_BUTTON_PRESSED);
        configButton.setActionCommand("config");
        configButton.setPreferredSize(new Dimension(35, 35));

        profileButton = new JTexturedButton(R.GAME_BUTTON, R.GAME_BUTTON_PRESSED);
        profileButton.setActionCommand("profile");
        profileButton.setPreferredSize(new Dimension(35, 35));

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
        counter.setFont(MinecraftFont.getFont());
        counter.setForeground(new Color(24, 176, 0));

        profileView = new ProfileView(listener);
        profileView.setVisible(false);

        configView = new ConfigView(listener);
        start();
    }

    private void mount() throws IOException {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.setOpaque(false);
        JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftTopPanel.setOpaque(false);
        JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightTopPanel.setOpaque(false);
        JPanel centerTopPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerTopPanel.setOpaque(false);

        leftTopPanel.add(configButton);

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

        centerTopPanel.add(layeredPane1);

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
        layeredPane.setLayer(overPanel, 4);
        layeredPane.setLayer(hoversPanel, 5);

        layeredPane.add(panel);
        layeredPane.add(mainPanel);
        layeredPane.add(storesView);
        layeredPane.add(background);
        layeredPane.add(overPanel);
        layeredPane.add(hoversPanel);
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

    public void startRedPanelAnimation(Point mousePosition) {
        // Create red panel
        try {
            JImagePanel redPanel = new JImagePanel(R.COIN);
            redPanel.setOpaque(false);

            redPanel.setSize(50, 50); // Set the size as desired
            redPanel.setLocation(mousePosition.x - 10 - this.getLocationOnScreen().x, mousePosition.y - 50 - this.getLocationOnScreen().y); // Set its initial position
            overPanel.add(redPanel); // Add red panel to the view

            // Implement fading animation
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                private float alpha = 1.0f; // Initial opacity

                @Override
                public void run() {
                    alpha -= 0.05f; // Adjust opacity (you can change this value for speed)
                    if (alpha <= 0.0f) {
                        // Animation finished, remove red panel from the view
                        overPanel.remove(redPanel);
                        overPanel.repaint(); // Refresh the overPanel
                        overPanel.revalidate(); // Revalidate the overPanel to reflect changes
                        cancel(); // Stop the timer
                    } else {
                        // Set new opacity for the red panel
                        redPanel.setLocation(redPanel.getLocation().x, redPanel.getLocation().y - 5); // Move the panel up (you can change this value for speed)
                        redPanel.setAlpha(alpha); // Red color with variable alpha
                    }
                }
            }, 0, 30);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize (int currency, int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp) {
        this.num = currency;
        counter.setText("Credits: " + currency);
        storesView.initialize(basicGenerator, midGenerator, highGenerator, lvlBasicImp, lvlMidImp, lvlHighImp);
    }

    public void addHoverPanel(JHoverPanel panel) {
        this.hoverPanelList.put(panel.getId(), panel.getPanel());
        this.hoversPanel.add(panel.getPanel());
    }

    public void updateCurrency(int gameCurrencies) {
        counter.setText("Credits: " + gameCurrencies);
    }

    public void removeHoverPanel(String name) {
        this.hoversPanel.remove(this.hoverPanelList.get(name));
        this.hoverPanelList.remove(name);
        this.revalidate();
    }


}
