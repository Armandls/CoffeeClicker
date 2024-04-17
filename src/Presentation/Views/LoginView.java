package Presentation.Views;

import Business.UserManager;
import Presentation.JImagePanel;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

public class LoginView extends JLayeredPane implements MyView {

    private ActionListener listener;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton forgotPassword;
    private JButton loginButton;
    private JRadioButton rememberMe;
    private JButton registerButton;

    public LoginView(ActionListener listener) {

        setLayout(new OverlayLayout(this));
        this.listener = listener;
        init();
        mount();
    }

    void mount() {
        //creating a panel for the form
        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        gridBagPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridheight = 1;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;

        //main panel for the layout
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(300, 500);

        JPanel loginLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginLabel.setOpaque(false);
        loginLabel.add(new JLabel("Login"));
        mainPanel.add(loginLabel);

        JPanel usernameLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernameLabel.setOpaque(false);
        usernameLabel.add(new JLabel("E-mail:"));
        mainPanel.add(usernameLabel);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setOpaque(false);
        usernamePanel.add(usernameField);
        mainPanel.add(usernamePanel);

        JPanel passwordLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordLabel.setOpaque(false);
        passwordLabel.add(new JLabel("Password:"));
        mainPanel.add(passwordLabel);
        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(rememberMe);
        panel.add(forgotPassword);
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));
        mainPanel.add(panel);


        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setOpaque(false);
        loginPanel.add(loginButton);
        loginPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        mainPanel.add(loginPanel);

        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
        registerPanel.add(new JLabel("Don't have an account?"));
        registerPanel.add(registerButton);
        mainPanel.add(registerPanel);
        gridBagPanel.add(mainPanel, c);

        JImagePanel background = new JImagePanel();
        JImagePanel form_background = new JImagePanel();

        //image pannels
        try {
            background = new JImagePanel(R.MAIN_BACKGROUND);
            background.setResolution(JImagePanel.EXTEND_RES);
            setLayer(background, 0);
        } catch (IOException ignored) {

        }

        try {
            form_background = new JImagePanel(R.FORM_BACKGROUND);
            setLayer(form_background, 1);
        } catch (IOException ignored) {

        }



        //setting layers

        setLayer(gridBagPanel, 2);

        //adding panels
        add(background);
        add(form_background);
        add(gridBagPanel);

    }
    void init() {
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 20));

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 20));

        forgotPassword = new JButton("Forgot password?");

        loginButton = new JButton("Login");

        rememberMe = new JRadioButton("Remember me");
        rememberMe.setOpaque(false);

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

    public void passwordDoesntMatch() {
        JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    public void userDoesntExist() {
        JOptionPane.showMessageDialog(this, "Mail is not registered. Please sign up with this mail address or check if you have entered the correct one.", "Mail Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidEmail() {
        JOptionPane.showMessageDialog(this, "Please enter a valid email address. Email address must contain '@gmail.com'.", "Mail Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidPassword() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least 7 characters.", "Password Error", JOptionPane.ERROR_MESSAGE);
    }


    public void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
