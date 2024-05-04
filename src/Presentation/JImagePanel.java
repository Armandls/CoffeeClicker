package Presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JImagePanel extends JPanel {

    private BufferedImage image;

    private int res;

    private float alpha;

    public static final int DEFAULT_RES = 0;
    public static final int EXTEND_RES_WIDTH = 1;
    public static final int EXTEND_RES_HEIGHT = 2;
    public JImagePanel() {
        super();
        setOpaque(false);
        res = 0;
    }

    public JImagePanel(BufferedImage image) {
        super();
        this.alpha = 1.0f;
        this.image = image;
        setOpaque(false);
    }

    public JImagePanel(String path) throws IOException {
        this.image = loadImage(path);
        setOpaque(false);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public void setResolution (int res) {
        this.res = res;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // Set alpha composite
            if (res == 0) {
            // Set the panel to be transparent
            setOpaque(false);

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Get the image's original width and height
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            // Calculate the scale to fit the image to the panel size
            double scaleFactor = Math.min((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);

            // Calculate the new image dimensions
            int newImageWidth = (int) (imageWidth * scaleFactor);
            int newImageHeight = (int) (imageHeight * scaleFactor);

            // Calculate the offset for centered positioning
            int xOffset = (panelWidth - newImageWidth) / 2;
            int yOffset = (panelHeight - newImageHeight) / 2;

            // Draw the scaled image centered onto the panel
            g2d.drawImage(image, xOffset, yOffset, newImageWidth, newImageHeight, null);
        }
            else if (res == 1) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                // Get the image's original width and height
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                // Calculate the scale to fit the extended image width to the panel size
                double scaleFactor = (double) panelWidth / imageWidth;

                // Calculate the new image dimensions
                int newImageWidth = panelWidth;
                int newImageHeight = (int) (imageHeight * scaleFactor);

                // Calculate the offset for centered positioning
                int xOffset = 0;
                int yOffset = (panelHeight - newImageHeight) / 2;

                // Draw the scaled image centered onto the panel
                g.drawImage(image, xOffset, yOffset, newImageWidth, newImageHeight, null);

            }
            else if (res == 2) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                // Get the image's original width and height
                int imageWidth = image.getWidth(null);
                int imageHeight = image.getHeight(null);

                // Calculate the scale to fit the extended image width to the panel size
                double scaleFactor = (double) panelWidth / imageWidth;

                // Calculate the new image dimensions
                int newImageWidth = panelWidth;
                int newImageHeight = (int) (imageHeight * scaleFactor);

                // Calculate the offset for centered positioning
                int xOffset = 0;
                int yOffset = (panelHeight - newImageHeight) / 2;

                // Draw the scaled image centered onto the panel
                g.drawImage(image, xOffset+50, yOffset+60, newImageWidth-100, newImageHeight-100, null);

            }
        }
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        repaint(); // Repaint the panel to reflect the alpha change
    }

    @Override
    public Dimension getPreferredSize() {
        if (image != null) {
            return new Dimension(image.getWidth(), image.getHeight());
        } else {
            return super.getPreferredSize();
        }
    }

    public static BufferedImage loadImage(String imagePath) throws IOException {
        try {
            File file = new File(System.getProperty("user.dir") + imagePath);
            return ImageIO.read(file);
        } catch (NullPointerException e) {
            throw new IOException("Image file not found: " + imagePath);
        }
    }
}