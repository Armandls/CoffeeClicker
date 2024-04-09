package Presentation.Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginView extends JPanel implements MyView {

    private ActionListener listener;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton forgotPassword;
    private JButton loginButton;
    private JRadioButton rememberMe;
    private JButton registerButton;

    public LoginView(ActionListener listener) {

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

        JPanel loginLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginLabel.add(new JLabel("Login"));
        mainPanel.add(loginLabel);

        JPanel usernameLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernameLabel.add(new JLabel("Username:"));
        mainPanel.add(usernameLabel);
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameField);
        mainPanel.add(usernamePanel);

        JPanel passwordLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordLabel.add(new JLabel("Password:"));
        mainPanel.add(passwordLabel);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(rememberMe);
        panel.add(forgotPassword);
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));
        mainPanel.add(panel);


        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.add(loginButton);
        loginPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        mainPanel.add(loginPanel);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
        registerPanel.add(new JLabel("Don't have an account?"));
        registerPanel.add(registerButton);
        mainPanel.add(registerPanel);

        add(mainPanel, c);

    }

    void init() {
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 20));

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 20));

        forgotPassword = new JButton("Forgot password?");

        loginButton = new JButton("Login");

        rememberMe = new JRadioButton("Remember me");

        registerButton = new JButton("Register");
        start();
    }

    @Override
    public void start() {
        forgotPassword.addActionListener(listener);
        forgotPassword.setActionCommand("forgotPassword");

        loginButton.addActionListener(listener);
        loginButton.setActionCommand("login");

        registerButton.addActionListener(listener);
        registerButton.setActionCommand("register");
    }

    @Override
    public void stop() {
        forgotPassword.removeActionListener(listener);
        loginButton.removeActionListener(listener);
        registerButton.removeActionListener(listener);
    }

    public String[] getInfo() {
        return new String[] {
                "username:"+usernameField.getText(),
                "password:"+ Arrays.toString(passwordField.getPassword()),
                "rememberMe:"+rememberMe.isSelected()
        };
    }
}
