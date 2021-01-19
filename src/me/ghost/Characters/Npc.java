package me.ghost.Characters;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;


public class Npc extends Character {
    public Npc(String characterName, float xPosition, float yPosition, Texture npcTexture) {
        super(characterName, xPosition, yPosition, npcTexture);
    }

    public FloatRect dialogueArea(int scaleFactor){
        float newHeight = this.getGlobalBounds().height * scaleFactor;
        float newWidth = this.getGlobalBounds().width * scaleFactor;
        float newLeft = this.getGlobalBounds().left - (this.getGlobalBounds().width) / scaleFactor;
        float newTop = this.getGlobalBounds().top - (this.getGlobalBounds().height) / scaleFactor;

        return new FloatRect(newLeft, newTop, newWidth, newHeight);
    }
}
