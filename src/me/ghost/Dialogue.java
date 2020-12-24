package me.ghost;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Dialogue {
    private Font simpleFont;
    private String resource;
    private Text text;
    private String toWrite;

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
        Vector2f dimensions = new Vector2f(3,3);
        RectangleShape textBackground = new RectangleShape(dimensions);
    }

    public Text getText(){
        return text;
    }
}