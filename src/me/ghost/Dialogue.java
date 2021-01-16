package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Dialogue implements Drawable {
    private Font simpleFont;
    private String resource;

    private Text characterName;
    private String speakingCharacter;

    private Text toWrite;
    private String dialogueText;

    private RectangleShape textBackground;
    private Texture boardTexture;

    private ArrayList<Drawable> toDraw;

    public Dialogue(Font font, Texture setBoardTexture, String textspeakingCharacter, String dialogueMessage) {
        simpleFont = new Font();                                    //Set the font
        speakingCharacter = textspeakingCharacter;
        dialogueText = dialogueMessage;
        boardTexture = new Texture();                               //Set image for dialogue box background

        toDraw = new ArrayList<Drawable>();

        boardTexture = setBoardTexture;
        this.setFont(font);
        formatText();
        writeText();
    }


    public void writeText() {

        //Set characterName, size, and position
        characterName = new Text(speakingCharacter, simpleFont, 20) {{
            this.setPosition(120, 320);
        }};
        toDraw.add(characterName);

        //Set Message
        toWrite = new Text(dialogueText, simpleFont, 20) {{
            this.setPosition(60, 370);
        }};
        toDraw.add(toWrite);

    }

    private void setFont(Font font){
       simpleFont = font;
    }

    public void formatText(){
        //Set the rectangle for the text to sit in
        Vector2f dimensions = new Vector2f(600,200);
        textBackground = new RectangleShape(dimensions){{
            this.setPosition(20,300);
            this.setSize(dimensions);
            // this.setFillColor(new Color(98,52,18));
            this.setTexture(boardTexture);
        }};
        toDraw.add(textBackground);
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        for(Drawable item : toDraw) {
            renderTarget.draw(item);
        }
    }
}