package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.R;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ImprovementsView extends JPanel {

    private ActionListener actionListener;
    private JScrollPane scrollPane;
    private JPanel mainPanel;

    public ImprovementsView(ActionListener listener) {
        this.actionListener = listener;
        init();
        mount();

        addImprovement(R.PILLS, "Pills", "100", "0");
        addImprovement(R.GLASSES, "Glasses", "200", "0");
        addImprovement(R.CARLOS, "Carlos", "300", "0");
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

    public void addImprovement(String picture, String name, String price, String amount) {
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

        mainPanel.add(generatorPanel);
        mainPanel.revalidate();
        scrollPane.revalidate();
    }

    public void removeImprovements() {
        this.mainPanel.removeAll();
        scrollPane.revalidate();
    }
}
