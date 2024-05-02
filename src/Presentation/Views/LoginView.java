package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.JImagePanel;
import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class LoginView extends JLayeredPane implements MyView {

    private ActionListener listener;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton forgotPassword;
    private JTexturedButton loginButton;
    private JRadioButton rememberMe;
    private JTexturedButton registerButton;
    private JImagePanel background;
    private JImagePanel form_background;

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
        JLabel label = new JLabel("Login");
        label.setFont(Objects.requireNonNull(MinecraftFont.getFont()).deriveFont(Font.PLAIN, 60));
        loginLabel.add(label);
        loginLabel.setBorder(new EmptyBorder(20, 0, 50, 0));
        mainPanel.add(loginLabel);

        JPanel usernameLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernameLabel.setOpaque(false);
        JLabel emailLabel = new JLabel("E-mail:");
        emailLabel.setFont(MinecraftFont.getFont());
        usernameLabel.add(emailLabel);
        mainPanel.add(usernameLabel);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setOpaque(false);
        usernamePanel.add(usernameField);
        usernamePanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(usernamePanel);

        JPanel passwordLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordLabel.setOpaque(false);
        JLabel pl = new JLabel("Password:");
        pl.setFont(MinecraftFont.getFont());
        passwordLabel.add(pl);
        mainPanel.add(passwordLabel);
        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.add(passwordField);
        passwordPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
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
        loginPanel.setBorder(new EmptyBorder(10, 0, 50, 0));
        mainPanel.add(loginPanel);

        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
        JLabel dha = new JLabel("Don't have an account?");
        dha.setFont(MinecraftFont.getFont());
        registerPanel.add(dha);
        registerPanel.add(registerButton);
        registerPanel.setBorder(new EmptyBorder(15, 0, 40, 0));
        mainPanel.add(registerPanel);
        gridBagPanel.add(mainPanel, c);



        //image pannels
        try {
            background = new JImagePanel(R.MAIN_BACKGROUND);
            background.setResolution(JImagePanel.EXTEND_RES_WIDTH);
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
        background = new JImagePanel();
        form_background = new JImagePanel();

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 20));
        usernameField.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 20));

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 20));

        forgotPassword = new JButton("Forgot password?");
        forgotPassword.setOpaque(false);
        forgotPassword.setContentAreaFilled(false);
        forgotPassword.setBorderPainted(false);
        forgotPassword.setFont(MinecraftFont.getFont());

        loginButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        loginButton.setText("Login");
        loginButton.setFont(MinecraftFont.getFont());

        rememberMe = new JRadioButton("Remember me");
        rememberMe.setFont(MinecraftFont.getFont());
        rememberMe.setOpaque(false);

        registerButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        registerButton.setText("Register");
        registerButton.setFont(MinecraftFont.getFont());
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
    public void adviceMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
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

    @Override
    public void clear() {

    }
}
