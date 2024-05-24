package Presentation.Views;
import Business.Entities.Generator.BasicGenerator;
import Presentation.Controllers.StoresController;
import Presentation.Fonts.MinecraftFont;
import Presentation.FrameController;
import Presentation.JImagePanel;
import Presentation.JTexturedButton;
import Presentation.R;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StoresView extends JPanel implements MyView {
    private JTexturedButton generatorsButton;
    private JTexturedButton upgradesButton;
    private CardLayout cardLayout;
    private JPanel storePanel;
    private JImagePanel background;
    private GeneratorsView generatorsView;
    private ImprovementsView improvementsView;
    private ActionListener listener;
    private FrameController controller;

    public StoresView(ActionListener listener, FrameController frameController) throws IOException {
        this.listener = listener;
        this.controller = frameController;
        setSize(800, 600);
        setLayout(new OverlayLayout(this));
        setOpaque(false);
        init();
        mount();
    }

    private void init() throws IOException {
        cardLayout = new CardLayout();
        background = new JImagePanel(R.STORES_BACKGROUND);
        background.setResolution(JImagePanel.EXTEND_RES_WIDTH);
        background.setVisible(true);

        generatorsButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        generatorsButton.setText("Generators");
        generatorsButton.setFont(MinecraftFont.getFont());
        generatorsButton.addActionListener(listener);
        generatorsButton.setActionCommand("generators");

        upgradesButton = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        upgradesButton.setText("Upgrades");
        upgradesButton.setFont(MinecraftFont.getFont());
        upgradesButton.addActionListener(listener);
        upgradesButton.setActionCommand("upgrades");

       this.generatorsView = new GeneratorsView(this.listener, (ListSelectionListener)this.listener, controller);
        this.improvementsView = new ImprovementsView(this.listener, (ListSelectionListener)this.listener, controller);

        this.storePanel = new JPanel();             //panell que conté les views de les dues botigues
        storePanel.setBackground(Color.BLACK);
        storePanel.setLayout(this.cardLayout);

    }

    private void mount() throws IOException {

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setOpaque(false);

        JPanel panel = new JPanel();    //panell que conté bottons i altres components
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setPreferredSize(new Dimension(275, 450));

        JPanel buttonsPanel = new JPanel(new FlowLayout()); //panell que conté els botons
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(generatorsButton);
        buttonsPanel.add(upgradesButton);
        panel.add(buttonsPanel);

        storePanel.add("generators", this.generatorsView);
        storePanel.add("upgrades", this.improvementsView);

        panel.add(storePanel);
        mainPanel.setBorder(new EmptyBorder(25, 5, 0, 0));
        mainPanel.add(panel);

        layeredPane.setLayer(mainPanel, 1);
        layeredPane.setLayer(background, 0);

        layeredPane.add(mainPanel);
        layeredPane.add(background);

        add(layeredPane);
    }

    public void swapPanel(String name) {
        this.cardLayout.show(storePanel, name);
    }

    public void initialize (int basicGenerator, int midGenerator, int highGenerator, int lvlBasicImp, int lvlMidImp, int lvlHighImp) {
        this.generatorsView.removeGenerators();
        this.generatorsView.addGenerator(R.REDBULL, "Redbull", "100", String.valueOf(basicGenerator), GeneratorsView.basicGenDesc);
        this.generatorsView.addGenerator(R.ENCHANTED_BOOK, "Notes", "200", String.valueOf(midGenerator), GeneratorsView.midGenDesc);
        this.generatorsView.addGenerator(R.CEUS, "CEUS", "300", String.valueOf(highGenerator), GeneratorsView.highGenDesc);
        this.improvementsView.addImprovement(R.PILLS, "Pills", "100", String.valueOf(lvlBasicImp), ImprovementsView.basicImprovementDesc);
        this.improvementsView.addImprovement(R.GLASSES, "Glasses", "200", String.valueOf(lvlMidImp), ImprovementsView.midImprovementDesc);
        this.improvementsView.addImprovement(R.CARLOS, "Carlos", "300", String.valueOf(lvlHighImp), ImprovementsView.highImprovementDesc);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void clear() {

    }

    public void updateGeneratorsView(int[] quantities, float[] totalCreditsPerSecond, float[] globalProductionPercentages, int[] prices) {
        generatorsView.updateTable(quantities, totalCreditsPerSecond, globalProductionPercentages, prices);
    }
    public void updateImprovementsView(int basic, int mid, int high) {
        this.improvementsView.removeImprovements();
        this.improvementsView.addImprovement(R.PILLS, "Pills", "100", String.valueOf(basic), ImprovementsView.basicImprovementDesc);
        this.improvementsView.addImprovement(R.GLASSES, "Glasses", "200", String.valueOf(mid), ImprovementsView.midImprovementDesc);
        this.improvementsView.addImprovement(R.CARLOS, "Carlos", "300", String.valueOf(high), ImprovementsView.highImprovementDesc);
    }

    public void noGenerators(String improvement) {
        JOptionPane.showMessageDialog(this, "You don't have generators to apply the improvement "+ improvement +" to.", "No Generators Error", JOptionPane.ERROR_MESSAGE);
    }
}
