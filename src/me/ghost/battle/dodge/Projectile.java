package me.ghost.battle.dodge;

import me.ghost.Characters.Npc;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Projectile extends CircleShape {

    private Vector2f velocity = null;

    public Projectile(int radius, int i) {
        super(radius, i);

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

    public void applyVelocity(){
        if (this.velocity == null) {
            int maxx = 5;
            int maxy = 5;
            int minx = -5;
            int miny = 1;
            this.velocity = new Vector2f(ThreadLocalRandom.current().nextInt(maxx - minx + 1), ThreadLocalRandom.current().nextInt(maxy - miny + 1));
        }

        this.setPosition(this.getPosition().x + this.velocity.x, this.getPosition().y + this.velocity.y);
    }

    public void thrown(Npc player) {
        this.setPosition(player.getPosition());
        this.applyVelocity();
    }
}
