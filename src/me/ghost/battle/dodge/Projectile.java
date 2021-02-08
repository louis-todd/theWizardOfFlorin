package me.ghost.battle.dodge;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;

import java.util.Random;

public class Projectile extends CircleShape {

    Vector2f velocity;
    public Projectile(int radius, int i) {
        super(radius, i);
        velocity = setRandomVelocity();
        this.setRandomColour();
    }

    private void setRandomColour(){
        Random rand = new Random();
        int r = rand.nextInt();
        int g = rand.nextInt();
        int b = rand.nextInt();
        Color randomColour = new Color(r, g, b);
        this.setFillColor(randomColour);
    }

    private Vector2f setRandomVelocity(){
        Random rand = new Random();
        int maxx = 5;
        int maxy = 5;
        int minx = -5;
        int miny = 1;
        return(new Vector2f(rand.nextInt(maxx - minx + 1), rand.nextInt(maxy - miny + 1)));
    }

}
