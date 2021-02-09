package me.ghost.battle;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.*;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2f;

public class BattleWindow {

    private final List<Drawable> toDraw = new ArrayList<>();
    private final List<CircleShape> healthCircles = new ArrayList<>();
    private final RectangleShape background = createDrawnRectangle(640, 480, 0, 0, 0, 0, 0);
    private final RectangleShape dialogueBox = this.createDrawnRectangle(620, 100, 10, 370, 98, 52, 18);
    private final RectangleShape healthLabel = createDrawnRectangle(30, 20, 10, 340, 255, 30, 0);
    private final RectangleShape ghostArea = createDrawnRectangle(500, 180, 70, 10, 255, 255, 255);
    private final RectangleShape playerArea = createDrawnRectangle(350, 100, 150, 210, 255, 255, 255);

    public BattleWindow() {
        this.createHealthCircles();
    }

    private void createHealthCircles() {
        for (int i = 50; i <= 100; i += 25) {
            healthCircles.add(healthCircle(i, 340));
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

    private CircleShape healthCircle(int x, int y) {
        CircleShape circle = new CircleShape(10);
        circle.setPosition(x, y);
        circle.setFillColor(new Color(255, 0, 255));
        circle.setOutlineThickness(1);
        toDraw.add(circle);
        return circle;
    }

    public List<CircleShape> getHealthCircles() {
        return healthCircles;
    }

    public RectangleShape getGhostArea() {
        return ghostArea;
    }

    public Vector2f getGhostAreaCentre() {
        return (new Vector2f(ghostArea.getPosition().x + ghostArea.getSize().x / 2, ghostArea.getPosition().y + ghostArea.getSize().y / 2));
    }

    public RectangleShape getPlayerArea() {
        return playerArea;
    }

    public Vector2f getPlayerAreaCentre() {
        return (new Vector2f(playerArea.getPosition().x + playerArea.getSize().x / 2, playerArea.getPosition().y + playerArea.getSize().y / 2));
    }

    public List<Drawable> getToDraw() {
        return toDraw;
    }
}