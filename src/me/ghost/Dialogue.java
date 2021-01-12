package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Dialogue implements Drawable {
    private Font simpleFont;
    private String resource;
    private Text text;
    private String toWrite;
    private RectangleShape textBackground;

    public Dialogue(String fontPath, String textToWrite) {
        resource = fontPath;
        simpleFont = new Font();                                    //Set the font
        toWrite = textToWrite;
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

            //Set text, size, and position
            text = new Text(toWrite, simpleFont, 20){{
                this.setPosition(20, 400);
            }};

    }

    public void formatText(){
        Vector2f dimensions = new Vector2f(600,50);
        textBackground = new RectangleShape(dimensions){{
            this.setPosition(20,400);
            this.setSize(dimensions);
            this.setFillColor(new Color(98,52,18));
        }};
    }

    public Text getText(){
        return text;
    }
    public RectangleShape getTextBackground() {return textBackground;}


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        renderTarget.draw(getTextBackground());
        renderTarget.draw(getText());
    }
}