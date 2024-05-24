package Presentation.Views;

import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileView extends JPanel implements MyView {

    private final ActionListener listener;
    private JTexturedButton logout;
    private JTexturedButton deleteAccount;
    private JTexturedButton close;

    public ProfileView(ActionListener listener) {
        this.listener = listener;
        setLayout(new GridLayout(2, 1));
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 200));
        setBorder(new StrokeBorder(new BasicStroke(2.0f), Color.YELLOW));
        init();
        mount();
    }

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
    @Override
    public void start() {
        deleteAccount.addActionListener(listener);
        logout.addActionListener(listener);
        close.addActionListener(listener);
    }

    @Override
    public void stop() {
        deleteAccount.removeActionListener(listener);
        logout.removeActionListener(listener);
        close.removeActionListener(listener);
    }
}
