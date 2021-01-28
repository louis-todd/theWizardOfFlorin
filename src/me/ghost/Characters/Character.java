package me.ghost.Characters;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class Character extends Sprite {

    private String[] script = {"CharacterScript: Page 1", "CharacterScript: Page 2", "CharacterScript: Page 3"};
    private String characterName;
    private int currentIndex = 1;

    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture) {

        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
    }

    public String[] getScript(){
        return script;
    }

    public String getName(){
        return characterName;
    }

    public int getCurrentIndex(){
        return currentIndex;
    }

    public void incrementCurrentIndex(){
        currentIndex++;
    }

    public void resetScript(){
        currentIndex=1;
    }

}
