package me.ghost.characters;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    public ArrayList<String> getScript(){
        NPCScript.clear();
        if (characterName.equals("Mayor")) {
            addDialogue("resources/MayorSpeech.csv");
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

    private void addDialogue(String fileName) {
        BufferedReader csvReader = returnBufferedReader(fileName);
        try{
            String row;
            int index = 1;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                NPCScript.addAll(Arrays.asList(data));
                }
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private BufferedReader returnBufferedReader(String fileName) {
        return new BufferedReader(new InputStreamReader(getFileStream(fileName)));
    }
    private InputStream getFileStream(String fileName) {
            InputStream fileStream = null;
        try {
                fileStream = new DataInputStream(new FileInputStream(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileStream;
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
