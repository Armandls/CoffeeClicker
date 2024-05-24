package Presentation.Views;

import Presentation.Fonts.MinecraftFont;
import Presentation.FrameController;
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

public class StatisticsView extends JLayeredPane implements MyView {

    private ActionListener listener;
    private FrameController controller;
    private JTexturedButton back;
    private JTexturedButton logout;
    private JTexturedButton deleteAccount;
    private ChartPanel chartPanel;
    private JScrollPane scrollPane;
    private JPanel games;
    private JImagePanel background;
    private String chartTitle;

    public StatisticsView(ActionListener listener) {
        this.listener = listener;
        setLayout(new OverlayLayout(this));
        init();
        mount();
    }

    private void init() {
        chartTitle = "Game: ID";
        try {
            background = new JImagePanel(R.GAME_BACKGROUND);
            background.setResolution(JImagePanel.WIDTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        back = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        back.setText("Back");
        back.addActionListener(listener);
        back.setActionCommand("back");

        logout = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        logout.setText("Logout");
        logout.addActionListener(listener);
        logout.setActionCommand("logout");

        deleteAccount = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        deleteAccount.setText("Delete Account");
        deleteAccount.addActionListener(listener);
        deleteAccount.setActionCommand("deleteAccount");

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String xAxisLabel = "Minute";
        String yAxisLabel = "Credits";
        chartPanel = new ChartPanel(
                ChartFactory.createLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false)
        );
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setOpaque(false);
        chartPanel.getChart().getTitle().setFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getDomainAxis().setLabelFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getRangeAxis().setLabelFont(MinecraftFont.getFont());

        games = new JPanel();
        games.setPreferredSize(new Dimension(200, 600));
        games.setOpaque(false);
        scrollPane = new JScrollPane(games);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    private void mount() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JPanel topPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanelLeft.setOpaque(false);
        topPanelLeft.add(back);

        JPanel topPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanelRight.setOpaque(false);
        topPanelRight.add(logout);
        topPanelRight.add(deleteAccount);

        topPanel.add(topPanelLeft, BorderLayout.WEST);
        topPanel.add(topPanelRight, BorderLayout.EAST);

        JPanel chartAux = new JPanel();
        chartAux.setLayout(new BoxLayout(chartAux, BoxLayout.Y_AXIS));
        chartAux.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        chartAux.setOpaque(false);
        chartAux.add(chartPanel);

        JPanel scrollPaneAux = new JPanel(new BorderLayout());
        scrollPaneAux.setOpaque(false);
        scrollPaneAux.add(scrollPane, BorderLayout.CENTER);

        JPanel scrollPanelAuxTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scrollPanelAuxTop.setBackground(Color.darkGray);
        JLabel label = new JLabel("Games");
        label.setFont(MinecraftFont.getFont());
        label.setForeground(Color.YELLOW);
        scrollPanelAuxTop.add(label);
        scrollPaneAux.add(scrollPanelAuxTop, BorderLayout.NORTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(chartAux, BorderLayout.CENTER);
        panel.add(scrollPaneAux, BorderLayout.WEST);

        setLayer(this.background, 0);
        setLayer(panel, 1);

        add(this.background);
        add(panel);
    }

    public void addGame(String gameID, String n_currencies) {
        chartTitle = "Game: " + gameID;
        JTexturedButton button = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        button.setPreferredSize(new Dimension(150, 25));
        button.setText(gameID + " - " + n_currencies);
        button.setActionCommand(gameID);
        button.addActionListener(listener);
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

    }
}
