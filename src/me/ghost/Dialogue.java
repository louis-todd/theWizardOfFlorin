package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class Dialogue implements Drawable {
    private Font simpleFont;

    private final String speakingCharacter;

    private final String dialogueText;

    private Texture boardTexture;

    private final ArrayList<Drawable> toDraw;

    private Text characterName;

    private Text toWrite;

    private float xBoundary;

    private float yBoundary;
    
    private View currentWorld;

    private Vector2f dimensions;

    private RectangleShape textBackground;



    public Dialogue(View currentWorld, Font font, Texture setBoardTexture, String textspeakingCharacter, String dialogueMessage) {
        simpleFont = new Font();                                    //Set the font
        speakingCharacter = textspeakingCharacter;
        dialogueText = dialogueMessage;
        boardTexture = new Texture();                               //Set image for dialogue box background
        toDraw = new ArrayList<>();

        this.currentWorld = currentWorld;
        xBoundary=(currentWorld.getCenter().x)-300;
        yBoundary=(currentWorld.getCenter().y)+60;

        boardTexture = setBoardTexture;
        simpleFont = font;
        formatText();
        writeText();
    }


    public void writeText() {

        //Set characterName, size, and position
        characterName = new Text(speakingCharacter, simpleFont, 20) {{
            this.setPosition(xBoundary+100, yBoundary+20);
        }};
        toDraw.add(characterName);

        //Set Message
        toWrite = new Text(dialogueText, simpleFont, 20) {{
            this.setPosition(xBoundary+40, yBoundary+70);
        }};
        toDraw.add(toWrite);

    }

    public void formatText(){
        //Set the rectangle for the text to sit in
        dimensions = new Vector2f(600,200);
        // this.setFillColor(new Color(98,52,18));
        textBackground = new RectangleShape(dimensions) {{
            this.setPosition(xBoundary, yBoundary);
            this.setSize(dimensions);
            // this.setFillColor(new Color(98,52,18));
            this.setTexture(boardTexture);
        }};
        toDraw.add(textBackground);
    }

    public void setTextContent(String contentToWrite){
        System.out.println("THE DIALOGUE X SHOULD BE: " + currentWorld.getCenter().x);
        xBoundary=(currentWorld.getCenter().x) - 300;
        yBoundary=(currentWorld.getCenter().y) + 60;
        System.out.println("THE DIALOGUE X IS *************: " + xBoundary);
        toWrite.setString(contentToWrite);
        textBackground.setPosition(xBoundary, yBoundary);
        characterName.setPosition(xBoundary+100, yBoundary+20);
        toWrite.setPosition(xBoundary+40, yBoundary+70);
    }

    public void setCharacterName(String nameToWrite){
        characterName.setString(nameToWrite);
    }

    public String getTextContent(){
        return toWrite.getString();
    }

    public String getCharacterName(){
        return characterName.getString();
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for(Drawable item : toDraw) {
            renderTarget.draw(item);
        }
    }
}