package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.JImagePanel;
import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;


public class RegisterView extends JLayeredPane implements MyView {

    private final ActionListener listener;
    private JTextField usernameField;

    private JTextField emailField;
    private JPasswordField passwordField;

    private JPasswordField confirmPasswordField;
    private JTexturedButton singUpButton;
    private JTexturedButton loginButton;

    public RegisterView(ActionListener listener) {
        setLayout(new OverlayLayout(this));
        this.listener = listener;

        init();

        JPanel gridBagPanel = createGridBagPanel();
        JPanel mainPanel = createMainPanel();

        gridBagPanel.add(mainPanel, createGridBagConstraints());

        try {
            JImagePanel background = createImagePanel(R.MAIN_BACKGROUND, JImagePanel.EXTEND_RES_WIDTH, 0);
            JImagePanel form_background = createImagePanel(R.FORM_BACKGROUND, -1, 1);

            add(background);
            add(form_background);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayer(gridBagPanel, 2);
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

        mainPanel.add(createLabelPanel("Sign Up", 60, FlowLayout.CENTER, new EmptyBorder(0, 0, 40, 0)));
        mainPanel.add(createFieldPanel("Username:", usernameField));
        mainPanel.add(createFieldPanel("Email:", emailField));
        mainPanel.add(createFieldPanel("Password:", passwordField));
        mainPanel.add(createFieldPanel("Confirm Password:", confirmPasswordField));
        mainPanel.add(createButtonPanel(singUpButton, new EmptyBorder(0, 0, 25, 0)));
        mainPanel.add(createRegisterPanel());

        return mainPanel;
    }

    private JPanel createLabelPanel(String text, int fontSize, int alignment, EmptyBorder border) {
        JPanel labelPanel = new JPanel(new FlowLayout(alignment));
        labelPanel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, fontSize));
        labelPanel.add(label);
        labelPanel.setBorder(border);
        return labelPanel;
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(MinecraftFont.getFont());
        labelPanel.add(label);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setOpaque(false);
        fieldPanel.add(field);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(labelPanel);
        panel.add(fieldPanel);

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
        JLabel alreadyHaveAccount = new JLabel("Already have an account? ");
        alreadyHaveAccount.setFont(MinecraftFont.getFont());
        registerPanel.add(alreadyHaveAccount);
        registerPanel.add(loginButton);
        registerPanel.setBorder(new EmptyBorder(30, 0, 20, 0));
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
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 20));
        usernameField.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 18));


        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 20));
        emailField.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 18));


        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20));

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 20));

        loginButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        loginButton.setText("Login");
        loginButton.setFont(MinecraftFont.getFont());

        singUpButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        singUpButton.setText("Sign Up");
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
    public void adviceMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidEmail() {
        JOptionPane.showMessageDialog(this, "Please enter a valid email address. Email address must contain '@gmail.com'.", "Mail Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidPassword8C() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least 8 characters.", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidPasswordLowerCase() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least one lowercase letter", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidPasswordUpperCase() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least one uppercase letter", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidPasswordOneNumber() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least one number", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    public void enterValidUsername() {
        JOptionPane.showMessageDialog(this, "Please enter a valid username. Username must be unique.", "Username Error", JOptionPane.ERROR_MESSAGE);
    }

    public void clearForm() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}
