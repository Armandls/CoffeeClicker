package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class JHoverPanel extends JPanel {

    private String id;
    private int height;
    private int width;

    private final JPanel panel;

    public JHoverPanel(JComponent component) {
        setLayout(null);
        setOpaque(false);
        setVisible(true);
        height = 25;
        width = 25;
        panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.RED);
        panel.setVisible(false);
        add(panel);

        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setVisible(true);
                System.out.println("Entered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setVisible(false);
                System.out.println("Exited");
            }

        });

        component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                Point window = SwingUtilities.getWindowAncestor(panel).getLocationOnScreen();
                panel.setBounds(p.x - window.x, p.y - window.y - height, width, height);
                System.out.println(window.x + ", " + window.y);
                System.out.println("Mouse moved");
            }
        });
    }

    public JHoverPanel(JComponent component, JPanel panelToHover) {
        setLayout(null);
        setOpaque(false);
        setVisible(true);
        panel = panelToHover;
        panel.setOpaque(true);
        height = (panelToHover.getHeight() == 0 ? 25: panelToHover.getHeight());
        width = (panelToHover.getWidth() == 0 ? 25: panelToHover.getWidth());
        panel.setVisible(false);
        add(panel);
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setVisible(false);
            }
        });

        component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                Point window = SwingUtilities.getWindowAncestor(panel).getLocationOnScreen();
                panel.setBounds(p.x - window.x, p.y - window.y - height - 25, width, height);
            }
        });
    }

    public JHoverPanel(JComponent component, JPanel panelToHover, String id) {
        setLayout(null);
        setOpaque(false);
        setVisible(true);

        this.id = id;
        panel = panelToHover;
        panel.setOpaque(true);
        height = (panelToHover.getHeight() == 0 ? 25: panelToHover.getHeight());
        width = (panelToHover.getWidth() == 0 ? 25: panelToHover.getWidth());
        panel.setVisible(false);
        add(panel);
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setVisible(false);
            }
        });

        component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                Point window = SwingUtilities.getWindowAncestor(panel).getLocationOnScreen();
                panel.setBounds(p.x - window.x, p.y - window.y - height - 25, width, height);
            }
        });
    }

    public String getId() {
        return this.id;
    }

    @Override
    public void setSize (int height, int width) {
        this.height = height;
        this.width = width;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
