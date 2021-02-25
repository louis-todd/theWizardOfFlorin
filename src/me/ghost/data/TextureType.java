package me.ghost.data;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

public enum TextureType {
    BOARD("DialogueBoard.png"),

    LEFTMOVEMENT1("wizardImages/left-movement1.png"),
    LEFTMOVEMENT2("wizardImages/left-movement2.png"),
    LEFTMOVEMENT3("wizardImages/left-movement3.png"),
    LEFTMOVEMENT4("wizardImages/left-movement4.png"),
    LEFTMOVEMENT5("wizardImages/left-movement5.png"),
    LEFTMOVEMENT6("wizardImages/left-movement6.png"),
    LEFTMOVEMENT7("wizardImages/left-movement7.png"),
    LEFTMOVEMENT8("wizardImages/left-movement8.png"),

    RIGHTMOVEMENT1("wizardImages/right-movement1.png"),
    RIGHTMOVEMENT2("wizardImages/right-movement2.png"),
    RIGHTMOVEMENT3("wizardImages/right-movement3.png"),
    RIGHTMOVEMENT4("wizardImages/right-movement4.png"),
    RIGHTMOVEMENT5("wizardImages/right-movement5.png"),
    RIGHTMOVEMENT6("wizardImages/right-movement6.png"),
    RIGHTMOVEMENT7("wizardImages/right-movement7.png"),
    RIGHTMOVEMENT8("wizardImages/right-movement8.png"),

    GHOST("ghost.png"),
    WHISKERS("whiskers.png"),
    SNUFFLES("snuffles.png"),

    FRONT1("wizardImages/front1.png"),
    FRONT2("wizardImages/front2.png"),
    FRONT3("wizardImages/front3.png"),
    FRONT4("wizardImages/front4.png"),

    BACK1("wizardImages/back1.png"),
    BACK2("wizardImages/back2.png"),
    BACK3("wizardImages/back3.png"),
    BACK4("wizardImages/back4.png"),


    DUCK("Items/duck.png"),
    ICE("Items/ice.png"),
    TAMBOURINE("Items/tambourine.png"),
    TEDDY("Items/teddy.png"),
    WHISKY("Items/whisky.png"),
    WOOD("Items/plank.png"),
    YARN("Items/yarn.png"),
    TREE("tree.png"),

    HITWIZARD("wizardImages/hit-wizard.png"),
    HEART("heart.png");

    private final Texture texture = new Texture();

    /**
     * Sole Constructor for the Texture Type Enum
     * @param path Path of the texture
     */
    TextureType(String path) {
        try {
            this.texture.loadFromStream(Game.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error loading item from: " + path);
        }
    }

    /**
     * @return the texture.
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * @param index sets the index to lookup.
     * @return the correct left texture based on index of movement in this direction.
     */
    public static Texture getLeftTextureByIndex(int index) {
        Texture[] allTextures = {LEFTMOVEMENT1.getTexture(), LEFTMOVEMENT2.getTexture(), LEFTMOVEMENT3.getTexture(), LEFTMOVEMENT4.getTexture(), LEFTMOVEMENT5.getTexture(),
                LEFTMOVEMENT6.getTexture(), LEFTMOVEMENT7.getTexture(), LEFTMOVEMENT8.getTexture()};
        return allTextures[index];
    }
    /**
     * @param index sets the index to lookup.
     * @return the correct right texture based on index of movement in this direction.
     */
    public static Texture getRightTextureByIndex(int index) {
        Texture[] allTextures = {RIGHTMOVEMENT1.getTexture(), RIGHTMOVEMENT2.getTexture(), RIGHTMOVEMENT3.getTexture(), RIGHTMOVEMENT4.getTexture(), RIGHTMOVEMENT5.getTexture(),
                RIGHTMOVEMENT6.getTexture(), RIGHTMOVEMENT7.getTexture(), RIGHTMOVEMENT8.getTexture()};
        return allTextures[index];
    }

    /**
     * @param index sets the index to lookup.
     * @return the correct back texture based on index of movement in this direction.
     */
    public static Texture getBackTextureByIndex(int index) {
        Texture[] allTextures = {BACK1.getTexture(), BACK2.getTexture(), BACK3.getTexture(), BACK4.getTexture()};
        return allTextures[index];
    }

    /**
     * @param index sets the index to lookup.
     * @return the correct back texture based on index of movement in this direction.
     */
    public static Texture getFrontTextureByIndex(int index) {
        Texture[] allTextures = {FRONT1.getTexture(), FRONT2.getTexture(), FRONT3.getTexture(), FRONT4.getTexture()};
        return allTextures[index];
    }
}
