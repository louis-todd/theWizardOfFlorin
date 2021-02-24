package me.ghost.character;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import me.ghost.CaseInsensitiveMap;
import me.ghost.Item;

public abstract class Character extends Sprite {

    private static ArrayList<Item> items;
    private static Boolean taskInProgress = false;
    private static Character NPCInProgress = null;
    private static ArrayList<Character>  allCharacters = new ArrayList<Character>();
    private final Map<String, Boolean> characterStates = new CaseInsensitiveMap<>();
    private final List<String> npcBattleScript = new ArrayList<>();
    private ArrayList<String> NPCScript = new ArrayList<String>();
    private String characterName;
    private int currentIndex = 1;
    private File npcTextFile;
    private ArrayList<Item> associatedItems = new ArrayList<Item>();
    private Boolean isFirstSuccess = true;
    private int expectedNumberOfItems;
    private int itemsFoundCount=0;
    private int currentBattleIndex = 0;
    private boolean hasCompletedTask = false;

    /**
     * 1/2 constructors - this one has the addition of expectedNumber of items.
     * @param characterName sets the name of this character.
     * @param xPosition sets the X position of this character.
     * @param yPosition sets the Y position of this character.
     * @param characterTexture sets the texture of this character.
     * @param expectedNumberOfItems sets the number of items associated with this character - NPC only.
     */
    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture,
            int expectedNumberOfItems) {
        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
        this.expectedNumberOfItems = expectedNumberOfItems;
        this.initCharacterStates();
        allCharacters.add(this);
    }

    /**
     * 2/2 constructors - this one has the addition of all items within the game.
     * @param characterName sets the name of this character.
     * @param xPosition sets the X position of this character.
     * @param yPosition sets the Y position of this character.
     * @param characterTexture sets the texture of this character.
     * @param items sets all items in the game.
     */
    public Character(String characterName, float xPosition, float yPosition, Texture characterTexture,
            ArrayList<Item> items) {
        Character.items = items;
        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);
        this.characterName = characterName;
    }

    /**
     * Manages the state of items
     */
    public void initCharacterStates() {
        this.characterStates.put("AVAILABLE", false);
        this.characterStates.put("IN PROGRESS", false);
        this.characterStates.put("RESOURCE FOUND", false);
        this.characterStates.put("ALL RESOURCES FOUND", false);
        this.characterStates.put("SUCCESS", false);
    }

    /** 
     * Retrieves the associated CSV for this character.
     * @return the script associated with this character.
     */
    public ArrayList<String> getScript() {
        NPCScript.clear();
        updateCharacterStates();

        if (associatedItems.isEmpty()) {
            if (characterStates.get("AVAILABLE")) {
                serveScript(0);
            } else {
                serveScript(3);
            }
        } else {
            if (characterStates.get("IN PROGRESS")) {
                if (itemsFoundCount!=expectedNumberOfItems && itemsFoundCount>0){
                    serveScript(4);
                } 
                else if (itemsFoundCount==0){
                    serveScript(0);
                }
                else {
                    serveScript(3);
                }
            } else {
                if (characterStates.get("RESOURCE FOUND") && (isFirstSuccess || characterStates.get("SUCCESS"))) {
                    isFirstSuccess = false;
                    if(associatedItems.size() == expectedNumberOfItems){
                        serveScript(2);
                    }
                    else{
                        serveScript(4);
                    }
                } else {
                    serveScript(3);
                }
            }
        }
        return NPCScript;
    }

    
    /** 
     * Gets the script from the associated CSV.
     * @param csvFileNumb sets which CSV retrieved dependent on progress of this character's quest.
     */
    private void serveScript(int csvFileNumb) {
        if (csvFileNumb == 0) {
            npcTextFile = new File("resources/Dialogue/" + characterName + "/" + characterName + ".csv");
        } else {
            npcTextFile = new File("resources/Dialogue/" + characterName + "/" + characterName + csvFileNumb + ".csv");
        }
        if (npcTextFile.exists()) {
            addDialogue(npcTextFile.getPath());
        } else {
            NPCScript.add("ERROR 1: File not found" + characterName);
        }
    }

    /**
     * Updates state of this character's quest.
     */
    private void updateCharacterStates() {
        if (associatedItems.isEmpty()) {
            if (taskInProgress && !(NPCInProgress.associatedItems.isEmpty())) {
                if(NPCInProgress!=this){
                    characterStates.put("AVAILABLE", false);
                }
            }
            else{
                characterStates.put("AVAILABLE", true);
                NPCInProgress = this;
                taskInProgress=true;
            }
        }
        else{
            if(NPCInProgress == this){
                characterStates.put("IN PROGRESS", true);
                taskInProgress = true;
            }
            else{
                characterStates.put("AVAILABLE", false);
            }
            for(Item item : associatedItems){
                if(item.isFound() && !(item.hasBeenCounted())){
                    itemsFoundCount++;
                    item.setAsCounted();
                }
                if(itemsFoundCount == expectedNumberOfItems){
                    this.characterStates.put("ALL RESOURCES FOUND", true);
                    characterStates.put("AVAILABLE", false);
                    characterStates.put("IN PROGRESS", false);
                    this.setHasCompletedTask(true);
                }
                if(itemsFoundCount>0){
                    characterStates.put("RESOURCE FOUND", true);
                }
            }
        }
    }

    
    /** 
     * @return the script to be displayed within the battle window.
     */
    public List<String> getBattleScript(){
        npcBattleScript.clear();

        File npcTextFile = new File("resources/BattleDialogue/" + characterName + ".csv");
        if(npcTextFile.exists()) {
            addBattleDialogue("resources/BattleDialogue/" + characterName + ".csv");
        }
        else{
            npcBattleScript.add("Page 1: " + characterName);
            npcBattleScript.add("Page 2: " + characterName);
            npcBattleScript.add("Page 3: " + characterName);
        }
        return npcBattleScript;
    }

    /** 
     * Retrieves the dialogue associated with this character - parsing special characters to get items.
     * @param fileName sets the name of the file to be read in as dialogue.
     */
    private void addDialogue(String fileName) {
        BufferedReader csvReader = returnBufferedReader(fileName);
        characterStates.put("SUCCESS", false);
        try{
            String row;
            while((row = csvReader.readLine()) != null){
                if(row.contains("@")){
                    characterStates.put("SUCCESS", true);
                    row=row.replace('@', ' ');
                    this.setHasCompletedTask(true);
                }
                if(row.contains("\\")){
                    int itemStart=-1;
                    itemStart = row.indexOf("\\");
                    String itemList="";
                    if(itemStart!=-1){
                        itemList = row.substring(itemStart+2);

                        //at start of item list - extract items from itemList
                        String[] RiddleItems = itemList.split(" ");
                        for (Item potentialItem : Character.items) {
                            for(String itemName : RiddleItems){
                                if (potentialItem.getName().equals(itemName)) {
                                    if(!associatedItems.contains(potentialItem)) {
                                        associatedItems.add(potentialItem);
                                    }
                                    potentialItem.setAsAvailableToCollect(true);
                                }
                            }
                        }
                        row=row.substring(0, itemStart);
                    }
                }
                String[] data = row.split(",,");
                data = this.wrapRoundDialogueBox(data);
                NPCScript.addAll(Arrays.asList(data));
            }
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /** 
     * Retrieves the battle dialogue associated with this character.
     * @param fileName sets the name of the file to be read in as dialogue.
     */
    private void addBattleDialogue(String fileName) {
        BufferedReader csvReader = returnBufferedReader(fileName);
        try{
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",,");
                data = this.wrapRoundDialogueBox(data);
                npcBattleScript.addAll(Arrays.asList(data));
            }
            csvReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /** 
     * Modifies dialogue to fit the size of the dialogue box.
     * @param data sets the sentence that is appearing in the box at that current time.
     * @return the new string with a return where the text is too long.
     */
    private String[] wrapRoundDialogueBox(String[] data){
        int widthOfDialogue=50;
        int tmp = 0;
        ArrayList<String> updatedData = new ArrayList<>();
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

    
    /** 
     * Surveys text to find special characters, and if found; extracts the items.
     * @param sentence sets the sentence that is currently to be surveyed.
     * @return the positions of where to extract the item from.
     */
    private int[] checkForItem(String sentence){
        String item = "";
        int itemStart = sentence.indexOf("**");
        int itemEnd = sentence.lastIndexOf("**");
        if(itemStart!=-1 && itemEnd!=-1){
            item = sentence.substring(sentence.indexOf("**")+2, sentence.lastIndexOf("**"));
            for (Item potentialItem : Character.items) {
                if (potentialItem.getName().equals(item)) {
                    if(!associatedItems.contains(potentialItem)) {
                        associatedItems.add(potentialItem);
                    }
                    potentialItem.setAsAvailableToCollect(true);
                }
            }
        }
        int[] positions = {itemStart, itemEnd};
        return positions;
    }
    
    /** 
     * @param fileName sets the file to be read.
     * @return BufferedReader
     */
    private BufferedReader returnBufferedReader(String fileName) {
        return new BufferedReader(new InputStreamReader(getFileStream(fileName)));
    }
    
    /** 
     * @param fileName
     * @return InputStream
     */
    private InputStream getFileStream(String fileName) {
            InputStream fileStream = null;
        try {
                fileStream = new DataInputStream(new FileInputStream(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileStream;
    }

    /** 
     * @return the name of this character.
     */
    public String getName() {
        return characterName;
    }
    
    /** 
     * @param name sets the name of the character to search for.
     * @return the characters with the associated name.
     */
    public static Character getCharacterByName(String name){
        for(Character each : allCharacters){
            if(each.getName() == name){
                return each;
            }
        }
        return null;
    }

    /** 
     * @return the current position in the CSV.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Step to the next line in the CSV.
     */
    public void incrementCurrentIndex() {
        currentIndex++;
    }

    /**
     * Set the pointer within the CSV to the beginning.
     */
    public void resetScript() {
        if(characterStates.get("SUCCESS") == true){
            taskInProgress=false;
            characterStates.put("SUCCESS", false);
        }
        currentIndex = 1;
    }

    /** 
     * @return the current position in the CSV.
     */
    public int getCurrentBattleIndex() {
        return currentBattleIndex;
    }

    /**
     * Step to the next line in the CSV.
     */
    public void incrementCurrentBattleIndex() {
        currentBattleIndex++;
    }

    /**
     * Set the pointer within the CSV to the beginning.
     */
    public void resetBattleScript() {
        currentBattleIndex = 1;
    }

    /** 
     * @param hasCompleted sets whether this ghosts mission has been resolved - be it battle or fetch quest.
     */
    public void setHasCompletedTask(boolean hasCompleted){
        hasCompletedTask = hasCompleted;
    }

    /** 
     * @return whether the player has completed this characters associated task.
     */
    public boolean hasCompletedTask(){
        return hasCompletedTask;
    }
    
    /** 
     * @return all items in the game.
     */
    public static ArrayList<Item> getItems(){
        return Character.items;
    }

    /**
     * Manages ordering of when NPCs appear in game.
     */
    public void setAssociatedNPCsToShow(){
        switch (this.characterName) {
            case "Mayor":
                if(Character.getCharacterByName("Snuffles") instanceof Npc){
                    ((Npc) Character.getCharacterByName("Snuffles")).setShouldDraw(true);
                }
                break;
            case "Snuffles":
                if(Character.getCharacterByName("Tree") instanceof Npc){
                    ((Npc) Character.getCharacterByName("Tree")).setShouldDraw(true);
                }
                break;
            case "Tree":
                if(Character.getCharacterByName("CrazyJoe") instanceof Npc){
                    ((Npc) Character.getCharacterByName("CrazyJoe")).setShouldDraw(true);
                }
                if(Character.getCharacterByName("Summer") instanceof Npc){
                    ((Npc) Character.getCharacterByName("Summer")).setShouldDraw(true);
                }
                break;
            case "CrazyJoe":
                if(Character.getCharacterByName("PirateJack") instanceof Npc){
                    ((Npc) Character.getCharacterByName("PirateJack")).setShouldDraw(true);
                }
                break;
            case "PirateJack":
                if(Character.getCharacterByName("Sibirius") instanceof Npc){
                    ((Npc) Character.getCharacterByName("Sibirius")).setShouldDraw(true);
                }
                break;
            case "Sibirius":
                if(Character.getCharacterByName("Gluttony") instanceof Npc){
                    ((Npc) Character.getCharacterByName("Gluttony")).setShouldDraw(true);
                }
                break;
            default:
                break;
        }
    }

}
