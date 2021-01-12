package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Dialogue implements Drawable {
    private Font simpleFont;
    private String resource;

    private Text characterName;
    private String speakingCharacter;

    private Text toWrite;
    private String dialogueText;

    private RectangleShape textBackground;
    private Texture boardTexture;

    public Dialogue(String fontPath, String boardTexturePath, String textspeakingCharacter, String dialogueMessage) {
        resource = fontPath;
        simpleFont = new Font();                                    //Set the font
        speakingCharacter = textspeakingCharacter;
        dialogueText = dialogueMessage;
        boardTexture = new Texture();                               //Set image for dialogue box background
        try {
            boardTexture.loadFromFile(Paths.get(boardTexturePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        writeText();
        formatText();
    }

    public void writeText(){

            //Set the font to the downloaded file
            try {
                simpleFont.loadFromFile(Paths.get(resource));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Set characterName, size, and position
            characterName = new Text(speakingCharacter, simpleFont, 20){{
                this.setPosition(120, 320);
            }};

            //Set Message
            toWrite = new Text(dialogueText, simpleFont, 20){{
                this.setPosition(60, 370);
            }};

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
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        renderTarget.draw(textBackground);
        renderTarget.draw(characterName);
        renderTarget.draw(toWrite);
    }
}