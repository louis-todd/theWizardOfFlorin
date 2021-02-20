package me.ghost.battle;

import me.ghost.data.FontType;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class WinLoseScreen extends RectangleShape {
    private List<Drawable> toDraw = new ArrayList<>();
    private RectangleShape exitButton = null;
    private Text buttonText = null;

    public WinLoseScreen(boolean won) {
        this.setSize(new Vector2f(640, 480));
        this.setFillColor(Color.BLACK);

        toDraw.add(this);
        toDraw.add(this.getExitButton());
        toDraw.add(this.getButtonText());
    }

    private RectangleShape getExitButton(){
        if(this.exitButton == null){
            RectangleShape rectangle = new RectangleShape();
            rectangle.setFillColor(Color.WHITE);
            rectangle.setPosition(new Vector2f(15, 15));
            rectangle.setSize(new Vector2f(100, 25));
            this.exitButton = rectangle;
        }
        return this.exitButton;
    }

    private Text getButtonText(){
        if(this.buttonText == null){
            Text text = new Text();
            text.setPosition(new Vector2f(50, 20));
            text.setString("Exit");
            text.setColor(Color.BLACK);
            text.setCharacterSize(14);
            text.setFont(FontType.ROBOTO.getFont());
            this.buttonText = text;
        }
        return this.buttonText;
    }

    public List<Drawable> getToDraw() {
        return toDraw;
    }
}
