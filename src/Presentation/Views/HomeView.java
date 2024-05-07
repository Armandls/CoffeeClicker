package Presentation.Views;

import Presentation.Controllers.HomeController;
import Presentation.Fonts.MinecraftFont;
import Presentation.JImagePanel;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class HomeView extends JPanel implements MyView {
    private ActionListener listener;
    private JButton newGame;
    private JButton resumeGame;
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
        newGame = new JButton("New Game");
        resumeGame = new JButton("Resume Game");

        try {
            Font minecraftFont = MinecraftFont.getFont().deriveFont(24f);
            newGame.setFont(minecraftFont);
            resumeGame.setFont(minecraftFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        start();
    }

    private void setupBackground() {
        try {
            background = new JImagePanel(R.MAIN_BACKGROUND);
            form_background = new JImagePanel(R.FORM_BACKGROUND);
            background.setOpaque(false);
            form_background.setOpaque(false);

            layeredPane = new JLayeredPane();
            layeredPane.setLayout(new BorderLayout());
            background.setSize(getSize());
            form_background.setSize(getSize());

            layeredPane.add(background, 1);
            layeredPane.add(form_background, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupLayout() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(newGame);
        buttonPanel.add(resumeGame);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonPanel, new GridBagConstraints());

        layeredPane.add(centerPanel, 3);

        add(layeredPane, BorderLayout.CENTER);
    }

    public void addNewGameButtonListener(ActionListener listener) {
        newGame.addActionListener(listener);
    }

    public void addResumeGameButtonListener(ActionListener listener) {
        resumeGame.addActionListener(listener);
    }

    public void start() {
        newGame.setActionCommand("newGame");
        resumeGame.setActionCommand("resumeGame");
    }

    @Override
    public void stop() {
    }

    @Override
    public void clear() {}

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
}


