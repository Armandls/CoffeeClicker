package Presentation.Views;

import Presentation.Controllers.HomeController;
import Presentation.Fonts.MinecraftFont;
import Presentation.JImagePanel;
import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The view for the home screen of the game.
 */
public class HomeView extends JPanel implements MyView {
    private final ActionListener listener;
    private JTexturedButton newGame;
    private JTexturedButton resumeGame;
    private JLayeredPane layeredPane;

    /**
     * Constructs a new HomeView with the specified ActionListener.
     *
     * @param listener The ActionListener for handling events.
     */
    public HomeView(ActionListener listener) {
        this.listener = listener;
        initComponents();
        setupBackground();
        setupLayout();
    }

    /**
     * Initializes the UI components.
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        // Initialize new game button
        newGame = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        newGame.setFont(MinecraftFont.getFont());
        newGame.setSize(200, 50);
        newGame.setText("New Game");
        newGame.addActionListener(listener);
        newGame.setActionCommand("newGame");

        // Initialize resume game button
        resumeGame = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        resumeGame.setFont(MinecraftFont.getFont());
        resumeGame.setSize(200, 50);
        resumeGame.setText("Resume Game");
        resumeGame.addActionListener(listener);

        newGame.setActionCommand("newGame");
        resumeGame.setActionCommand("resumeGame");

        // Set font for buttons
        newGame.setFont(MinecraftFont.getFont());
        resumeGame.setFont(MinecraftFont.getFont());
    }

    /**
     * Sets up the background image.
     */
    private void setupBackground() {
        try {
            // Create background image panels
            JImagePanel background = new JImagePanel(R.MAIN_BACKGROUND);
            JImagePanel form_background = new JImagePanel(R.FORM_BACKGROUND);
            background.setOpaque(false);
            form_background.setOpaque(false);

            // Create layered pane for overlaying components
            layeredPane = new JLayeredPane();
            layeredPane.setLayout(new OverlayLayout(layeredPane));

            // Set resolution for background image
            background.setResolution(JImagePanel.EXTEND_RES_WIDTH);
            form_background.setSize(getSize());

            // Add background and form background to layered pane
            layeredPane.setLayer(background, 0);
            layeredPane.add(background);

            layeredPane.setLayer(form_background, 1);
            layeredPane.add(form_background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the layout of UI components.
     */
    private void setupLayout() {
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 150));
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 50));
        buttonPanel.add(newGame);
        buttonPanel.add(resumeGame);

        // Create center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel);

        // Add center panel to layered pane
        layeredPane.setLayer(centerPanel, 2);
        layeredPane.add(centerPanel);

        // Add layered pane to main panel
        add(layeredPane, BorderLayout.CENTER);
    }

    /**
     * Displays the list of games available for resuming.
     *
     * @param games A map of game IDs to their respective credits.
     */
    public void displayGames(Map<Integer, Integer> games) {
        // Create dialog for displaying games
        JDialog resumeDialog = new JDialog(JOptionPane.getFrameForComponent(this), "Resume Game", true);
        resumeDialog.setSize(300, 400);
        resumeDialog.setLocationRelativeTo(this);

        // Create layered pane for dialog content
        JLayeredPane panel = new JLayeredPane();
        panel.setLayout(new OverlayLayout(panel));

        // Create panel for displaying game list
        JPanel gameListPanel = new JPanel();
        gameListPanel.setLayout(new BoxLayout(gameListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(gameListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        int i = 1;
        for (Map.Entry<Integer, Integer> entry : games.entrySet()) {
            // Create button for each game
            JTexturedButton gameButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
            gameButton.setText("Game " + i + " | " + entry.getValue() + " credits");
            gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameListPanel.add(gameButton);
            final int gameId = entry.getKey();
            // Add action listener to load selected game
            gameButton.addActionListener(e -> {
                resumeDialog.dispose();
                this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "loadGame-" + gameId));
            });
            i++;
        }

        // Add game list panel to dialog
        resumeDialog.add(scrollPane);
        resumeDialog.setVisible(true);
    }

    /**
     * Displays a dialog for creating a new game.
     */
    @Override
    public void start() {
        // Add action listeners to buttons
        newGame.addActionListener(listener);
        resumeGame.addActionListener(listener);
    }

    /**
     * Stops the view by removing action listeners.
     */
    @Override
    public void stop() {
        // Stop any ongoing processes
    }

    /**
     * Clears the view.
     */
    @Override
    public void clear() {
        // Clear any displayed content
    }
}
