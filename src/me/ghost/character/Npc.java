package me.ghost.character;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;

import me.ghost.Item;

public class Npc extends Character {

    private Boolean shouldDraw = false;

    public Npc(String characterName, float xPosition, float yPosition, Texture npcTexture, int expectedNumberOfItems) {
        super(characterName, xPosition, yPosition, npcTexture, expectedNumberOfItems);
    }

    public FloatRect dialogueArea(float scaleFactor) {
        float centrex = this.getGlobalBounds().left + (this.getGlobalBounds().width / 2);
        float centrey = this.getGlobalBounds().top + (this.getGlobalBounds().height / 2);
        float newHeight = this.getGlobalBounds().height * scaleFactor;
        float newWidth = this.getGlobalBounds().width * scaleFactor;
        float newLeft = centrex - (newWidth / 2);
        float newTop = centrey - (newHeight / 2);
        return new FloatRect(newLeft, newTop, newWidth, newHeight);
    }

    public Boolean shouldDraw() {
        return shouldDraw;
    }

    public void setShouldDraw(Boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
    }

}
