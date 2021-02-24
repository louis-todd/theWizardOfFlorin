package me.ghost.battle;

import java.util.ArrayList;
import java.util.List;

import me.ghost.data.FontType;
import org.jsfml.graphics.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2f;

/**
 * this class has the code which is used to make the UI for the battle window
 */

public class BattleWindow {

    private final List<Drawable> toDraw = new ArrayList<>();
    private final List<CircleShape> healthCircles = new ArrayList<>();
    private final RectangleShape background = createDrawnRectangle(640, 480, 0, 0, 0, 0, 0);
    private final RectangleShape dialogueBox = this.createDrawnRectangle(620, 100, 10, 370, 98, 52, 18);
    private final RectangleShape healthLabel = createDrawnRectangle(30, 20, 10, 340, 255, 30, 0);
    private final RectangleShape ghostArea = createDrawnRectangle(500, 180, 70, 10, 255, 255, 255);
    private final RectangleShape playerArea = createDrawnRectangle(350, 100, 150, 210, 255, 255, 255);
    private final Text dialogueText = new Text("...REMINDER: Use arrow keys to avoid the ghosts wrath!!...", FontType.ROBOTO.getFont());

    /**
     * constructor for the battle window
     */

    public BattleWindow() {
        this.createHealthCircles();
        dialogueText.setPosition(20, 370);
        dialogueText.setCharacterSize(18);
        this.toDraw.add(dialogueText);
    }

    /**
     * loop to create health circles
     */

    private void createHealthCircles() {
        for (int i = 50; i <= 100; i += 25) {
            healthCircles.add(healthCircle(i, 340));
        }
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

    /**
     *this method will return a circle .
     * these circles will be used to represent lives of the character and will decrease if
     * the character takes damage

     * @param x X position of the circle
     * @param y Y position of the circle

     * @return returns a circle with the the set colour and size
     */

    private CircleShape healthCircle(int x, int y) {
        CircleShape circle = new CircleShape(10);
        circle.setPosition(x, y);
        circle.setFillColor(new Color(255, 0, 255));
        circle.setOutlineThickness(1);
        toDraw.add(circle);
        return circle;
    }

    /**
     * get method to return health circles
     * @return returns health circle
     */

    public List<CircleShape> getHealthCircles() {
        return healthCircles;
    }

    /**
     * get method to return return the ghost area which is at the top
     * @return returns ghost area
     */

    public RectangleShape getGhostArea() {
        return ghostArea;
    }

    /**
     * get method to return the vectors for the centre of the ghost area
     * @return returns vectors for the centre of the ghost area
     */

    public Vector2f getGhostAreaCentre() {
        return (new Vector2f(ghostArea.getPosition().x + ghostArea.getSize().x / 2, ghostArea.getPosition().y + ghostArea.getSize().y / 2));
    }

    /**
     * get method to return player area which is the middle box
     * @return returns the player area box
     */

    public RectangleShape getPlayerArea() {
        return playerArea;
    }

    /**
     * get method to return background of the whole screen
     * @return returns background
     */

    public RectangleShape getBackground() {
        return background;
    }

    /**
     * get method to return the vector of the centre of player area
     * @return returns vector of the position of the centre of player area
     */

    public Vector2f getPlayerAreaCentre() {
        return (new Vector2f(playerArea.getPosition().x + playerArea.getSize().x / 2, playerArea.getPosition().y + playerArea.getSize().y / 2));
    }

    /**
     * get method to return array list toDraw which has all of the
     * UI items stored inside of it
     * @return returns array list
     */

    public List<Drawable> getToDraw() {
        return toDraw;
    }

    /**
     * set method to set the text on battle screen
     * @return returns text which is set on battle screen
     */

    public void setBattleText(String newText){
        this.dialogueText.setString(newText);
    }

    /**
     * get method to return the text which is already set on battle screen
     * @return returns text already set.
     */

    public Text getBattleText(){
        return this.dialogueText;
    }
}