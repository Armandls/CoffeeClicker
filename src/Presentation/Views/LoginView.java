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

    private final ActionListener listener;
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
        JPanel gridBagPanel = createGridBagPanel();
        JPanel mainPanel = createMainPanel();

        gridBagPanel.add(mainPanel, createGridBagConstraints());

        try {
            background = createImagePanel(R.MAIN_BACKGROUND, JImagePanel.EXTEND_RES_WIDTH, 0);
            form_background = createImagePanel(R.FORM_BACKGROUND, -1, 1); // -1 indicates no resolution setting
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }

        setLayer(gridBagPanel, 2);
        add(background);
        add(form_background);
        add(gridBagPanel);
    }

    private JPanel createGridBagPanel() {
        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        gridBagPanel.setOpaque(false);
        return gridBagPanel;
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridheight = 1;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        return c;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(300, 500);

        mainPanel.add(createLoginLabelPanel());
        mainPanel.add(createLabelPanel("E-mail:", FlowLayout.LEFT));
        mainPanel.add(createFieldPanel(usernameField, new EmptyBorder(0, 0, 20, 0)));
        mainPanel.add(createLabelPanel("Password:", FlowLayout.LEFT));
        mainPanel.add(createFieldPanel(passwordField, new EmptyBorder(0, 0, 20, 0)));
        mainPanel.add(createRememberMePanel());
        mainPanel.add(createButtonPanel(loginButton, new EmptyBorder(10, 0, 50, 0)));
        mainPanel.add(createRegisterPanel());

        return mainPanel;
    }

    private JPanel createLoginLabelPanel() {
        JPanel loginLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginLabel.setOpaque(false);
        JLabel label = new JLabel("Login");
        label.setFont(Objects.requireNonNull(MinecraftFont.getFont()).deriveFont(Font.PLAIN, 60));
        loginLabel.add(label);
        loginLabel.setBorder(new EmptyBorder(20, 0, 50, 0));
        return loginLabel;
    }

    private JPanel createLabelPanel(String text, int alignment) {
        JPanel labelPanel = new JPanel(new FlowLayout(alignment));
        labelPanel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(MinecraftFont.getFont());
        labelPanel.add(label);
        return labelPanel;
    }

    private JPanel createFieldPanel(JComponent field, EmptyBorder border) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setOpaque(false);
        fieldPanel.add(field);
        fieldPanel.setBorder(border);
        return fieldPanel;
    }

    private JPanel createRememberMePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(rememberMe);
        panel.add(forgotPassword);
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));
        return panel;
    }

    private JPanel createButtonPanel(JButton button, EmptyBorder border) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(button);
        buttonPanel.setBorder(border);
        return buttonPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
        JLabel dha = new JLabel("Don't have an account?");
        dha.setFont(MinecraftFont.getFont());
        registerPanel.add(dha);
        registerPanel.add(registerButton);
        registerPanel.setBorder(new EmptyBorder(15, 0, 40, 0));
        return registerPanel;
    }

    private JImagePanel createImagePanel(String resourcePath, int resolution, int layer) throws IOException {
        JImagePanel imagePanel = new JImagePanel(resourcePath);
        if (resolution != -1) {
            imagePanel.setResolution(resolution);
        }
        setLayer(imagePanel, layer);
        return imagePanel;
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
    public void enterValidEmail() {
        JOptionPane.showMessageDialog(this, "Please enter a valid email address. Email address must contain '@gmail.com'.", "Mail Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidPassword() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least 7 characters.", "Password Error", JOptionPane.ERROR_MESSAGE);
    }
    public void adviceMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
    }


}
