/**
 * The StatisticsView class represents the graphical user interface for displaying statistics.
 * It extends JLayeredPane and implements the MyView interface.
 */
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

    /**
     * Constructs a StatisticsView object with the given ActionListener.
     *
     * @param listener The ActionListener to handle user actions.
     */
    public StatisticsView(ActionListener listener) {
        this.listener = listener;
        gameButtons = new ArrayList<>();
        setLayout(new OverlayLayout(this));
        init();
        mount();
    }

    /**
     * Initializes the components of the StatisticsView.
     */
    private void init() {
        chartTitle = "Game: ID";
        initializeBackground();
        initializeButtons();
        initializeChartPanel();
        initializeGamesPanel();
    }

    /**
     * Initializes the background image panel.
     */
    private void initializeBackground() {
        try {
            background = new JImagePanel(R.GAME_BACKGROUND);
            background.setResolution(JImagePanel.WIDTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the buttons used in the view.
     */
    private void initializeButtons() {
        back = createButton("Back", "back");
        logout = createButton("Logout", "logout");
        deleteAccount = createButton("Delete Account", "deleteAccount");
    }

    /**
     * Creates a JButton with the given text and action command.
     *
     * @param text          The text to display on the button.
     * @param actionCommand The action command associated with the button.
     * @return The created JButton.
     */
    private JTexturedButton createButton(String text, String actionCommand) {
        JTexturedButton button = new JTexturedButton(R.BUTTON_DEFAULT, R.BUTTON_PRESSED);
        button.setText(text);
        button.addActionListener(listener);
        button.setActionCommand(actionCommand);
        return button;
    }

    /**
     * Initializes the chart panel used to display statistics.
     */
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

    /**
     * Sets the fonts used in the chart panel.
     *
     * @param chartPanel The chart panel whose fonts are to be set.
     */
    private void setChartFonts(ChartPanel chartPanel) {
        chartPanel.getChart().getTitle().setFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getDomainAxis().setLabelFont(MinecraftFont.getFont());
        chartPanel.getChart().getCategoryPlot().getRangeAxis().setLabelFont(MinecraftFont.getFont());
    }

    /**
     * Initializes the panel used to display the list of games.
     */
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

    /**
     * Mounts the components onto the StatisticsView.
     */
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

    /**
     * Creates the main panel of the view.
     *
     * @return The main panel.
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Creates the top panel of the view.
     *
     * @return The top panel.
     */

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JPanel topPanelLeft = createTopPanelLeft();
        JPanel topPanelRight = createTopPanelRight();

        topPanel.add(topPanelLeft, BorderLayout.WEST);
        topPanel.add(topPanelRight, BorderLayout.EAST);

        return topPanel;
    }

    /**
     * Creates the left portion of the top panel.
     *
     * @return The left portion of the top panel.
     */
    private JPanel createTopPanelLeft() {
        JPanel topPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanelLeft.setOpaque(false);
        topPanelLeft.add(back);
        return topPanelLeft;
    }

    /**
     * Creates the right portion of the top panel.
     *
     * @return The right portion of the top panel.
     */
    private JPanel createTopPanelRight() {
        JPanel topPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanelRight.setOpaque(false);
        topPanelRight.add(logout);
        topPanelRight.add(deleteAccount);
        return topPanelRight;
    }

    /**
     * Creates the panel for the chart component.
     *
     * @return The panel for the chart component.
     */
    private JPanel createChartAuxPanel() {
        JPanel chartAux = new JPanel();
        chartAux.setLayout(new BoxLayout(chartAux, BoxLayout.Y_AXIS));
        chartAux.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        chartAux.setOpaque(false);
        chartAux.add(chartPanel);
        return chartAux;
    }

    /**
     * Creates the panel for the scroll pane component.
     *
     * @return The panel for the scroll pane component.
     */
    private JPanel createScrollPaneAuxPanel() {
        JPanel scrollPaneAux = new JPanel(new BorderLayout());
        scrollPaneAux.setOpaque(false);
        scrollPaneAux.add(scrollPane, BorderLayout.CENTER);

        JPanel scrollPanelAuxTop = createScrollPanelAuxTop();
        scrollPaneAux.add(scrollPanelAuxTop, BorderLayout.NORTH);

        return scrollPaneAux;
    }

    /**
     * Creates the top portion of the scroll pane panel.
     *
     * @return The top portion of the scroll pane panel.
     */
    private JPanel createScrollPanelAuxTop() {
        JPanel scrollPanelAuxTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scrollPanelAuxTop.setBackground(Color.darkGray);
        JLabel label = new JLabel("Games");
        label.setFont(MinecraftFont.getFont());
        label.setForeground(Color.YELLOW);
        scrollPanelAuxTop.add(label);
        return scrollPanelAuxTop;
    }

    /**
     * Adds a game to the list of games displayed.
     *
     * @param gameID        The ID of the game.
     * @param n_currencies  The number of currencies associated with the game.
     */
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

    /**
     * Updates the graphic representation of the statistics based on the provided data.
     *
     * @param data The data to update the statistics.
     */
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

    /**
     * Starts the view.
     */
    @Override
    public void start() {

    }

    /**
     * Stops the view.
     */
    @Override
    public void stop() {

    }

    /**
     * Clears the view.
     */
    @Override
    public void clear() {
        for (JTexturedButton gameButton : gameButtons) {
            this.games.remove(gameButton);
        }
        this.gameButtons.clear();
    }
}
