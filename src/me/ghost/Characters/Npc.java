package me.ghost.Characters;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;


public class Npc extends Character {
    public Npc(float xPosition, float yPosition, Texture npcTexture) {
        super(xPosition, yPosition, npcTexture);
    }

    public FloatRect dialogueArea(){
        float newHeight = this.getGlobalBounds().height * 8;
        float newWidth = this.getGlobalBounds().width * 8;
        float newLeft = this.getGlobalBounds().left - (this.getGlobalBounds().width) / 8;
        float newTop = this.getGlobalBounds().top - (this.getGlobalBounds().height) / 8;

        return new FloatRect(newLeft, newTop, newWidth, newHeight);
    }
}
