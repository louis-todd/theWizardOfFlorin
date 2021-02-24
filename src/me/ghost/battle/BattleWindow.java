package me.ghost.battle;

import java.util.ArrayList;
import java.util.List;
import me.ghost.data.FontType;
import org.jsfml.graphics.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2f;

public class BattleWindow {

    private final List<Drawable> toDraw = new ArrayList<>();
    private final List<CircleShape> healthCircles = new ArrayList<>();
    private final RectangleShape background = createDrawnRectangle(640, 480, 0, 0, 0, 0, 0);
    private final RectangleShape ghostArea = createDrawnRectangle(500, 180, 70, 10, 255, 255, 255);
    private final RectangleShape playerArea = createDrawnRectangle(350, 100, 150, 210, 255, 255, 255);
    private final Text dialogueText = new Text("...REMINDER: Use arrow keys to avoid the ghosts wrath!!...", FontType.ROBOTO.getFont());

    /**
     * Sole constructor for battle window which draws the arena for battle.
     */
    public BattleWindow() {
        this.createHealthCircles();
        dialogueText.setPosition(20, 370);
        dialogueText.setCharacterSize(18);
        this.toDraw.add(dialogueText);
    }

    /**
     * Creates a set of circles which represent health level.
     */
    private void createHealthCircles() {
        for (int i = 50; i <= 100; i += 25) {
            healthCircles.add(healthCircle(i, 340));
        }
    }
    
    /** 
     * Creates and draws a rectangle
     * @param width sets the width of the rectangle.
     * @param height sets the height of the rectangle.
     * @param x sets the x position of the rectangle.
     * @param y sets the y position of the rectangle.
     * @param r the R value of the RGB.
     * @param g the R value of the RGB.
     * @param b the R value of the RGB.
     * @return the drawn rectangle
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
     * Creates and draws a circle, used to indicate the players health level.
     * @param x
     * @param y
     * @return a circle which represents a health component.
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
     * Gets the collection of circles that represent the players secondary health levels, i.e. health during current battle.
     * @return List<CircleShape>
     */
    public List<CircleShape> getHealthCircles() {
        return healthCircles;
    }
    
    /** 
     * @return the area for the NPC.
     */
    public RectangleShape getGhostArea() {
        return ghostArea;
    }
    
    /** 
     * @return the center of the NPCs area.
     */
    public Vector2f getGhostAreaCentre() {
        return (new Vector2f(ghostArea.getPosition().x + ghostArea.getSize().x / 2, ghostArea.getPosition().y + ghostArea.getSize().y / 2));
    }

    /** 
     * @return the area in which the player can move within.
     */
    public RectangleShape getPlayerArea() {
        return playerArea;
    }

    /** 
     * @return the background of the window.
     */
    public RectangleShape getBackground() {
        return background;
    }
    
    /** 
     * @return the center of the players area.
     */
    public Vector2f getPlayerAreaCentre() {
        return (new Vector2f(playerArea.getPosition().x + playerArea.getSize().x / 2, playerArea.getPosition().y + playerArea.getSize().y / 2));
    }
    
    /** 
     * @return the collection of items to draw.
     */
    public List<Drawable> getToDraw() {
        return toDraw;
    }

    /** 
     * @param newText sets the text to be displayed in the battle dialogue box.
     */
    public void setBattleText(String newText){
        this.dialogueText.setString(newText);
    }
    
    /** 
     * @return the text currently being displayed in the dialogue box.
     */
    public Text getBattleText(){
        return this.dialogueText;
    }
}