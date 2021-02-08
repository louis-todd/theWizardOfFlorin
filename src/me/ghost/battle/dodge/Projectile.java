package me.ghost.battle.dodge;

import org.jsfml.graphics.ConvexShape;
import org.jsfml.system.Vector2f;

public class Projectile extends ConvexShape {

    Vector2f velocity;
    public Projectile(int i, Vector2f setVelocity) {
        super(i);
        velocity = setVelocity;
    }

}
