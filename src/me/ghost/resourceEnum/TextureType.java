package me.ghost.ResourceEnum;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

public enum TextureType {
    SQUARE16("square-16.png"),
    LEFTSTILL("left-still.png"),
    RIGHTSTILL("right-still.png"),
    LEFTSPRITE("left-sprite.png"),
    RIGHTSPRITE("right-sprite.png"),
    BOARD("DialogueBoard.png"),
    TEST("tiles/tile200.png32")
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
