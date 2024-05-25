package Presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A custom JPanel that displays an image.
 */
public class JImagePanel extends JPanel {

    private BufferedImage image;
    private int res;
    private float alpha;

    public static final int EXTEND_RES_WIDTH = 1;

    /**
     * Constructs a new JImagePanel.
     */
    public JImagePanel() {
        super();
        setOpaque(false);
        res = 0;
    }

    /**
     * Constructs a new JImagePanel with the specified image.
     *
     * @param image The image to be displayed.
     */
    public JImagePanel(BufferedImage image) {
        super();
        this.alpha = 1.0f;
        this.image = image;
        setOpaque(false);
    }

    /**
     * Constructs a new JImagePanel with the image loaded from the specified path.
     *
     * @param path The path to the image file.
     * @throws IOException if the image cannot be loaded.
     */
    public JImagePanel(String path) throws IOException {
        this.alpha = 1.0f;
        setOpaque(false);
        this.image = loadImage(path);
    }

    /**
     * Sets the image to be displayed.
     *
     * @param image The image to be displayed.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        this.alpha = 1.0f;
        repaint();
    }

    /**
     * Sets the resolution mode of the image.
     *
     * @param res The resolution mode. Use {@link #EXTEND_RES_WIDTH} for extending the image to the width of the panel.
     */
    public void setResolution(int res) {
        this.res = res;
    }

    /**
     * Sets the transparency of the image.
     *
     * @param alpha The alpha value (transparency) of the image.
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        revalidate();
        repaint();
    }

    /**
     * Paints the image on the panel.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        setOpaque(res != 0);

        if (image == null) {
            return;
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        switch (res) {
            case 0:
                drawCenteredImage(g2d, panelWidth, panelHeight, imageWidth, imageHeight);
                break;
            case 1:
                drawStretchedImage(g2d, panelWidth, panelHeight, imageWidth, imageHeight);
                break;
            case 2:
                drawOffsetStretchedImage(g2d, panelWidth, panelHeight, imageWidth, imageHeight);
                break;
            default:
                break;
        }

        g2d.dispose();
    }

    /**
     *  Returns the preferred size of the panel.
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        if (image != null) {
            return new Dimension(image.getWidth(), image.getHeight());
        } else {
            return super.getPreferredSize();
        }
    }

    /**
     * Draws the image centered on the panel.
     * @param g2d
     * @param panelWidth
     * @param panelHeight
     * @param imageWidth
     * @param imageHeight
     */
    private void drawCenteredImage(Graphics2D g2d, int panelWidth, int panelHeight, int imageWidth, int imageHeight) {
        double scaleFactor = Math.min((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);
        int newImageWidth = (int) (imageWidth * scaleFactor);
        int newImageHeight = (int) (imageHeight * scaleFactor);
        int xOffset = (panelWidth - newImageWidth) / 2;
        int yOffset = (panelHeight - newImageHeight) / 2;
        g2d.drawImage(image, xOffset, yOffset, newImageWidth, newImageHeight, this);
    }

    /**
     * Draws the image stretched to fit the panel.
     * @param g2d
     * @param panelWidth
     * @param panelHeight
     * @param imageWidth
     * @param imageHeight
     */
    private void drawStretchedImage(Graphics2D g2d, int panelWidth, int panelHeight, int imageWidth, int imageHeight) {
        double scaleFactor = (double) panelWidth / imageWidth;
        int newImageWidth = panelWidth;
        int newImageHeight = (int) (imageHeight * scaleFactor);
        int xOffset = 0;
        int yOffset = (panelHeight - newImageHeight) / 2;
        g2d.drawImage(image, xOffset, yOffset, newImageWidth, newImageHeight, this);
    }

    /**
     * Draws the image stretched to fit the panel with an offset.
     * @param g2d
     * @param panelWidth
     * @param panelHeight
     * @param imageWidth
     * @param imageHeight
     */
    private void drawOffsetStretchedImage(Graphics2D g2d, int panelWidth, int panelHeight, int imageWidth, int imageHeight) {
        double scaleFactor = (double) panelWidth / imageWidth;
        int newImageWidth = panelWidth;
        int newImageHeight = (int) (imageHeight * scaleFactor);
        int xOffset = 50;
        int yOffset = 60;
        int adjustedWidth = newImageWidth - 100;
        int adjustedHeight = newImageHeight - 100;
        g2d.drawImage(image, xOffset, yOffset, adjustedWidth, adjustedHeight, this);
    }

    /**
     * Loads an image from the specified path.
     * @param imagePath
     * @return
     * @throws IOException
     */
    private static BufferedImage loadImage(String imagePath) throws IOException {
        try {
            File file = new File(System.getProperty("user.dir") + imagePath);
            return ImageIO.read(file);
        } catch (NullPointerException e) {
            throw new IOException("Image file not found: " + imagePath);
        }
    }
}
