package me.ghost.data;


import me.ghost.Game;
import org.jsfml.graphics.Font;

public enum FontType {
    ROBOTO("ubuntu.regular.ttf")
    //UBUNTU("ubuntu.regular.ttf")
    ;

    private final Font font = new Font();

    FontType(String path) {
        try {
            this.font.loadFromStream(Game.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            // e.printStackTrace();
             System.out.println("4");
        }
    }

    public Font getFont() {
        return this.font;
    }
}
