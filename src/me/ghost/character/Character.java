package me.ghost.character;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class Character extends Sprite {

    private final List<String> npcScript = new ArrayList<>();
    private final String characterName;
    private int currentIndex = 1;

    private int currentBattleIndex = 1;
    private final List<String> npcBattleScript = new ArrayList<>();

    private boolean hasCompletedBattle = false;

    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture) {

        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
    }

    public List<String> getScript(){
        npcScript.clear();

        File npcTextFile = new File("resources/Dialogue/" + characterName + "1.csv");
        if(npcTextFile.exists()) {
            addDialogue("resources/Dialogue/" + characterName + "1.csv");
        }
        else{
            npcScript.add("Page 1: " + characterName);
            npcScript.add("Page 2: " + characterName);
            npcScript.add("Page 3: " + characterName);
        }
        return npcScript;
    }

    public List<String> getBattleScript(){
        npcBattleScript.clear();

        File npcTextFile = new File("resources/Dialogue/" + characterName + "1.csv");
        if(npcTextFile.exists()) {
            addBattleDialogue("resources/Dialogue/" + characterName + "1.csv");
        }
        else{
            npcBattleScript.add("Page 1: " + characterName);
            npcBattleScript.add("Page 2: " + characterName);
            npcBattleScript.add("Page 3: " + characterName);
        }
        return npcBattleScript;
    }

    private void addDialogue(String fileName) {
        BufferedReader csvReader = returnBufferedReader(fileName);
        try{
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                data = this.wrapRoundDialogueBox(data);
                npcScript.addAll(Arrays.asList(data));
                }
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addBattleDialogue(String fileName) {
        BufferedReader csvReader = returnBufferedReader(fileName);
        try{
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                data = this.wrapRoundDialogueBox(data);
                npcBattleScript.addAll(Arrays.asList(data));
                }
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String[] wrapRoundDialogueBox(String[] data){
        int widthOfDialogue=50;
        int tmp = 0;
        ArrayList<String> updatedData = new ArrayList<>();
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

    public int getCurrentBattleIndex() {
        return currentBattleIndex;
    }

    public void incrementCurrentBattleIndex() {
        currentBattleIndex++;
    }

    public void resetBattleScript() {
        currentBattleIndex = 1;
    }

    public void setHasCompletedBattle(boolean hasCompleted){
        hasCompletedBattle = hasCompleted;
    }

    public boolean hasCompletedBattle(){
        return hasCompletedBattle;
    }

}
