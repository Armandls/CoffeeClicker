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

public class HomeView extends JPanel implements MyView {
    private ActionListener listener;
    private JTexturedButton newGame;
    private JTexturedButton resumeGame;
    private JImagePanel background;
    private JImagePanel form_background;
    private JLayeredPane layeredPane;

    public HomeView(ActionListener listener) {
        this.listener = listener;
        initComponents();
        setupBackground();
        setupLayout();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        newGame = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        newGame.setFont(MinecraftFont.getFont());
        newGame.setSize(200, 50);
        newGame.setText("New Game");

        resumeGame = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        resumeGame.setFont(MinecraftFont.getFont());
        newGame.setSize(200, 50);
        resumeGame.setText("Resume Game");

        newGame.setActionCommand("newGame");
        resumeGame.setActionCommand("resumeGame");

        newGame.setFont(MinecraftFont.getFont());
        resumeGame.setFont(MinecraftFont.getFont());
        start();
    }

    private void setupBackground() {
        try {
            background = new JImagePanel(R.MAIN_BACKGROUND);
            form_background = new JImagePanel(R.FORM_BACKGROUND);
            background.setOpaque(false);
            form_background.setOpaque(false);

            layeredPane = new JLayeredPane();
            layeredPane.setLayout(new OverlayLayout(layeredPane));

            background.setResolution(JImagePanel.EXTEND_RES_WIDTH);
            form_background.setSize(getSize());
            layeredPane.setLayer(background, 0);
            layeredPane.add(background);

            layeredPane.setLayer(form_background, 1);
            layeredPane.add(form_background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupLayout() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 150));
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 50));
        buttonPanel.add(newGame);
        buttonPanel.add(resumeGame);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel);

        layeredPane.setLayer(centerPanel, 2);
        layeredPane.add(centerPanel);

        add(layeredPane, BorderLayout.CENTER);
    }


    public void displayGames(Map<Integer, Integer> games) {
        JDialog resumeDialog = new JDialog(JOptionPane.getFrameForComponent(this), "Resume Game", true);
        resumeDialog.setSize(300, 400);
        resumeDialog.setLocationRelativeTo(this);

        JPanel gameListPanel = new JPanel();
        gameListPanel.setLayout(new BoxLayout(gameListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(gameListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        int i = 1;
        for (Map.Entry<Integer, Integer> entry : games.entrySet()) {
            JButton gameButton = new JButton("Game "+i+" | " + entry.getValue() + " credits");
            gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameListPanel.add(gameButton);
            final int gameId = entry.getKey();
            gameButton.addActionListener(e -> {
                resumeDialog.dispose();
                this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "loadGame-" + gameId));
            });
            i++;
        }

        resumeDialog.add(scrollPane);
        resumeDialog.setVisible(true);
    }


    public void addNewGameButtonListener(ActionListener listener) {
        newGame.addActionListener(listener);
    }

    public void addResumeGameButtonListener(ActionListener listener) {
        resumeGame.addActionListener(listener);
    }


    @Override
    public void start() {
        newGame.addActionListener(listener);
        resumeGame.addActionListener(listener);
    }
    @Override
    public void stop() {
    }

    @Override
    public void clear() {
    }
}


