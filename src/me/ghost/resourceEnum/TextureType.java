package me.ghost.resourceEnum;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

public enum TextureType {
    SQUARE16("square-16.png"),
    BOARD("DialogueBoard.png")

    ;

private final Texture texture = new Texture();

    TextureType(String path) {
        try {
            this.texture.loadFromStream(Game.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Texture getTexture() {
        return this.texture;
    }
}
