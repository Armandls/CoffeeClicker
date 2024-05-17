package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.FrameController;
import Presentation.JHoverPanel;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ImprovementsView extends JPanel {

    public static final String basicImprovementDesc = "Aquestes pastilles combinades amb el teu RedBull\nson una bomba d'hiperactivitat que et permet\nestudiar a nivells inimaginables.\n\nNo et preocupis, no et faran mal.";
    public static final String midImprovementDesc = "Tantes hores llegint fan mal.\nMillor posa't unes ulleres que els oculistes\nsón cars!";
    public static final String highImprovementDesc = "Que cols que t'hi digui, si no fós \nper aquest home, molta gent suspendria Mates.";
    private ActionListener actionListener;
    private FrameController controller;
    private JScrollPane scrollPane;
    private JPanel mainPanel;

    public ImprovementsView(ActionListener listener, FrameController controller) {
        this.actionListener = listener;
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
        this.scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addImprovement(String picture, String name, String price, String amount, String description) {
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
        button.setActionCommand("improvementsBuy:" + name);

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        buttonPanel.add(button, BorderLayout.CENTER);

        generatorPanel.setLayer(buttonPanel, 1);
        generatorPanel.add(buttonPanel);


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

        //JHoverPanel hoverPanel = new JHoverPanel(button, descriptionPanel);
        //controller.addHoverPanel(hoverPanel);

        mainPanel.add(generatorPanel);
        mainPanel.revalidate();
        scrollPane.revalidate();
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
}
