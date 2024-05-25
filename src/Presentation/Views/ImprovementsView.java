package Presentation.Views;

import Presentation.Fonts.MinecraftFont;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

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

    public ImprovementsView(ActionListener listener, ListSelectionListener listSelectionListener) {
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

    private JLayeredPane createImprovementPanel() {
        JLayeredPane improvementPanel = new JLayeredPane();
        improvementPanel.setLayout(new OverlayLayout(improvementPanel));
        improvementPanel.setPreferredSize(new Dimension(245, 50));
        return improvementPanel;
    }

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

    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(MinecraftFont.getFont());
        label.setForeground(color);
        return label;
    }

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

    public void updateTable(int[] quantities) {
        for (int i = 0; i < quantities.length; i++) {
            improvementsTable.setValueAt(quantities[i], i, 1);
        }
    }
}
