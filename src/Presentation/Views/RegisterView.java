package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.JImagePanel;
import Presentation.R;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;


public class RegisterView extends JLayeredPane implements MyView {

    private ActionListener listener;
    private JTextField usernameField;

    private JTextField emailField;
    private JPasswordField passwordField;

    private JPasswordField confirmPasswordField;
    private JButton singUpButton;
    private JButton loginButton;

    public RegisterView(ActionListener listener) {
        setLayout(new OverlayLayout(this));


        this.listener = listener;

        init();

        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        gridBagPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridheight = 1;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(300, 500);


        JPanel singupLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        singupLabelPanel.setOpaque(false);
        JLabel signUpLabel = new JLabel("Sing Up");
        signUpLabel.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 60));
        singupLabelPanel.add(signUpLabel);
        mainPanel.add(singupLabelPanel);

        JPanel usernameLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernameLabelPanel.setOpaque(false);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(MinecraftFont.getFont());
        usernameLabelPanel.add(usernameLabel);
        mainPanel.add(usernameLabelPanel);
        JPanel usernamePanel = new JPanel();
        usernamePanel.setOpaque(false);
        usernamePanel.add(usernameField);
        mainPanel.add(usernamePanel);

        JPanel emailLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailLabelPanel.setOpaque(false);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(MinecraftFont.getFont());
        emailLabelPanel.add(emailLabel);
        mainPanel.add(emailLabelPanel);
        JPanel emailPanel = new JPanel();
        emailPanel.setOpaque(false);
        emailPanel.add(emailField);
        mainPanel.add(emailPanel);

        JPanel passwordLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordLabelPanel.setOpaque(false);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(MinecraftFont.getFont());
        passwordLabelPanel.add(passwordLabel);
        mainPanel.add(passwordLabelPanel);
        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        JPanel confirmPasswordLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        confirmPasswordLabelPanel.setOpaque(false);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(MinecraftFont.getFont());
        confirmPasswordLabelPanel.add(confirmPasswordLabel);
        mainPanel.add(confirmPasswordLabelPanel);
        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordPanel.setOpaque(false);
        confirmPasswordPanel.add(confirmPasswordField);
        mainPanel.add(confirmPasswordPanel);

        JPanel singUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        singUpPanel.setOpaque(false);
        singUpPanel.add(singUpButton);
        mainPanel.add(singUpPanel);

        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
        JLabel alreadyHaveAccount = new JLabel("Already have an account? ");
        alreadyHaveAccount.setFont(MinecraftFont.getFont());
        registerPanel.add(alreadyHaveAccount);
        registerPanel.add(loginButton);
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

        //set layouts
        setLayer(gridBagPanel, 2);

        add(background);
        add(form_background);
        add(gridBagPanel);

    }

    void init() {
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 20));
        usernameField.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 18));


        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 20));
        emailField.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 18));


        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20));
        //passwordField.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 18));

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 20));
        //confirmPasswordField.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 18));

        loginButton = new JButton("Login");
        loginButton.setFont(MinecraftFont.getFont());

        singUpButton = new JButton("Sign Up");
        singUpButton.setFont(MinecraftFont.getFont());

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

    @Override
    public void clear() {

    }
}
