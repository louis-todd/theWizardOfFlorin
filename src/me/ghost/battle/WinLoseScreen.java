package me.ghost.battle;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class WinLoseScreen extends RectangleShape {
    private final FloatRect exitButton = new FloatRect(10, 10, 100, 100);
    public WinLoseScreen(boolean won) {
        this.setSize(new Vector2f(640, 480));
        this.setFillColor(Color.BLACK);

    }
}
