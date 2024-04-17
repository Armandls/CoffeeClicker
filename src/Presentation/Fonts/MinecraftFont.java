package Presentation.Fonts;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MinecraftFont extends Font {

    public  MinecraftFont() throws IOException, FontFormatException {
        super("Minecraft", Font.PLAIN, 12);
    }

    public static Font getFont() {
        try {
            Font ret = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "/files/Resources/Fonts/Minecraftchmc-dBlX.ttf"));
            return ret.deriveFont(Font.PLAIN, 12);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
