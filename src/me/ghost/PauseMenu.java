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


public class PauseMenu {

    private final BattleWindow battleWindow = new BattleWindow();
    private final boolean pauseOpen = true;
    private final List<Drawable> toDraw = new ArrayList<>();
    private Texture backgroundTexture = TextureType.PAUSEBACKGROUND.getTexture();
    private final RectangleShape background = createTexturedRectangle(640, 480, 0, 0, backgroundTexture);
    private final int pauseX = 165;
    private final int quitX = 165;
    private final int pauseY = 150;
    private final int quitY = 300;
    private final int buttonLength = 320;
    private final int buttonHeight = 60;
    private final RectangleShape pauseBackground = createSimpleRectangle(buttonLength, buttonHeight, 165, 150, 120, 119, 161);
    private final RectangleShape exitBackground = createSimpleRectangle(buttonLength, buttonHeight, 165, 300, 120, 119, 161);
    private Drawable[] itemsToDraw = {background, pauseBackground, exitBackground};  
    private final Font font = FontType.ROBOTO.getFont();  
    private boolean mouseButtonclicked;
    private Vector2f mousePosition;
    private Boolean quited = false;

    private Text text;

    public PauseMenu() {
        //
        toDraw.addAll(Arrays.asList(itemsToDraw));
        writeText("Paused...", 155, 150);
        writeText("Press here to quit...", 120, 300);
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
        text = new Text(function, font, 20) {
            {
                this.setPosition(x + 120, y + 20);
            }
        };

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
            //handle quit
            if(mousePosition!=null){
                if(mousePosition.x <= quitX + buttonLength && mousePosition.x >= quitX && mousePosition.y <= quitY + buttonHeight && mousePosition.y >= quitY){
                    quited=true;
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