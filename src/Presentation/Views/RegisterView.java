package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RegisterView extends JPanel implements MyView {

    private ActionListener listener;
    private JTextField usernameField;

    private JTextField emailField;
    private JPasswordField passwordField;

    private JPasswordField confirmPasswordField;
    private JButton singUpButton;
    private JButton loginButton;

    public RegisterView(ActionListener listener) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridheight = 1;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;

        this.listener = listener;

        init();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(300, 500);


        mainPanel.add(new JLabel("Sing Up"));


        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameField);
        mainPanel.add(usernamePanel);

        JPanel emailPanel = new JPanel();
        emailPanel.add(emailField);
        mainPanel.add(emailPanel);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordPanel.add(confirmPasswordField);
        mainPanel.add(confirmPasswordPanel);

        mainPanel.add(singUpButton);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
        registerPanel.add(new JLabel("Already have an account? "));
        registerPanel.add(loginButton);
        mainPanel.add(registerPanel);

        add(mainPanel, c);

    }

    void init() {
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 20));

        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 20));

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20));

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 20));

        loginButton = new JButton("Login");

        singUpButton = new JButton("Sing Up");

        start();
    }

    @Override
    public void start() {
        loginButton.addActionListener(listener);
        loginButton.setActionCommand("login");

        singUpButton.addActionListener(listener);
        singUpButton.setActionCommand("singUp");
    }

    @Override
    public void stop() {
        loginButton.removeActionListener(listener);
        singUpButton.removeActionListener(listener);
    }

    public String[] getInfo() {
        return new String[]{
                "username:" + usernameField.getText(),
                "email:" + emailField.getText(),
                "password:" + Arrays.toString(passwordField.getPassword()),
                "confirmPassword:" + Arrays.toString(confirmPasswordField.getPassword())
        };
    }
}
