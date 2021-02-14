package me.ghost.data;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

public enum TextureType {
    SQUARE16("square-16.png"),
    BOARD("DialogueBoard.png"),

    // LEFTSTILL("wizardImages/left-movement1.png"),
    LEFTMOVEMENT1("wizardImages/left-movement1.png"),
    LEFTMOVEMENT2("wizardImages/left-movement2.png"),
    LEFTMOVEMENT3("wizardImages/left-movement3.png"),
    LEFTMOVEMENT4("wizardImages/left-movement4.png"),
    LEFTMOVEMENT5("wizardImages/left-movement5.png"),
    LEFTMOVEMENT6("wizardImages/left-movement6.png"),
    LEFTMOVEMENT7("wizardImages/left-movement7.png"),
    LEFTMOVEMENT8("wizardImages/left-movement8.png"),

    RIGHTSTILL("wizardImages/right-still.png"),
    RIGHTMOVEMENT1("wizardImages/right-movement1.png"),

    GHOST("ghost.png"),
    FRONTVIEW("wizardImages/front.png"),
    BACKVIEW("wizardImages/back.png"),
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

    public static Texture getLeftTextureByIndex(int index){
        Texture[] allTextures = {LEFTMOVEMENT1.getTexture(), LEFTMOVEMENT2.getTexture(), LEFTMOVEMENT3.getTexture(), LEFTMOVEMENT4.getTexture(), LEFTMOVEMENT5.getTexture(), 
            LEFTMOVEMENT6.getTexture(), LEFTMOVEMENT7.getTexture(), LEFTMOVEMENT8.getTexture()};
        return allTextures[index];
    }
}
