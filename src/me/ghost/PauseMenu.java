package me.ghost;

import me.ghost.battle.BattleWindow;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;


public class PauseMenu {

    private final BattleWindow battleWindow = new BattleWindow();
    private final boolean pauseOpen = true;
    private final List<Drawable> toDraw = new ArrayList<>();
    private final RectangleShape background;
    private float xBoundary;
    private float yBoundary;
    private Vector2f dimensions;
    

    public PauseMenu() {
        //

        dimensions = new Vector2f(640, 480);

        background = new RectangleShape(dimensions) {
            {
                this.setPosition(0, 0);
                this.setSize(dimensions);
                this.setFillColor(new Color(98,52,18));
            }
        };

        toDraw.add(background);
    }

    public void draw(RenderWindow window) {
        toDraw.forEach(window::draw);
    }

    public void handleInput(KeyEvent event) {
        if(event.type == Event.Type.KEY_PRESSED){
            if (event.key == Keyboard.Key.W) {
                //
            }
            if (event.key == Keyboard.Key.A) {
                //
            }
            if (event.key == Keyboard.Key.S) {
                //
            }
            if (event.key == Keyboard.Key.D) {
                //
            }
        }
    }

    private RectangleShape createDrawnRectangle(int width, int height, int x, int y, int r, int g, int b) {
        Vector2f dimensions = new Vector2f(width, height);
        RectangleShape textBackground = new RectangleShape(dimensions);
        textBackground.setPosition(x, y);
        textBackground.setSize(dimensions);
        textBackground.setFillColor(new Color(r, g, b));
        textBackground.setOutlineThickness(1);
        toDraw.add(textBackground);
        return textBackground;
    }

}
