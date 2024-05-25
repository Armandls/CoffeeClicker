package Presentation.Views;

import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View for displaying the user's profile.
 */
public class ProfileView extends JPanel implements MyView {

    private final ActionListener listener;
    private JTexturedButton logout;
    private JTexturedButton deleteAccount;
    private JTexturedButton close;

    /**
     * Constructs a new ProfileView.
     * @param listener The ActionListener for handling events.
     */
    public ProfileView(ActionListener listener) {
        this.listener = listener;
        setLayout(new GridLayout(2, 1));
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 200));
        setBorder(new StrokeBorder(new BasicStroke(2.0f), Color.YELLOW));
        init();
        mount();
    }

    /**
     * Initializes the components of the view.
     */
    private void init() {
        logout = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        logout.setText("Logout");
        logout.setActionCommand("logout");

        deleteAccount = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        deleteAccount.setText("Delete Account");
        deleteAccount.setActionCommand("deleteAccount");

        close = new JTexturedButton(R.GAME_BUTTON, R.GAME_BUTTON_PRESSED);
        close.setPreferredSize(new Dimension(35, 35));
        close.setActionCommand("profileClose");

        start();
    }

    /**
     * Mounts the components onto the view.
     */
    private void mount() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.darkGray);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(Color.darkGray);
        JPanel aux = new JPanel();
        aux.setBackground(Color.darkGray);
        aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));

        topPanel.add(close);
        aux.add(logout);
        aux.add(deleteAccount);
        bottomPanel.add(aux);
        add(topPanel);
        add(bottomPanel);
    }

    /**
     * Starts the view.
     */
    @Override
    public void start() {
        deleteAccount.addActionListener(listener);
        logout.addActionListener(listener);
        close.addActionListener(listener);
    }

    /**
     * Stops the view.
     */
    @Override
    public void stop() {
        deleteAccount.removeActionListener(listener);
        logout.removeActionListener(listener);
        close.removeActionListener(listener);
    }

    /**
     * Clears the view.
     */
    @Override
    public void clear() {
        // This method could be implemented to clear any data or state in the view.
    }
}
