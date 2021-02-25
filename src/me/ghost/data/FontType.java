package me.ghost.data;


import me.ghost.Game;
import org.jsfml.graphics.Font;

public enum FontType {
    ROBOTO("ubuntu.regular.ttf")
    //UBUNTU("ubuntu.regular.ttf")
    ;

    private final Font font = new Font();

    /**
     * Sole constructor for the FontType Enum
     * @param path Path of the Font
     */
    FontType(String path) {
        try {
            this.font.loadFromStream(Game.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Returns the font
     */
    public Font getFont() {
        return this.font;
    }
}
