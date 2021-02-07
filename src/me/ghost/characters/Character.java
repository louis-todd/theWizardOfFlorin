package me.ghost.Characters;

import java.util.ArrayList;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class Character extends Sprite {

    private String[] script;
    private ArrayList<String> NPCScript = new ArrayList<String>();
    private String characterName;
    private int currentIndex = 1;

    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture) {

        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
    }

    public ArrayList<String> getScript() {
        NPCScript.clear();
        if (characterName == "Placeholder1") {
            NPCScript.add("Page 1: NPC1");
            NPCScript.add("Page 2: NPC1");
            NPCScript.add("Page 3: NPC1");
        }
        if (characterName == "Placeholder2") {
            NPCScript.add("Page 1: NPC2");
            NPCScript.add("Page 2: NPC2");
            NPCScript.add("Page 3: NPC2");
        }
        if (characterName == "Placeholder3") {
            NPCScript.add("Page 1: NPC3");
            NPCScript.add("Page 2: NPC3");
            NPCScript.add("Page 3: NPC3");
        }
        if (NPCScript.size() == 0) {
            NPCScript.add("I'm empty because you haven't passed me in a character name that I recognise!");
        }
        return NPCScript;
    }

    public String getName() {
        return characterName;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void incrementCurrentIndex() {
        currentIndex++;
    }

    public void resetScript() {
        currentIndex = 1;
    }

}
