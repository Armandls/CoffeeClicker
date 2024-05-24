package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.FrameController;
import Presentation.JHoverPanel;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

public class GeneratorsView extends JPanel {
    public static final String basicGenDesc = "Aquest generador t'omple de cafeïna i et\npermet estudiar més temps.\n\nRedBull et dóna ales!";
    public static final String midGenDesc = "Els teus companys són molt bones persones\ni et donen els seus apunts per tal que\npuguis entendre les assignatures.\n\nSpoiler: no ho entendràs igualment.";
    public static final String highGenDesc = "En vistes de que res funciona i no tens\ntemps per estudiar, has decidit fer un\npacte amb el diable i apuntar-te a la CEUS.\n\nBenvingut a la foscor.";

    private ActionListener actionListener;
    private ListSelectionListener listSelectionListener;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private FrameController controller;
    private JTable generatorsTable;
    private DefaultTableModel tableModel;
    private JPanel tablePanel;

    public GeneratorsView(ActionListener listener, ListSelectionListener listSelectionListener, FrameController controller) {
        this.actionListener = listener;
        this.listSelectionListener = listSelectionListener;
        this.controller = controller;
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

        tableModel = new DefaultTableModel(data, columnNames);
        generatorsTable = new JTable(tableModel);
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

    public void addGenerator(String picture, String name, String price, String amount, String description) {
        JLayeredPane generatorPanel = new JLayeredPane();
        generatorPanel.setLayout(new OverlayLayout(generatorPanel));
        generatorPanel.setPreferredSize(new Dimension(245, 50));

        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBackground(Color.BLACK);
        panel.setOpaque(false);

        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + picture);
        int value = image.getIconWidth() - image.getIconHeight();
        if (value >= 0) {

            image = new ImageIcon(image.getImage().getScaledInstance(50, (image.getIconHeight()*50)/image.getIconWidth(), Image.SCALE_DEFAULT));
        }
        else {
            image = new ImageIcon(image.getImage().getScaledInstance((image.getIconWidth()*50)/image.getIconHeight(), 50, Image.SCALE_DEFAULT));
        }
        panel.add(new JLabel(image));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(MinecraftFont.getFont());
        nameLabel.setForeground(new Color(24, 176, 0));
        panel.add(nameLabel);

        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(MinecraftFont.getFont());
        priceLabel.setForeground(new Color(0, 102, 204));
        panel.add(priceLabel);

        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(MinecraftFont.getFont());
        amountLabel.setForeground(Color.WHITE);
        panel.add(amountLabel);

        generatorPanel.setLayer(panel, 0);
        generatorPanel.add(panel);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        JButton button = new JButton();

        button.addActionListener(actionListener);
        button.setActionCommand("generatorsBuy:" + name);

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        buttonPanel.add(button, BorderLayout.CENTER);

        generatorPanel.setLayer(buttonPanel, 1);
        generatorPanel.add(buttonPanel);

        mainPanel.add(generatorPanel);
        mainPanel.revalidate();
        scrollPane.revalidate();

        JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descriptionPanel.setOpaque(true);
        descriptionPanel.setSize(300, 100);
        JTextArea descriptionLabel = new JTextArea(description);
        descriptionLabel.setBackground(Color.darkGray);
        descriptionLabel.setPreferredSize(new Dimension(290, 90));
        descriptionLabel.setFont(MinecraftFont.getFontWithSize(16));
        descriptionLabel.setForeground(Color.cyan);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.setBackground(Color.darkGray);
        descriptionPanel.setBorder(new StrokeBorder(new BasicStroke(1.0f), Color.YELLOW));
        //JHoverPanel hoverPanel = new JHoverPanel(button, descriptionPanel, name);
        this.controller.removeHoverPanel(name);
        //controller.addHoverPanel(hoverPanel);
    }

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

    public void updateTable(int[] quantities, float[] totalCreditsPerSecond, float[] globalProductionPercentages, int[] prices) {
        for (int i = 0; i < quantities.length; i++) {
            tableModel.setValueAt(prices[i], i, 2);
            tableModel.setValueAt(quantities[i], i, 1);
            tableModel.setValueAt(String.format("%.2f ", totalCreditsPerSecond[i]), i, 3);
            tableModel.setValueAt(String.format("%.2f ", globalProductionPercentages[i]), i, 4);
        }
    }
}
