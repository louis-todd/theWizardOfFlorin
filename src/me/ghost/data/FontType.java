package me.ghost.data;


import me.ghost.Game;
import org.jsfml.graphics.Font;

public enum FontType {
    ROBOTO("ubuntu.regular.ttf")
    //UBUNTU("ubuntu.regular.ttf")
    ;

    private final Font font = new Font();

    /**
     * Sole constructor.
     * @param path sets the path of the Font.
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
     * @return the font.
     */
    public Font getFont() {
        return this.font;
    }
}
