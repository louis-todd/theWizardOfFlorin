package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Dialogue implements Drawable {

    private final Font font;
    private final View currentWorld;
    private final String speakingCharacter;
    private final String dialogueText;
    private final Texture boardTexture;
    private final List<Drawable> toDraw = new ArrayList<>();
    private String originalInteractor = "";
    private Text characterName;
    private Text toWrite;
    private float xBoundary;
    private float yBoundary;
    private Vector2f dimensions;
    private RectangleShape textBackground;

    /**
     * Soul constructor for the dialogue box that appears at the bottom of the screen when interacting with an NPC.
     * @param currentWorld sets the current view of the world.
     * @param font sets the font of the text within the dialogue box.
     * @param boardTexture sets the texture of the dialogue box background.
     * @param speakingCharacter sets the header of the dialogue box to the character currently speaking.
     * @param dialogueText sets the message to be spoken.
     */
    public Dialogue(View currentWorld, Font font, Texture boardTexture, String speakingCharacter, String dialogueText) {
        this.speakingCharacter = speakingCharacter;
        this.dialogueText = dialogueText;
        this.currentWorld = currentWorld;
        this.boardTexture = boardTexture;
        this.font = font;

        xBoundary = (currentWorld.getCenter().x) - 300;
        yBoundary = (currentWorld.getCenter().y) + 60;

        formatText();
        writeText();
    }

    /**
     * Set the character name and main message to be displayed in the dialogue.
     */
    public void writeText() {
        characterName = new Text(speakingCharacter, font, 20) {
            {
                this.setPosition(xBoundary + 100, yBoundary + 20);
            }
        };
        toDraw.add(characterName);

        toWrite = new Text(dialogueText, font, 20) {
            {
                this.setPosition(xBoundary + 40, yBoundary + 70);
            }
        };
        toDraw.add(toWrite);
    }

    /**
     * Format the textbox for the text to sit in.
     */
    public void formatText() {
        dimensions = new Vector2f(600, 200);
        textBackground = new RectangleShape(dimensions) {
            {
                this.setPosition(xBoundary, yBoundary);
                this.setSize(dimensions);
                this.setTexture(boardTexture);
            }
        };
        toDraw.add(textBackground);
    }

    /** 
     * @param contentToWrite sets the main text within the dialogue graphic.
     */
    public void setTextContent(String contentToWrite) {
        xBoundary = (currentWorld.getCenter().x) - 300;
        yBoundary = (currentWorld.getCenter().y) + 60;
        toWrite.setString(contentToWrite);
        textBackground.setPosition(xBoundary, yBoundary);
        characterName.setPosition(xBoundary + 100, yBoundary + 20);
        toWrite.setPosition(xBoundary + 40, yBoundary + 70);
    }
    
    /** 
     * @param nameToWrite sets the name displayed in the dialogue box.
     */
    public void setCharacterName(String nameToWrite) {
        characterName.setString(nameToWrite);
    }

    /** 
     * @return the text displayed in the message component of the dialogue box.
     */
    public String getTextContent() {
        return toWrite.getString();
    }

    /** 
     * Uses the special characters in the dialogue to determine who should display as speaking in the dialogue box.
     * @return the character name being displayed within the dialogue box.
     */
    public String getCharacterName() {
        if (toWrite.getString().substring(0, 2).equals("££")) {
            toWrite.setString(toWrite.getString().substring(2));
            return "Wizard";
        } 
        if (toWrite.getString().substring(0, 2).equals("$$")) {
            toWrite.setString(toWrite.getString().substring(2));
            return "Whiskers";
        } 
        if (toWrite.getString().substring(0, 2).equals("]]")) {
            toWrite.setString(toWrite.getString().substring(2));
            return "...unknown";
        } 
        if (toWrite.getString().substring(0, 2).equals("%%")) {
            // toWrite.setString(toWrite.getString().substring(2));
            toWrite.setString("AHHHHHHH!");
            return "BATTLE";
        } 
        if (toWrite.getString().substring(0, 2).equals("||")) {
            // toWrite.setString(toWrite.getString().substring(2));
            toWrite.setString("...");
            return "...";
        } 
        else {
            return originalInteractor;
        }
    }

    /** 
     * @param nameToWrite sets the original NPC which has instigated the dialogue.
     */
    public void setOriginalInteractor(String nameToWrite) {
        originalInteractor = nameToWrite;
    }

    /** 
     * @return gets the original NPC which has instigated the dialogue.
     */
    public String getOriginalInteractor() {
        return originalInteractor;
    }

    /** 
     * {@inheritDoc}
     * Draws all components of the dialogue graphic.
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for (Drawable item : toDraw) {
            renderTarget.draw(item);
        }
    }
}