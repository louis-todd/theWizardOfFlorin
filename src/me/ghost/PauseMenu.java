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

/**
 * this class is for the pause menu UI
 */

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

    /**
     *
     * @param window the window in whuch pause menu is drawn
     */

    public void draw(RenderWindow window) {
        checkMouse();
        toDraw.forEach(window::draw);
    }

    /**
     * this method handles key inputs from the W,A,S and D keys
     * @param event this is the button that will be pressed
     *
     */

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

    /**
     * this method is used to write text on the pause menu
     * @param function
     * @param x this is the X position where the text will be set
     * @param y this si the Y position of the text
     */

    public void writeText(String function, int x, int y) {
        // Set and format the character's name
        text = new Text(function, font, 20);
        text.setPosition(x + 120, y + 20);


        toDraw.add(text);
    }

    /**
     *this method is to return a rectangle shaped text box for the text to go in and this has a default colour
     * @param width width of the textbox
     * @param height height of the text box
     * @param x X position of the text box
     * @param y Y position of the text box
     * @param rectangleTexture the texture of the Text box
     * @return this will return a Text Box
     */


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

    /**
     *this method will return a box also but can add custom colours.
     * @param width width of the text box
     * @param height height of the text box
     * @param x X position of the text box
     * @param y Y position of the text box
     * @param r this is where concentration of Red colour will go
     * @param g this is where concentration of green will go
     * @param b this is where concentration of  blue will go
     * @return returns a box with the custom colour 
     */

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
