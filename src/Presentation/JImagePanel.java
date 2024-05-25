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

    public static final int EXTEND_RES_WIDTH = 1;
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
        this.alpha = 1.0f;
        setOpaque(false);
        this.image = loadImage(path);
    }


    public void setImage(BufferedImage image) {
        this.image = image;
        this.alpha = 1.0f;
        repaint();
    }

    public void setResolution (int res) {
        this.res = res;
    }

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

    private void drawCenteredImage(Graphics2D g2d, int panelWidth, int panelHeight, int imageWidth, int imageHeight) {
        double scaleFactor = Math.min((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);
        int newImageWidth = (int) (imageWidth * scaleFactor);
        int newImageHeight = (int) (imageHeight * scaleFactor);
        int xOffset = (panelWidth - newImageWidth) / 2;
        int yOffset = (panelHeight - newImageHeight) / 2;
        g2d.drawImage(image, xOffset, yOffset, newImageWidth, newImageHeight, this);
    }

    private void drawStretchedImage(Graphics2D g2d, int panelWidth, int panelHeight, int imageWidth, int imageHeight) {
        double scaleFactor = (double) panelWidth / imageWidth;
        int newImageWidth = panelWidth;
        int newImageHeight = (int) (imageHeight * scaleFactor);
        int xOffset = 0;
        int yOffset = (panelHeight - newImageHeight) / 2;
        g2d.drawImage(image, xOffset, yOffset, newImageWidth, newImageHeight, this);
    }

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


    public void setAlpha(float alpha) {
        this.alpha = alpha;
        revalidate();
        repaint();
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