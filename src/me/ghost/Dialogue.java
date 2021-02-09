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

    private Text characterName;
    private Text toWrite;
    private float xBoundary;
    private float yBoundary;
    private Vector2f dimensions;
    private RectangleShape textBackground;

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

    public void writeText() {
        // Set and format the character's name
        characterName = new Text(speakingCharacter, font, 20) {
            {
                this.setPosition(xBoundary + 100, yBoundary + 20);
            }
        };
        toDraw.add(characterName);

        // Set Message
        toWrite = new Text(dialogueText, font, 20) {
            {
                this.setPosition(xBoundary + 40, yBoundary + 70);
            }
        };
        toDraw.add(toWrite);
    }

    public void formatText() {
        // Set the rectangle for the text to sit in
        dimensions = new Vector2f(600, 200);
        // this.setFillColor(new Color(98,52,18));
        textBackground = new RectangleShape(dimensions) {
            {
                this.setPosition(xBoundary, yBoundary);
                this.setSize(dimensions);
                // this.setFillColor(new Color(98,52,18));
                this.setTexture(boardTexture);
            }
        };
        toDraw.add(textBackground);
    }

    public void setTextContent(String contentToWrite) {
        xBoundary = (currentWorld.getCenter().x) - 300;
        yBoundary = (currentWorld.getCenter().y) + 60;
        toWrite.setString(contentToWrite);
        textBackground.setPosition(xBoundary, yBoundary);
        characterName.setPosition(xBoundary + 100, yBoundary + 20);
        toWrite.setPosition(xBoundary + 40, yBoundary + 70);
    }

    public void setCharacterName(String nameToWrite) {
        characterName.setString(nameToWrite);
    }

    public String getTextContent() {
        return toWrite.getString();
    }

    public String getCharacterName() {
        return characterName.getString();
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for (Drawable item : toDraw) {
            renderTarget.draw(item);
        }
    }
}