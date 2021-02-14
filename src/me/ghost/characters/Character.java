package me.ghost.characters;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import me.ghost.CaseInsensitiveMap;
import me.ghost.Item;

public abstract class Character extends Sprite {

    private ArrayList<String> NPCScript = new ArrayList<String>();
    private String characterName;
    private int currentIndex = 1;
    private File npcTextFile;

    // variables concerning item pickup 
    private static ArrayList<Item> items;
    private Item associatedItem = null;
    private static Boolean taskInProgress = false;
    private static Character NPCInProgress = null;
    private final Map<String, Boolean> characterStates = new CaseInsensitiveMap<>();





    
    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture) {

        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
        this.initCharacterStates();
    }

    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture, ArrayList<Item> items) {

        Character.items = items;
        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
    }








    public void initCharacterStates() {
        this.characterStates.put("AVAILABLE", false);
        this.characterStates.put("IN PROGRESS", false);
        this.characterStates.put("RESOURCE FOUND", false);
    }


    public ArrayList<String> getScript(){
        NPCScript.clear();
        updateCharacterStates(); 

        if(associatedItem == null){
            //if available serve that script
            if(characterStates.get("AVAILABLE")){
                System.out.println("AVAILABLE TO INTERACT WITH!");
                serveScript(0);
            }
            //if unavailable serve generic script
            else{
                System.out.println("AVAILABLE IS FALSE");
                serveScript(3);
            }
        }
        else{
            //if in progress and resource has not been found, serve general response
            if(characterStates.get("IN PROGRESS")){
                System.out.println("MY MISSION IS IN PROGRESS!");
                serveScript(3);
            }
            //if in progress and resource has been found, serve congrats
            else{
                System.out.println("YOU'VE FOUND WHAT I WAS LOOKING FOR - STOP BOTHERING ME!");
                serveScript(2);
            }
        }
        return NPCScript;
    }

    private void serveScript(int csvFileNumb){
        if(csvFileNumb==0){
            npcTextFile = new File("resources/Dialogue/" + characterName + ".csv"); 
        }
        else{
            npcTextFile = new File("resources/Dialogue/" + characterName + csvFileNumb + ".csv");
        }
        if(npcTextFile.exists()) {
            addDialogue(npcTextFile.getPath());
        }
        else{
            NPCScript.add("ERROR 1: File not found" + characterName);
        }
    }

    private void updateCharacterStates(){
        if(this.associatedItem == null){
            System.out.println("1");
            //check if available to talk to  - handle available/unavailable

            //if theres a task in progress and its not mine set to false
            if(taskInProgress){
                System.out.println("2");
                if(NPCInProgress!=this){
                    characterStates.put("AVAILABLE", false);
                    System.out.println("3");
                }
            }
            //if theres a task in progress and its mine - set all to true
            else{
                System.out.println("4: SET AVAILABLE TO TRUE");
                characterStates.put("AVAILABLE", true);
                NPCInProgress = this;
            }
        }
        //if this character has been already assigned an item
        else{
            System.out.println("5");
            //check if in progress - handle in progress
            if(NPCInProgress == this){
                System.out.println("6");
                characterStates.put("IN PROGRESS", true);
                taskInProgress = true;
            }
            else{
                System.out.println("7");
                characterStates.put("AVAILABLE", false);
            }
        }
    }

    private void addDialogue(String fileName) {
        BufferedReader csvReader = returnBufferedReader(fileName);
        try{
            String row;
            while((row = csvReader.readLine()) != null){
                if(row.contains("@")){
                    if(associatedItem!=null){
                        characterStates.put("RESOURCE FOUND", true);
                        row=row.replace('@', ' ');
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
                    potentialItem.setAsAvailableToCollect(true);
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
        if(characterStates.get("RESOURCE FOUND") == true){
            if(associatedItem!=null){
                associatedItem.setAshasBeenReportedAsFound(true);
            }
        }
        currentIndex = 1;
    }

    public static ArrayList<Item> getItems(){
        return Character.items;
    }

}
