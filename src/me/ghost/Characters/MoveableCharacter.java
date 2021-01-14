package me.ghost.Characters;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;

public class MoveableCharacter extends Character {


    public MoveableCharacter(float xPosition, float yPosition, String imagePath) {
        super(xPosition, yPosition, imagePath);
    }

    @Override
    public void move() {

    }

    public boolean collides(Sprite npc) {
        return this.getGlobalBounds().intersection(npc.getGlobalBounds()) != null;
    }

    public FloatRect collisionRectangle(Sprite npc){
        return this.getGlobalBounds().intersection((npc.getGlobalBounds()));
    }
}
