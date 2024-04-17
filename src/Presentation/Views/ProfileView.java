package Presentation.Views;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileView extends JPanel implements MyView {

    private ActionListener listener;
    private JButton logout;
    private JButton deleteAccount;
    private JButton close;

    public ProfileView(ActionListener listener) {
        this.listener = listener;
        setLayout(new GridLayout(2, 1));
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 200));
        setBorder(new StrokeBorder(new BasicStroke(2.0f)));
        init();
        mount();
    }

    private void init() {
        logout = new JButton("Logout");
        logout.setActionCommand("logout");

        deleteAccount = new JButton("Delete Account");
        deleteAccount.setActionCommand("deleteAccount");

        close = new JButton("x");
        close.setActionCommand("profileClose");

        start();
    }

    private void mount() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel aux = new JPanel();
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

    @Override
    public void clear() {
        //unimplemented
    }
}
