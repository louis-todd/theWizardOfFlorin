package me.ghost.battle.dodge;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.system.Vector2f;

import java.util.Random;

public class Projectile extends ConvexShape {

    Vector2f velocity;
    public Projectile(int i, Vector2f setVelocity) {
        super(i);
        velocity = setVelocity;
    }

    private void setRandomColour(){
        Random rand = new Random();
        int r = rand.nextInt();
        int g = rand.nextInt();
        int b = rand.nextInt();
        Color randomColour = new Color(r, g, b);
        this.setFillColor(randomColour);
    }
}
