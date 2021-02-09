package me.ghost.character;

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
    private File npcTextFile;

    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture) {

        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
    }

    public ArrayList<String> getScript(){
        NPCScript.clear();

        npcTextFile = new File("resources/Dialogue/" + characterName + ".csv");
        if(npcTextFile.exists()) {
            addDialogue("resources/Dialogue/" + characterName + ".csv");
        }
        else{
            NPCScript.add("Page 1: " + characterName);
            NPCScript.add("Page 2: " + characterName);
            NPCScript.add("Page 3: " + characterName);
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
                data = this.wrapRoundDialogueBox(data);
                NPCScript.addAll(Arrays.asList(data));
                }
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String[] wrapRoundDialogueBox(String[] data){
        int widthOfDialogue=50;
        int tmp = 0;
        ArrayList<String> updatedData = new ArrayList<String>();
        for (String sentence : data){
            for(int length=0; length<sentence.length(); length++){
                if(tmp>=widthOfDialogue){
                    if(sentence.charAt(length) == (' ')){
                        sentence = sentence.substring(0, length) + "\n" + sentence.substring(length);
                        tmp=0;
                    }
                }
                tmp++;
            }
            updatedData.add(sentence);
        }
        return updatedData.toArray(new String[0]);
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
