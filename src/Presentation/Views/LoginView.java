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

/**
 * Represents the view for user login.
 */
public class LoginView extends JLayeredPane implements MyView {

    private final ActionListener listener;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTexturedButton loginButton;
    private JTexturedButton registerButton;
    private JImagePanel background;
    private JImagePanel form_background;

    /**
     * Constructs a new instance of LoginView with the given ActionListener.
     *
     * @param listener The ActionListener to handle actions in the view.
     */
    public LoginView(ActionListener listener) {
        setLayout(new OverlayLayout(this));
        this.listener = listener;
        init();
        mount();
    }

    /**
     * Mounts the components onto the layered pane.
     */
    void mount() {
        JPanel gridBagPanel = createGridBagPanel();
        JPanel mainPanel = createMainPanel();

        gridBagPanel.add(mainPanel, createGridBagConstraints());

        try {
            background = createImagePanel(R.MAIN_BACKGROUND, JImagePanel.EXTEND_RES_WIDTH, 0);
            form_background = createImagePanel(R.FORM_BACKGROUND, -1, 1); // -1 indicates no resolution setting
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayer(gridBagPanel, 2);
        add(background);
        add(form_background);
        add(gridBagPanel);
    }

    /**
     * Creates a JPanel with GridBagLayout.
     *
     * @return The JPanel with GridBagLayout.
     */
    private JPanel createGridBagPanel() {
        JPanel gridBagPanel = new JPanel(new GridBagLayout());
        gridBagPanel.setOpaque(false);
        return gridBagPanel;
    }

    /**
     * Creates GridBagConstraints for the layout.
     *
     * @return The created GridBagConstraints.
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
     * Creates the main panel containing login components.
     *
     * @return The created main panel.
     */
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
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(300, 50));
        emptyPanel.setOpaque(false);
        mainPanel.add(emptyPanel);
        mainPanel.add(createButtonPanel(loginButton, new EmptyBorder(10, 0, 50, 0)));
        mainPanel.add(createRegisterPanel());

        return mainPanel;
    }

    /**
     * Creates the login label panel.
     *
     * @return The created login label panel.
     */
    private JPanel createLoginLabelPanel() {
        JPanel loginLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginLabel.setOpaque(false);
        JLabel label = new JLabel("Login");
        label.setFont(Objects.requireNonNull(MinecraftFont.getFont()).deriveFont(Font.PLAIN, 60));
        loginLabel.add(label);
        loginLabel.setBorder(new EmptyBorder(20, 0, 50, 0));
        return loginLabel;
    }

    /**
     * Creates a label panel with the given text and alignment.
     *
     * @param text      The text of the label.
     * @param alignment The alignment of the label.
     * @return The created label panel.
     */
    private JPanel createLabelPanel(String text, int alignment) {
        JPanel labelPanel = new JPanel(new FlowLayout(alignment));
        labelPanel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(MinecraftFont.getFont());
        labelPanel.add(label);
        return labelPanel;
    }

    /**
     * Creates a panel containing the given field with the specified border.
     *
     * @param field  The field to be added to the panel.
     * @param border The border of the panel.
     * @return The created field panel.
     */
    private JPanel createFieldPanel(JComponent field, EmptyBorder border) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setOpaque(false);
        fieldPanel.add(field);
        fieldPanel.setBorder(border);
        return fieldPanel;
    }

    /**
     * Creates a panel containing the given button with the specified border.
     *
     * @param button The button to be added to the panel.
     * @param border The border of the panel.
     * @return The created button panel.
     */
    private JPanel createButtonPanel(JButton button, EmptyBorder border) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(button);
        buttonPanel.setBorder(border);
        return buttonPanel;
    }

    /**
     * Creates the register panel.
     *
     * @return The created register panel.
     */
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

    /**
     * Creates an image panel with the specified resource path, resolution, and layer.
     *
     * @param resourcePath The path to the image resource.
     * @param resolution   The resolution setting for the image panel.
     * @param layer        The layer index for the layered pane.
     * @return The created image panel.
     * @throws IOException If an IO error occurs while loading the image.
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
        background = new JImagePanel();
        form_background = new JImagePanel();

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 20));
        usernameField.setFont(MinecraftFont.getFont().deriveFont(Font.PLAIN, 20));

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 20));

        loginButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        loginButton.setText("Login");
        loginButton.setFont(MinecraftFont.getFont());

        registerButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        registerButton.setText("Register");
        registerButton.setFont(MinecraftFont.getFont());

        start();
    }

    /**
     * Starts the view.
     */
    @Override
    public void start() {
        loginButton.addActionListener(listener);
        loginButton.setActionCommand("login");

        registerButton.addActionListener(listener);
        registerButton.setActionCommand("register");
    }

    /**
     * Stops the view.
     */
    @Override
    public void stop() {
        loginButton.removeActionListener(listener);
        registerButton.removeActionListener(listener);
    }

    /**
     * Clears the view.
     */
    @Override
    public void clear() {
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * Retrieves the information entered by the user.
     *
     * @return An array containing the username and password.
     */
    public String[] getInfo() {
        return new String[] {
                "username:"+usernameField.getText(),
                "password:"+ Arrays.toString(passwordField.getPassword()),
        };
    }

    /**
     * Displays an error message for an invalid email.
     */
    public void enterValidEmail() {
        JOptionPane.showMessageDialog(this, "Please enter a valid email address. Email address must contain '@gmail.com'.", "Mail Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an error message for an invalid password.
     */
    public void enterValidPassword() {
        JOptionPane.showMessageDialog(this, "Please enter a valid password. Password must contain at least 7 characters.", "Password Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays an advice message.
     *
     * @param message The message to display.
     * @param title   The title of the message dialog.
     */
    public void adviceMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Clears the login form.
     */
    public void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
