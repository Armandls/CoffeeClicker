package Presentation.Fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/**
 * Custom font class for Minecraft font.
 */
public class MinecraftFont extends Font {

    /**
     * Constructor to create a MinecraftFont object.
     */
    public MinecraftFont() {
        super("Minecraft", Font.PLAIN, 12);
    }

    /**
     * Get the Minecraft font with a default size of 20.
     *
     * @return The Minecraft font with a default size of 20.
     */
    public static Font getFont() {
        try {
            Font ret = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "/files/Resources/Fonts/Minecraftchmc-dBlX.ttf"));
            return ret.deriveFont(Font.BOLD, 20);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the Minecraft font with a custom size.
     *
     * @param size The size of the Minecraft font.
     * @return The Minecraft font with the specified size.
     */
    public static Font getFontWithSize(int size) {
        try {
            Font ret = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "/files/Resources/Fonts/Minecraftchmc-dBlX.ttf"));
            return ret.deriveFont(Font.BOLD, size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
