package me.ghost.Characters;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;


public class Npc extends Character {
    public Npc(float xPosition, float yPosition, Texture npcTexture) {
        super(xPosition, yPosition, npcTexture);
    }

    public FloatRect dialogueArea(){
        float newLeft = this.getGlobalBounds().left - (this.getGlobalBounds().width) / 2;
        float newTop = this.getGlobalBounds().top - (this.getGlobalBounds().height) / 2;
        float newHeight = this.getGlobalBounds().height * 2;
        float newWidth = this.getGlobalBounds().width * 2;
        return new FloatRect(newLeft, newTop, newWidth, newHeight);
    }
}
