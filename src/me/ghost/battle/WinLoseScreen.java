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
    private Text winLossText = null;

    public WinLoseScreen(boolean won) {
        this.setSize(new Vector2f(400, 300));
        this.setPosition(new Vector2f(120, 90));
        this.setFillColor(Color.RED);

        toDraw.add(this);
        toDraw.add(this.getExitButton());
        toDraw.add(this.getButtonText());
        toDraw.add(this.getWinLossText(won));
    }

    public RectangleShape getExitButton(){
        if(this.exitButton == null){
            RectangleShape rectangle = new RectangleShape();
            rectangle.setFillColor(Color.BLACK);
            rectangle.setPosition(new Vector2f(this.getPosition().x + 15, this.getPosition().y + 250));
            rectangle.setSize(new Vector2f(100, 40));
            this.exitButton = rectangle;
        }
        return this.exitButton;
    }

    private Text getButtonText(){
        if(this.buttonText == null){
            Text text = new Text();
            text.setPosition(new Vector2f(this.exitButton.getPosition().x + 35, this.exitButton.getPosition().y + 12));
            text.setString("Exit");
            text.setColor(Color.WHITE);
            text.setCharacterSize(14);
            text.setFont(FontType.ROBOTO.getFont());
            this.buttonText = text;
        }
        return this.buttonText;
    }

    private Text getWinLossText(boolean won) {
        if (this.winLossText == null) {
            Text text = new Text();
            text.setPosition(new Vector2f(this.getPosition().x + 100, this.getPosition().y + 20));
            if (won) {
                text.setString("You've won the battle");
            } else {
                text.setString("You've lost the battle");
            }
            text.setColor(Color.BLACK);
            text.setCharacterSize(20);
            text.setFont(FontType.ROBOTO.getFont());
            this.winLossText = text;
        }
        return this.winLossText;
    }

    public List<Drawable> getToDraw() {
        return toDraw;
    }
}
