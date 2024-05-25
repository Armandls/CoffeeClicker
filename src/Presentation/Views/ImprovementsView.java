package Presentation.Views;

import Presentation.Fonts.MinecraftFont;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The view for managing improvements in the game.
 */
public class ImprovementsView extends JPanel {

    public static final String basicImprovementDesc = "Aquestes pastilles combinades amb el teu RedBull\nson una bomba d'hiperactivitat que et permet\nestudiar a nivells inimaginables.\n\nNo et preocupis, no et faran mal.";
    public static final String midImprovementDesc = "Tantes hores llegint fan mal.\nMillor posa't unes ulleres que els oculistes\nsón cars!";
    public static final String highImprovementDesc = "Que cols que t'hi digui, si no fós \nper aquest home, molta gent suspendria Mates.";
    private final ActionListener actionListener;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JTable improvementsTable;
    private JPanel tablePanel;
    private final ListSelectionListener listSelectionListener;

    /**
     * Constructs a new ImprovementsView with the specified ActionListener and ListSelectionListener.
     *
     * @param listener             The ActionListener for handling events.
     * @param listSelectionListener The ListSelectionListener for handling table selection events.
     */
    public ImprovementsView(ActionListener listener, ListSelectionListener listSelectionListener) {
        this.actionListener = listener;
        this.listSelectionListener = listSelectionListener;
        init();
        mount();
        setVisible(true);
    }

