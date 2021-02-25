package me.ghost.character;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;

public class Npc extends Character {

    private boolean shouldDraw = false;
    private String battleDifficulty = "EASY";

    /**
     * Sole constructor.
     * @param characterName sets the name of this character.
     * @param xPosition sets the X position of this character.
     * @param yPosition sets the Y position of this character.
     * @param npcTexture sets the texture of this character.
     * @param expectedNumberOfItems sets the number of items associated with this character.
     * @param battleDifficulty sets the difficulty of the battle associated with this character.
     */
    public Npc(String characterName, float xPosition, float yPosition, Texture npcTexture, int expectedNumberOfItems, String battleDifficulty) {
        super(characterName, xPosition, yPosition, npcTexture, expectedNumberOfItems);
    }

    /** 
     * Gets the area around this character in which dialogue can be instigated within.
     * @param scaleFactor sets the scale factor of interaction radius.
     * @return the radius around the character in which they can be interacted within.
     */
    public FloatRect dialogueArea(float scaleFactor) {
        float centrex = this.getGlobalBounds().left + (this.getGlobalBounds().width / 2);
        float centrey = this.getGlobalBounds().top + (this.getGlobalBounds().height / 2);
        float newHeight = this.getGlobalBounds().height * scaleFactor;
        float newWidth = this.getGlobalBounds().width * scaleFactor;
        float newLeft = centrex - (newWidth / 2);
        float newTop = centrey - (newHeight / 2);
        return new FloatRect(newLeft, newTop, newWidth, newHeight);
    }

    /** 
     * @return whether this NPC should be drawn.
     */
    public boolean shouldDraw() {
        return shouldDraw;
    }

    /** 
     * @param shouldDraw sets whether this NPC should be drawn.
     */
    public void setShouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
    }

    /** 
     * @return the difficulty of this NPCs associated battle.
     */
    public String getBattleDifficulty(){
        return battleDifficulty;
    }
}
