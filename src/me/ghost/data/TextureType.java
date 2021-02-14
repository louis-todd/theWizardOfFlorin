package me.ghost.data;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

public enum TextureType {
    SQUARE16("square-16.png"),
    BOARD("DialogueBoard.png"),
    LEFTSTILL("wizardImages/left-still.png"),
    RIGHTSTILL("wizardImages/right-still.png"),
    LEFTSPRITE("wizardImages/left-sprite.png"),
    RIGHTSPRITE("wizardImages/right-sprite.png"),
    GHOST("ghost.png"),
    FRONTVIEW("wizardImages/wizard_front.png"),
    BACKVIEW("wizardImages/wizard_back.png"),
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