    /**
     * Initializes the UI components.
     */
    private void init() {
        setLayout(new BorderLayout());
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setBackground(Color.BLACK);

        String[] columnNames = {"Name", "Amount", "Price"};
        Object[][] data = {
                {"Pills", 0, "100"},
                {"Glasses", 0, "250"},
                {"Carlos", 0, "500"}
        };

        improvementsTable = new JTable(data, columnNames);
        improvementsTable.setBackground(Color.BLACK);
        improvementsTable.setFont(MinecraftFont.getFont());
        improvementsTable.setRowHeight(30);
        improvementsTable.setDefaultRenderer(Object.class, new JTableRender());
        improvementsTable.getSelectionModel().addListSelectionListener(this.listSelectionListener);
        improvementsTable.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(java.util.EventObject e) {
                return false;
            }
        });

        JTableHeader tableHeader = improvementsTable.getTableHeader();
        tableHeader.setFont(MinecraftFont.getFont());
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);

        scrollPane = new JScrollPane(improvementsTable);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setPreferredSize(new Dimension(375, 117));

        tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tablePanel.setOpaque(true);
        tablePanel.add(scrollPane);
        tablePanel.setPreferredSize(new Dimension(400, 100));
    }

    /**
     * Mounts the UI components.
     */
    private void mount() {
        JPanel improvementsPanel = new JPanel(new GridLayout(1, 4));
        improvementsPanel.setBackground(Color.BLACK);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        improvementsPanel.add(emptyPanel);
        improvementsPanel.add(new JLabel("Name"));
        improvementsPanel.add(new JLabel("Price"));
        improvementsPanel.add(new JLabel("Amount"));
        mainPanel.add(improvementsPanel);
        this.scrollPane = new JScrollPane(tablePanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Adds an improvement to the view.
     *
     * @param picture     The picture of the improvement.
     * @param name        The name of the improvement.
     * @param price       The price of the improvement.
     * @param amount      The amount of the improvement.
     * @param description The description of the improvement.
     */
    public void addImprovement(String picture, String name, String price, String amount, String description) {
        JLayeredPane improvementPanel = createImprovementPanel();

        JPanel panel = createInfoPanel(picture, name, price, amount);
        improvementPanel.setLayer(panel, 0);
        improvementPanel.add(panel);

        JPanel buttonPanel = createButtonPanel(name, "improvementsBuy");
        improvementPanel.setLayer(buttonPanel, 1);
        improvementPanel.add(buttonPanel);

        mainPanel.add(improvementPanel);
        mainPanel.revalidate();
        scrollPane.revalidate();

        JPanel descriptionPanel = createDescriptionPanel(description);
    }

    /**
     * Creates a layered pane for an improvement panel.
     *
     * @return The created layered pane.
     */
    private JLayeredPane createImprovementPanel() {
        JLayeredPane improvementPanel = new JLayeredPane();
        improvementPanel.setLayout(new OverlayLayout(improvementPanel));
        improvementPanel.setPreferredSize(new Dimension(245, 50));
        return improvementPanel;
    }

    /**
     * Creates the panel for displaying improvement information.
     *
     * @param picture The picture of the improvement.
     * @param name    The name of the improvement.
     * @param price   The price of the improvement.
     * @param amount  The amount of the improvement.
     * @return The created panel.
     */
    private JPanel createInfoPanel(String picture, String name, String price, String amount) {
        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBackground(Color.BLACK);
        panel.setOpaque(false);

        ImageIcon image = loadImage(picture);
        panel.add(new JLabel(image));

        JLabel nameLabel = createLabel(name, new Color(24, 176, 0));
        panel.add(nameLabel);

        JLabel priceLabel = createLabel(price, new Color(238, 255, 20));
        panel.add(priceLabel);

        JLabel amountLabel = createLabel(amount, Color.WHITE);
        panel.add(amountLabel);

        return panel;
    }

    /**
     * Loads an image.
     *
     * @param picture The path to the image file.
     * @return The loaded image.
     */
    private ImageIcon loadImage(String picture) {
        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + picture);
        int value = image.getIconWidth() - image.getIconHeight();
        if (value >= 0) {
            image = new ImageIcon(image.getImage().getScaledInstance(50, (image.getIconHeight() * 50) / image.getIconWidth(), Image.SCALE_DEFAULT));
        } else {
            image = new ImageIcon(image.getImage().getScaledInstance((image.getIconWidth() * 50) / image.getIconHeight(), 50, Image.SCALE_DEFAULT));
        }
        return image;
    }

    /**
     * Creates a JLabel with the specified text and color.
     *
     * @param text  The text of the label.
     * @param color The color of the label.
     * @return The created label.
     */
    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(MinecraftFont.getFont());
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a panel for a button with the specified name and action prefix.
     *
     * @param name         The name of the button.
     * @param actionPrefix The action prefix for the button's action command.
     * @return The created button panel.
     */
    private JPanel createButtonPanel(String name, String actionPrefix) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);

        JButton button = new JButton();
        button.addActionListener(actionListener);
        button.setActionCommand(actionPrefix + ":" + name);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        buttonPanel.add(button, BorderLayout.CENTER);
        return buttonPanel;
    }

    /**
     * Creates a panel for displaying a description with the specified text.
     *
     * @param description The description text.
     * @return The created description panel.
     */
    private JPanel createDescriptionPanel(String description) {
        JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descriptionPanel.setOpaque(true);
        descriptionPanel.setSize(300, 100);
        descriptionPanel.setBackground(Color.darkGray);
        descriptionPanel.setBorder(new StrokeBorder(new BasicStroke(1.0f), Color.YELLOW));

        JTextArea descriptionLabel = new JTextArea(description);
        descriptionLabel.setBackground(Color.darkGray);
        descriptionLabel.setPreferredSize(new Dimension(290, 90));
        descriptionLabel.setFont(MinecraftFont.getFontWithSize(16));
        descriptionLabel.setForeground(Color.yellow);
        descriptionPanel.add(descriptionLabel);

        return descriptionPanel;
    }

    /**
     * Removes all improvements from the view.
     */
    public void removeImprovements() {
        this.mainPanel.removeAll();

        JPanel improvementsPanel = new JPanel(new GridLayout(1, 4));
        improvementsPanel.setBackground(Color.BLACK);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        improvementsPanel.add(emptyPanel);
        improvementsPanel.add(new JLabel("Name"));
        improvementsPanel.add(new JLabel("Price"));
        improvementsPanel.add(new JLabel("Amount"));
        mainPanel.add(improvementsPanel);

        scrollPane.revalidate();
    }

    /**
     * Updates the table with the specified quantities.
     *
     * @param quantities The quantities to update the table with.
     */
    public void updateTable(int[] quantities) {
        for (int i = 0; i < quantities.length; i++) {
            improvementsTable.setValueAt(quantities[i], i, 1);
        }
    }
}
