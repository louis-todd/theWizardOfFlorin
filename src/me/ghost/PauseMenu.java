package me.ghost;

import me.ghost.battle.BattleWindow;
import me.ghost.data.FontType;
import me.ghost.data.TextureType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.w3c.dom.css.Rect;


public class PauseMenu {

    private final BattleWindow battleWindow = new BattleWindow();
    private final boolean pauseOpen = true;
    private final List<Drawable> toDraw = new ArrayList<>();
    private final int pauseX = 165;
    private final int quitX = 165;
    private final int pauseY = 150;
    private final int quitY = 300;
    private final int buttonLength = 320;
    private final int buttonHeight = 60;


    private final Font font = FontType.ROBOTO.getFont();  
    private boolean mouseButtonclicked;
    private Vector2f mousePosition;
    private Boolean quited = false;
    private final RectangleShape background = createTexturedRectangle(640, 480, 0, 0, TextureType.SQUARE16.getTexture());
    private final RectangleShape exitBackground = createSimpleRectangle(buttonLength, buttonHeight, 165, 300, 120, 119, 161);
    private Drawable[] itemsToDraw = {background, exitBackground};
    private Text text;

    public PauseMenu(boolean pause, boolean won) {
        //If pause is true then it loads the pause, if false it loads the end screen
        toDraw.addAll(Arrays.asList(itemsToDraw));

        if(!won) {
            if (pause) {
                writeText("Paused...", 155, 150);
                writeText("Press here to quit...", 120, 300);
            } else {
                writeText("You have lost all of your lives."+ System.lineSeparator() + "  click anywhere to quit", 80, 135);
            }
        } else {
            writeText("Congratulations... you've Won!", 60, 150);
            writeText("Click anywhere to exit", 100, 300);
        }

    }

    public void draw(RenderWindow window) {
        checkMouse();
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

    public void writeText(String function, int x, int y) {
        // Set and format the character's name
        text = new Text(function, font, 20);
        text.setPosition(x + 120, y + 20);


        toDraw.add(text);
    }


    private RectangleShape createTexturedRectangle(int width, int height, int x, int y, Texture rectangleTexture) {
        Vector2f dimensions = new Vector2f(width, height);
        RectangleShape textBackground = new RectangleShape(dimensions);
        textBackground.setPosition(x, y);
        textBackground.setSize(dimensions);
        // textBackground.setFillColor(new Color(r, g, b));
        textBackground.setTexture(rectangleTexture);
        textBackground.setOutlineThickness(1);
        toDraw.add(textBackground);
        return textBackground;
    }

    private RectangleShape createSimpleRectangle(int width, int height, int x, int y, int r, int g, int b) {
        Vector2f dimensions = new Vector2f(width, height);
        RectangleShape textBackground = new RectangleShape(dimensions);
        textBackground.setPosition(x, y);
        textBackground.setSize(dimensions);
        textBackground.setFillColor(new Color(r, g, b));
        textBackground.setOutlineThickness(1);
        toDraw.add(textBackground);
        return textBackground;
    }

    private void checkMouse(){
        if(this.mouseButtonclicked){
            //handle quit=
            if(mousePosition!=null){
                System.out.println("GETTING IN HERE");
                if(mousePosition.x <= quitX + buttonLength && mousePosition.x >= quitX && mousePosition.y <= quitY + buttonHeight && mousePosition.y >= quitY){
                    quited=true;
                    exitBackground.setFillColor(new Color(255, 255, 255));
                }
            }
            this.mouseButtonclicked = false;
        }
    }

    public void setMouseButtonclicked(boolean mouseButtonclicked) {
        this.mouseButtonclicked = mouseButtonclicked;
    }

    public void setMousePosition(Vector2f mousePosition) {
        this.mousePosition = mousePosition;
    }

    public Boolean playerHasQuit() {
        return quited;
    }



}
