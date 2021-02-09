package me.ghost.characters;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import me.ghost.Item;

public abstract class Character extends Sprite {

    private String[] script;
    private ArrayList<String> NPCScript = new ArrayList<String>();
    private String characterName;
    private int currentIndex = 1;
    private File npcTextFile;
    private static ArrayList<Item> items;
    private Item associatedItem = null;
    private Boolean npcResolved = false;

    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture) {

        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
    }
    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture, ArrayList<Item> items) {

        Character.items = items;
        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
    }

    public ArrayList<String> getScript(){
        NPCScript.clear();

        if(!(this.associatedItem == null)){
            if(this.associatedItem.isFound() && !(this.associatedItem.hasBeenReportedAsFound())){
                npcTextFile = new File("resources/Dialogue/" + characterName + "2.csv");
                if(npcTextFile.exists()) {
                    addDialogue(npcTextFile.getPath());
                }
                else{
                    NPCScript.add("ERROR 1: File not found" + characterName);
                }
            }
            else{
                if(this.associatedItem.hasBeenReportedAsFound()){
                    npcTextFile = new File("resources/Dialogue/" + characterName + "3.csv");
                    if(npcTextFile.exists()) {
                        addDialogue(npcTextFile.getPath());
                    }
                    else{
                        NPCScript.add("ERROR 2: File not found" + characterName);
                    }
                }
                else{
                    npcTextFile = new File("resources/Dialogue/" + characterName + ".csv");
                    if(npcTextFile.exists()) {
                        addDialogue(npcTextFile.getPath());
                    }
                    else{
                        NPCScript.add("ERROR 3: File not found" + characterName);
                    }
                }
            }
        }
        else{
            npcTextFile = new File("resources/Dialogue/" + characterName + ".csv");
            if(npcTextFile.exists()) {
                addDialogue(npcTextFile.getPath());
            }
            else{
                NPCScript.add("ERROR 4: File not found" + characterName);
            }
        }
        
        return NPCScript;
    }

    private void addDialogue(String fileName) {
        BufferedReader csvReader = returnBufferedReader(fileName);
        npcResolved=false;
        try{
            String row;
            while((row = csvReader.readLine()) != null){
                if(row.contains("@")){
                    if(associatedItem!=null){
                        npcResolved=true;
                    }
                }
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
            int[] positions = this.checkForItem(sentence);
            if(positions[0]!=-1 && positions[1]!=-1){
                sentence = sentence.substring(0, positions[0]) + sentence.substring(positions[0]+2, positions[1]) + sentence.substring(positions[1]+2, sentence.length());
            }
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

    private int[] checkForItem(String sentence){
        String item = "";
        int itemStart = sentence.indexOf("**");
        int itemEnd = sentence.lastIndexOf("**");
        if(itemStart!=-1 && itemEnd!=-1){
            item = sentence.substring(sentence.indexOf("**")+2, sentence.lastIndexOf("**"));
            for (Item potentialItem : Character.items) {
                if (potentialItem.getName().equals(item)) {
                    this.associatedItem = potentialItem;
                    potentialItem.setAsAvailibleToCollect(true);
                }
            }
        }
        int[] positions = {itemStart, itemEnd};
        return positions;
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
        if(npcResolved==true){
            System.out.println("Hey look I do set reported to true!");
            associatedItem.setAshasBeenReportedAsFound(true);
        }
        currentIndex = 1;
    }

    public static ArrayList<Item> getItems(){
        return Character.items;
    }

}
