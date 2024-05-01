package Presentation.Views;
import Presentation.Controllers.StoresController;
import Presentation.JImagePanel;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StoresView extends JPanel {
    private JButton generatorsButton;
    private JButton upgradesButton;
    private CardLayout cardLayout;
    private JPanel storePanel;
    private JImagePanel background;
    private GeneratorsView generatorsView;
    private ImprovementsView improvementsView;
    private ActionListener listener;

    public StoresView(ActionListener listener) throws IOException {
        this.listener = listener;
        setSize(800, 600);
        setLayout(new OverlayLayout(this));
        setOpaque(false);
        init();
        mount();
    }

    private void init() throws IOException {
        cardLayout = new CardLayout();
        background = new JImagePanel(R.STORES_BACKGROUND);
        background.setResolution(JImagePanel.EXTEND_RES_HEIGHT);
        background.setVisible(true);

        generatorsButton = new JButton("Generators");
        generatorsButton.addActionListener(listener);
        generatorsButton.setActionCommand("generators");

        upgradesButton = new JButton("Upgrades");
        upgradesButton.addActionListener(listener);
        upgradesButton.setActionCommand("upgrades");

        this.generatorsView = new GeneratorsView(this.listener);
        this.improvementsView = new ImprovementsView(this.listener);

        this.storePanel = new JPanel();             //panell que conté les views de les dues botigues
        storePanel.setLayout(this.cardLayout);

    }

    private void mount() throws IOException {

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setOpaque(false);

        JPanel panel = new JPanel();    //panell que conté bottons i altres components
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setPreferredSize(new Dimension(250, 425));

        JPanel buttonsPanel = new JPanel(new FlowLayout()); //panell que conté els botons
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(generatorsButton);
        buttonsPanel.add(upgradesButton);
        panel.add(buttonsPanel);


        storePanel.add("generators", this.generatorsView);
        storePanel.add("upgrades", this.improvementsView);

        panel.add(storePanel);
        mainPanel.setBorder(new EmptyBorder(49, 5, 0, 0));
        mainPanel.add(panel);

        layeredPane.setLayer(mainPanel, 1);
        layeredPane.setLayer(background, 0);

        layeredPane.add(mainPanel);
        layeredPane.add(background);

        add(layeredPane);
    }

    public void swapPanel(String panel) {
        cardLayout.show(storePanel, panel);
    }
}
