package Presentation;

import javax.swing.*;
import java.awt.*;

public class JTexturedButton extends JButton {

    private Image texture;
    private Image pressedTexture;


    public JTexturedButton() {}

    public JTexturedButton(String texture, String pressedTexture) {
        this.texture = new ImageIcon(System.getProperty("user.dir") + texture).getImage();
        this.pressedTexture = new ImageIcon(System.getProperty("user.dir") + pressedTexture).getImage();
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

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
