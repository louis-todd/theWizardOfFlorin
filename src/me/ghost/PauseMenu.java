package me.ghost;

import me.ghost.data.FontType;
import me.ghost.data.TextureType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

/**
 * The pause menu is designed to allow a player to pause the game and return at a later state, and is also reused for the win/lose screen. 
 * The class also provides the ability to quit the game in both cases.
 */

public class PauseMenu {

    private final List<Drawable> toDraw = new ArrayList<>();
    private final int quitX = 165;
    private final int quitY = 300;
    private final int buttonLength = 320;
    private final int buttonHeight = 60;
    private final Font font = FontType.ROBOTO.getFont();  
    private final RectangleShape background = createTexturedRectangle(640, 480, 0, 0, TextureType.SQUARE16.getTexture());
    private final RectangleShape exitBackground = createSimpleRectangle(buttonLength, buttonHeight, 165, 300, 120, 119, 161);
    private boolean mouseButtonclicked;
    private Vector2f mousePosition;
    private boolean quited = false;
    private Drawable[] itemsToDraw = {background, exitBackground};
    private Text text;

    /**
     * Sole constructor for PauseMenu - if pause is true then it loads pause, if false, it loads the end screen.
     * @param pause defines whether the game is currently paused.
     * @param won defines whether the game has been won.
     */
    public PauseMenu(boolean pause, boolean won) {
        toDraw.addAll(Arrays.asList(itemsToDraw));

        if(!won) {
            if (pause) {
                writeText("Paused...", 155, 150);
                writeText("Press here to quit...", 120, 300);
            } else {
                writeText("You have lost all of your lives!"+ System.lineSeparator() + "...click anywhere to quit", 80, 135);
                toDraw.remove(exitBackground);
            }
        } else {
            writeText("Congratulations... you've Won!", 60, 150);
            writeText("Click anywhere to exit", 100, 300);
        }
    }

    /**
     * Draws all items on the pause screen.
     * @param window the window in whuch pause menu is to be drawn.
     */

    public void draw(RenderWindow window) {
        checkMouse();
        toDraw.forEach(window::draw);
    }

    /**
     * Sets the text to appear in the two placeholders drawn within this Pause Menu.
     * @param function sets what this button should be used for.
     * @param x sets the X position where the text should be drawn.
     * @param y sets the Y position where the text should be drawn.
     */
    public void writeText(String function, int x, int y) {
        text = new Text(function, font, 20);
        text.setPosition(x + 120, y + 20);
        toDraw.add(text);
    }

    /**
     * Create a button with texture. 
     * @param width sets the width of the button.
     * @param height sets the height of the button.
     * @param x sets the X position where the box should be drawn.
     * @param y sets the Y position where the box should be drawn.
     * @param rectangleTexture sets the texture of the button.
     * @return the button.
     */
    private RectangleShape createTexturedRectangle(int width, int height, int x, int y, Texture rectangleTexture) {
        Vector2f dimensions = new Vector2f(width, height);
        RectangleShape textBackground = new RectangleShape(dimensions);
        textBackground.setPosition(x, y);
        textBackground.setSize(dimensions);
        textBackground.setTexture(rectangleTexture);
        textBackground.setOutlineThickness(1);
        toDraw.add(textBackground);
        return textBackground;
    }

    /**
     * As createTexturedRectangle, creates a button, however with a set colour rather than a texture. 
     * @param width sets the width of the button.
     * @param height sets the height of the button.
     * @param x sets the X position where the box should be drawn.
     * @param y sets the Y position where the box should be drawn.
     * @param r used as the red value in RGB.
     * @param g used as the green value in RGB.
     * @param b used as the blue value in RGB.
     * @return the button.
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

    /**
     * Handles whether a user is pressing on the quit button.
     */
    private void checkMouse(){
        if(this.mouseButtonclicked){
            if(mousePosition!=null){
                if(mousePosition.x <= quitX + buttonLength && mousePosition.x >= quitX && mousePosition.y <= quitY + buttonHeight && mousePosition.y >= quitY){
                    quited=true;
                    exitBackground.setFillColor(new Color(255, 255, 255));
                }
            }
            this.mouseButtonclicked = false;
        }
    }

    /** 
     * @param mouseButtonclicked sets whether the quit button is being pressed.
     */
    public void setMouseButtonclicked(boolean mouseButtonclicked) {
        this.mouseButtonclicked = mouseButtonclicked;
    }

    
    /** 
     * @param mousePosition sets the position of the mouse.
     */
    public void setMousePosition(Vector2f mousePosition) {
        this.mousePosition = mousePosition;
    }

    
    /** 
     * @return whether the player has quit the game.
     */
    public boolean playerHasQuit() {
        return quited;
    }

}
