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

/**
 * View for user registration.
 */
public class RegisterView extends JLayeredPane implements MyView {

    private final ActionListener listener;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTexturedButton singUpButton;
    private JTexturedButton loginButton;

    /**
     * Constructs a new RegisterView.
     * @param listener The ActionListener for handling events.
     */
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

    /**
     * Creates a JPanel with GridBagLayout.
     * @return JPanel with GridBagLayout.
     */
    private JPanel createGridBagPanel() {
        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        gridBagPanel.setOpaque(false);
        return gridBagPanel;
    }

    /**
     * Creates GridBagConstraints for layout positioning.
     * @return GridBagConstraints object.
     */
    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridheight = 1;
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        return c;
    }

    /**
     * Creates the main panel containing registration components.
     * @return JPanel with registration components.
     */
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

    /**
     * Creates a JPanel with a label.
     * @param text The text of the label.
     * @param fontSize The font size of the label.
     * @param alignment The alignment of the label.
     * @param border The border of the panel.
     * @return JPanel with a label.
     */
    private JPanel createLabelPanel(String text, int fontSize, int alignment, EmptyBorder border) {
        JPanel labelPanel = new JPanel(new FlowLayout(alignment));
        labelPanel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, fontSize));
        labelPanel.add(label);
        labelPanel.setBorder(border);
        return labelPanel;
    }

    /**
     * Creates a JPanel containing a label and a field.
     * @param labelText The text of the label.
     * @param field The text field component.
     * @return JPanel containing the label and field.
     */
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

    /**
     * Creates a JPanel containing a button.
     * @param button The button component.
     * @param border The border of the panel.
     * @return JPanel containing the button.
     */
    private JPanel createButtonPanel(JButton button, EmptyBorder border) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(button);
        buttonPanel.setBorder(border);
        return buttonPanel;
    }

    /**
     * Creates a JPanel containing the registration link.
     * @return JPanel containing the registration link.
     */
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

    /**
     * Creates a JImagePanel from the given resource path.
     * @param resourcePath The resource path of the image.
     * @param resolution The resolution of the image.
     * @param layer The layer of the panel.
     * @return JImagePanel with the specified properties.
     * @throws IOException If an I/O error occurs.
     */
    private JImagePanel createImagePanel(String resourcePath, int resolution, int layer) throws IOException {
        JImagePanel imagePanel = new JImagePanel(resourcePath);
        if (resolution != -1) {
            imagePanel.setResolution(resolution);
        }
        setLayer(imagePanel, layer);
        return imagePanel;
    }

    /**
     * Initializes the components of the view.
     */
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

    /**
     * Starts the view by adding action listeners to buttons.
     */
    @Override
    public void start() {
        loginButton.addActionListener(listener);
        loginButton.setActionCommand("login");

        singUpButton.addActionListener(listener);
        singUpButton.setActionCommand("singUp");
    }

    /**
     * Stops the view by removing action listeners from buttons.
     */
    @Override
    public void stop() {
        loginButton.removeActionListener(listener);
        singUpButton.removeActionListener(listener);
    }

    /**
     * Clears the view.
     */
    @Override
    public void clear() {

    }

    /**
     * Retrieves the registration information entered by the user.
     * @return Array containing username, email, password, and confirm password.
     */
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

    /**
     * Displays an advice message.
     * @param message The message to be displayed.
     * @param title The title of the message dialog.
     */
    public void adviceMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an error message for invalid email.
     */
    public void enterValidEmail() {
        JOptionPane.showMessageDialog(this, "Please enter a valid email address. Email address must contain '@gmail.com'.", "Mail Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an error message for invalid password length.
     */
    public void enterValidPassword8C() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least 8 characters.", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an error message for missing lowercase letters in the password.
     */
    public void enterValidPasswordLowerCase() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least one lowercase letter", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an error message for missing uppercase letters in the password.
     */
    public void enterValidPasswordUpperCase() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least one uppercase letter", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an error message for missing numbers in the password.
     */
    public void enterValidPasswordOneNumber() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least one number", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an error message for invalid username.
     */
    public void enterValidUsername() {
        JOptionPane.showMessageDialog(this, "Please enter a valid username. Username must be unique.", "Username Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Clears the registration form fields.
     */
    public void clearForm() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}
