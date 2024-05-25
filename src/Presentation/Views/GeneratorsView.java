package Presentation.Views;

import Presentation.Fonts.MinecraftFont;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View class for displaying generators.
 */
public class GeneratorsView extends JPanel {

    public static final String basicGenDesc = "Aquest generador t'omple de cafeïna i et\npermet estudiar més temps.\n\nRedBull et dóna ales!";
    public static final String midGenDesc = "Els teus companys són molt bones persones\ni et donen els seus apunts per tal que\npuguis entendre les assignatures.\n\nSpoiler: no ho entendràs igualment.";
    public static final String highGenDesc = "En vistes de que res funciona i no tens\ntemps per estudiar, has decidit fer un\npacte amb el diable i apuntar-te a la CEUS.\n\nBenvingut a la foscor.";

    private final ActionListener actionListener;
    private final ListSelectionListener listSelectionListener;
    private JTable generatorsTable;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JPanel tablePanel;

    /**
     * Constructor for GeneratorsView.
     *
     * @param listener            ActionListener for handling button clicks.
     * @param listSelectionListener ListSelectionListener for handling row selections in the table.
     */
    public GeneratorsView(ActionListener listener, ListSelectionListener listSelectionListener) {
        this.actionListener = listener;
        this.listSelectionListener = listSelectionListener;
        init();
        mount();
        setVisible(true);
    }

    private void init() {
        setLayout(new BorderLayout());
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setBackground(Color.BLACK);

        String[] columnNames = {"Name", "Amount", "Price c/s", "Total credits/s", "% global production"};
        Object[][] data = {
                {"Redbull", 0, "0.2", "0", "0"},
                {"Notes", 0, "1.15", "0", "0"},
                {"CEUS", 0, "15", "0", "0"}
        };

        generatorsTable = new JTable(data, columnNames);
        generatorsTable.setBackground(Color.BLACK);
        generatorsTable.setFont(MinecraftFont.getFont());
        generatorsTable.setRowHeight(30);
        generatorsTable.setDefaultRenderer(Object.class, new JTableRender());
        generatorsTable.getSelectionModel().addListSelectionListener(this.listSelectionListener);
        generatorsTable.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(java.util.EventObject e) {
                return false;
            }
        });

        JTableHeader tableHeader = generatorsTable.getTableHeader();
        tableHeader.setFont(MinecraftFont.getFont());
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);

        scrollPane = new JScrollPane(generatorsTable);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setPreferredSize(new Dimension(375, 117));

        tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tablePanel.setOpaque(true);
        tablePanel.add(scrollPane);
        tablePanel.setPreferredSize(new Dimension(400, 100));
    }

    private void mount() {
        JPanel generatorsPanel = new JPanel(new GridLayout(1, 4));
        generatorsPanel.setBackground(Color.BLACK);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        generatorsPanel.add(emptyPanel);
        generatorsPanel.add(new JLabel("Name"));
        generatorsPanel.add(new JLabel("Price"));
        generatorsPanel.add(new JLabel("Amount"));
        mainPanel.add(generatorsPanel);
        this.scrollPane = new JScrollPane(tablePanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Adds a generator to the view.
     *
     * @param picture     The picture of the generator.
     * @param name        The name of the generator.
     * @param price       The price of the generator.
     * @param amount      The amount of the generator.
     * @param description The description of the generator.
     */
    public void addGenerator(String picture, String name, String price, String amount, String description) {
        JLayeredPane generatorPanel = createGeneratorPanel();

        JPanel panel = createInfoPanel(picture, name, price, amount);
        generatorPanel.setLayer(panel, 0);
        generatorPanel.add(panel);

        JPanel buttonPanel = createButtonPanel(name);
        generatorPanel.setLayer(buttonPanel, 1);
        generatorPanel.add(buttonPanel);

        mainPanel.add(generatorPanel);
        mainPanel.revalidate();
        scrollPane.revalidate();

        JPanel descriptionPanel = createDescriptionPanel(description);
    }


    /**
     * Creates a layered pane for displaying generators.
     *
     * @return The created JLayeredPane for generators.
     */
    private JLayeredPane createGeneratorPanel() {
        JLayeredPane generatorPanel = new JLayeredPane();
        generatorPanel.setLayout(new OverlayLayout(generatorPanel));
        generatorPanel.setPreferredSize(new Dimension(245, 50));
        return generatorPanel;
    }

    /**
     * Creates a panel containing information about a generator.
     *
     * @param picture The path to the image of the generator.
     * @param name The name of the generator.
     * @param price The price of the generator.
     * @param amount The amount of the generator.
     * @return The JPanel containing generator information.
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
     * Loads an image from the specified path and scales it to fit within a 50x50 area.
     *
     * @param picture The path to the image file.
     * @return The scaled ImageIcon.
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
     * @param text The text to display on the label.
     * @param color The color of the text.
     * @return The created JLabel.
     */
    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(MinecraftFont.getFont());
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a panel containing a button for buying a generator.
     *
     * @param name The name of the generator.
     * @return The JPanel containing the button.
     */
    private JPanel createButtonPanel(String name) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);

        JButton button = new JButton();
        button.addActionListener(actionListener);
        button.setActionCommand("generatorsBuy:" + name);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        buttonPanel.add(button, BorderLayout.CENTER);
        return buttonPanel;
    }

    /**
     * Creates a panel containing a description of a generator.
     *
     * @param description The description of the generator.
     * @return The JPanel containing the description.
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
     * Removes all generators from the view.
     */
    public void removeGenerators() {
        this.mainPanel.removeAll();
        JPanel generatorsPanel = new JPanel(new GridLayout(1, 4));
        generatorsPanel.setBackground(Color.BLACK);
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);
        generatorsPanel.add(emptyPanel);
        generatorsPanel.add(new JLabel("Name"));
        generatorsPanel.add(new JLabel("Price"));
        generatorsPanel.add(new JLabel("Amount"));
        mainPanel.add(generatorsPanel);

        scrollPane.revalidate();
    }

    /**
     * Updates the table with new data.
     *
     * @param quantities                 The quantities of each generator.
     * @param totalCreditsPerSecond      The total credits per second of each generator.
     * @param globalProductionPercentages The global production percentages of each generator.
     * @param prices                     The prices of each generator.
     */
    public void updateTable(int[] quantities, float[] totalCreditsPerSecond, float[] globalProductionPercentages, int[] prices) {
        for (int i = 0; i < quantities.length; i++) {
            generatorsTable.setValueAt(quantities[i], i, 1);
            generatorsTable.setValueAt(prices[i], i, 2);
            generatorsTable.setValueAt(String.format("%.2f", totalCreditsPerSecond[i]), i, 3);
            generatorsTable.setValueAt(String.format("%.2f", globalProductionPercentages[i]), i, 4);
        }
    }
}
