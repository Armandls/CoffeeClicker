package Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JButton class that allows for textured backgrounds.
 * The button can display different textures for its normal and pressed states.
 */
public class JTexturedButton extends JButton {

    private Image texture;
    private Image pressedTexture;

    /**
     * Default constructor for JTexturedButton.
     */
    public JTexturedButton() {}

    /**
     * Constructs a JTexturedButton with specified textures for normal and pressed states.
     *
     * @param texture the path to the texture image for the normal state
     * @param pressedTexture the path to the texture image for the pressed state
     */
    public JTexturedButton(String texture, String pressedTexture) {
        this.texture = new ImageIcon(System.getProperty("user.dir") + texture).getImage();
        this.pressedTexture = new ImageIcon(System.getProperty("user.dir") + pressedTexture).getImage();
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    /**
     * Overrides the paintComponent method to draw the textured backgrounds based on the button state.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Draw background image based on button state
        if (texture == null || pressedTexture == null) {
            super.paintComponent(g);
            return;
        }
        if (getModel().isPressed()) {
            g.drawImage(pressedTexture, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(texture, 0, 0, getWidth(), getHeight(), this);
        }
        // Draw button text
        super.paintComponent(g);
    }
}
