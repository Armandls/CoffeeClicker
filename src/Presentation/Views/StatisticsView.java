package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.JImagePanel;
import Presentation.JTexturedButton;
import Presentation.R;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StatisticsView extends JLayeredPane implements MyView {

    private final ActionListener listener;
    private JTexturedButton back;
    private JTexturedButton logout;
    private JTexturedButton deleteAccount;
    private ChartPanel chartPanel;
    private JScrollPane scrollPane;
    private JPanel games;
    private JImagePanel background;
    private String chartTitle;
    private ArrayList<JTexturedButton> gameButtons;

    public StatisticsView(ActionListener listener) {
        this.listener = listener;
        gameButtons = new ArrayList<>();
        setLayout(new OverlayLayout(this));
        init();
        mount();
    }

    private void init() {
        chartTitle = "Game: ID";
        initializeBackground();
        initializeButtons();
        initializeChartPanel();
        initializeGamesPanel();
    }

    private void initializeBackground() {
        try {
            background = new JImagePanel(R.GAME_BACKGROUND);
            background.setResolution(JImagePanel.WIDTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeButtons() {
        back = createButton("Back", "back");
        logout = createButton("Logout", "logout");
        deleteAccount = createButton("Delete Account", "deleteAccount");
    }

    private JTexturedButton createButton(String text, String actionCommand) {
        JTexturedButton button = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        button.setText(text);
        button.addActionListener(listener);
        button.setActionCommand(actionCommand);
        return button;
    }

    private void initializeChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String xAxisLabel = "Minute";
        String yAxisLabel = "Credits";
        chartPanel = new ChartPanel(
                ChartFactory.createLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false)
        );
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setOpaque(false);
        setChartFonts(chartPanel);
    }

    private void setChartFonts(ChartPanel chartPanel) {
        chartPanel.getChart().getTitle().setFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getDomainAxis().setLabelFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getRangeAxis().setLabelFont(MinecraftFont.getFont());
    }

    private void initializeGamesPanel() {
        games = new JPanel();
        games.setPreferredSize(new Dimension(200, 600));
        games.setOpaque(true);
        games.setBackground(new Color(143, 47, 19));
        scrollPane = new JScrollPane(games);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    private void mount() {
        JPanel panel = createMainPanel();
        JPanel topPanel = createTopPanel();
        JPanel chartAux = createChartAuxPanel();
        JPanel scrollPaneAux = createScrollPaneAuxPanel();

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(chartAux, BorderLayout.CENTER);
        panel.add(scrollPaneAux, BorderLayout.WEST);

        setLayer(this.background, 0);
        setLayer(panel, 1);

        add(this.background);
        add(panel);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JPanel topPanelLeft = createTopPanelLeft();
        JPanel topPanelRight = createTopPanelRight();

        topPanel.add(topPanelLeft, BorderLayout.WEST);
        topPanel.add(topPanelRight, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createTopPanelLeft() {
        JPanel topPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanelLeft.setOpaque(false);
        topPanelLeft.add(back);
        return topPanelLeft;
    }

    private JPanel createTopPanelRight() {
        JPanel topPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanelRight.setOpaque(false);
        topPanelRight.add(logout);
        topPanelRight.add(deleteAccount);
        return topPanelRight;
    }

    private JPanel createChartAuxPanel() {
        JPanel chartAux = new JPanel();
        chartAux.setLayout(new BoxLayout(chartAux, BoxLayout.Y_AXIS));
        chartAux.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        chartAux.setOpaque(false);
        chartAux.add(chartPanel);
        return chartAux;
    }

    private JPanel createScrollPaneAuxPanel() {
        JPanel scrollPaneAux = new JPanel(new BorderLayout());
        scrollPaneAux.setOpaque(false);
        scrollPaneAux.add(scrollPane, BorderLayout.CENTER);

        JPanel scrollPanelAuxTop = createScrollPanelAuxTop();
        scrollPaneAux.add(scrollPanelAuxTop, BorderLayout.NORTH);

        return scrollPaneAux;
    }

    private JPanel createScrollPanelAuxTop() {
        JPanel scrollPanelAuxTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scrollPanelAuxTop.setBackground(Color.darkGray);
        JLabel label = new JLabel("Games");
        label.setFont(MinecraftFont.getFont());
        label.setForeground(Color.YELLOW);
        scrollPanelAuxTop.add(label);
        return scrollPanelAuxTop;
    }


    public void addGame(String gameID, String n_currencies) {
        chartTitle = "Game: " + gameID;
        JTexturedButton button = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        button.setPreferredSize(new Dimension(150, 25));
        button.setText(gameID + " - " + n_currencies);
        button.setActionCommand(gameID);
        button.addActionListener(listener);
        gameButtons.add(button);
        this.games.add(button);
        this.games.revalidate();
        this.scrollPane.revalidate();
    }

    public void updateGraphic(String[][] data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String[] datum : data) {
            dataset.addValue(Integer.parseInt(datum[1]), "Game", datum[0]);
        }
        chartPanel.setChart(ChartFactory.createLineChart(chartTitle, "Minute", "Credits", dataset, PlotOrientation.VERTICAL, false, true, false));
        chartPanel.getChart().getTitle().setFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getDomainAxis().setLabelFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getDomainAxis().setTickLabelFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getRangeAxis().setLabelFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getRangeAxis().setTickLabelFont(MinecraftFont.getFont());
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void clear() {
        for (JTexturedButton gameButton : gameButtons) {
            this.games.remove(gameButton);
        }
        this.gameButtons.clear();
    }
}
