package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.FrameController;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
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
    private DefaultTableModel tableModel;
    private JPanel tablePanel;
    private final ListSelectionListener listSelectionListener;

    public ImprovementsView(ActionListener listener, ListSelectionListener listSelectionListener, FrameController controller) {
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
                {"Pills", 0, "0.2", "0", "0"},
                {"Glasses", 0, "1.15", "0", "0"},
                {"Carlos", 0, "15", "0", "0"}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        JTable generatorsTable = new JTable(tableModel);
        generatorsTable.setBackground(Color.BLACK);
        generatorsTable.setFont(MinecraftFont.getFont());
        generatorsTable.setRowHeight(30);
        generatorsTable.setDefaultRenderer(Object.class, new JTableRender());
        generatorsTable.getSelectionModel().addListSelectionListener(this.listSelectionListener);

        JTableHeader tableHeader = generatorsTable.getTableHeader();
        tableHeader.setFont(MinecraftFont.getFont());
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(generatorsTable);
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

    public void addImprovement(String picture, String name, String price, String amount, String description) {
        JLayeredPane generatorPanel = createGeneratorPanel();

        JPanel panel = createInfoPanel(picture, name, price, amount);
        generatorPanel.setLayer(panel, 0);
        generatorPanel.add(panel);

        JPanel buttonPanel = createButtonPanel(name, "improvementsBuy");
        generatorPanel.setLayer(buttonPanel, 1);
        generatorPanel.add(buttonPanel);

        mainPanel.add(generatorPanel);
        mainPanel.revalidate();
        scrollPane.revalidate();

        JPanel descriptionPanel = createDescriptionPanel(description);
    }

    private JLayeredPane createGeneratorPanel() {
        JLayeredPane generatorPanel = new JLayeredPane();
        generatorPanel.setLayout(new OverlayLayout(generatorPanel));
        generatorPanel.setPreferredSize(new Dimension(245, 50));
        return generatorPanel;
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

    public void updateTable(int[] quantities, float[] totalCreditsPerSecond, float[] globalProductionPercentages) {
        for (int i = 0; i < quantities.length; i++) {
            tableModel.setValueAt(quantities[i], i, 1);
            tableModel.setValueAt(String.format("%.2f ", totalCreditsPerSecond[i]), i, 3);
            tableModel.setValueAt(String.format("%.2f ", globalProductionPercentages[i]), i, 4);
        }
    }
}
