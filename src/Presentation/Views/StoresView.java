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
    private JImagePanel background;
    private GeneratorsView generatorsPanel;
    private JPanel upgradesPanel;
    private ActionListener listener;

    public StoresView(ActionListener listener) throws IOException {
        this.listener = listener;
        setSize(800, 600);
        setLayout(new OverlayLayout(this));
        setOpaque(false);
        init(listener);
        mount();
    }

    private void init(ActionListener listener) throws IOException {
        cardLayout = new CardLayout();
        background = new JImagePanel(R.STORES_BACKGROUND);
        background.setResolution(JImagePanel.EXTEND_RES_HEIGHT);
        background.setVisible(true);
        generatorsButton = new JButton("Generators");
        upgradesButton = new JButton("Upgrades");
        generatorsPanel = new GeneratorsView(listener);

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

        JPanel storePanel = new JPanel(); //panell que conté les views de les dues botigues
        storePanel.setLayout(new BorderLayout());
        storePanel.add(new GeneratorsView(this.listener));

        panel.add(storePanel);
        mainPanel.setBorder(new EmptyBorder(49, 5, 0, 0));
        mainPanel.add(panel);

        layeredPane.setLayer(mainPanel, 1);
        layeredPane.setLayer(background, 0);

        layeredPane.add(mainPanel);
        layeredPane.add(background);

        add(layeredPane);
    }
}
