package Presentation.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JOptionPane;


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


        JPanel singupLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        singupLabel.add(new JLabel("Sing Up"));
        mainPanel.add(singupLabel);

        JPanel usernameLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernameLabel.add(new JLabel("Username:"));
        mainPanel.add(usernameLabel);
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameField);
        mainPanel.add(usernamePanel);

        JPanel emailLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailLabel.add(new JLabel("Email:"));
        mainPanel.add(emailLabel);
        JPanel emailPanel = new JPanel();
        emailPanel.add(emailField);
        mainPanel.add(emailPanel);

        JPanel passwordLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordLabel.add(new JLabel("Password:"));
        mainPanel.add(passwordLabel);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        JPanel confirmPasswordLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        confirmPasswordLabel.add(new JLabel("Confirm Password:"));
        mainPanel.add(confirmPasswordLabel);
        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordPanel.add(confirmPasswordField);
        mainPanel.add(confirmPasswordPanel);

        JPanel singUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        singUpPanel.add(singUpButton);
        mainPanel.add(singUpPanel);

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
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        return new String[]{
                username,
                email,
                password,
                confirmPassword
        };
    }

    public void passwordDoesntMatch() {
        JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    public void userAlreadyExists() {
        JOptionPane.showMessageDialog(this, "Mail is already being used. Please use another mail address or log in if you already have an account.", "Mail Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidEmail() {
        JOptionPane.showMessageDialog(this, "Please enter a valid email address. Email address must contain '@gmail.com'.", "Mail Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidPassword() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least 7 characters.", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    public void clearForm() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}
