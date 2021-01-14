package me.ghost.Characters;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;

import java.util.List;
import java.util.Map;

public class MoveableCharacter extends Character {

    private boolean wizardColliding;

    public MoveableCharacter(float xPosition, float yPosition, String imagePath) {
        super(xPosition, yPosition, imagePath);
        wizardColliding = false;
    }

    public void moveCharacter(Map<String, Boolean> keyPresses, List<Drawable> toDraw) {
        Sprite npcCollide = null;

        for (Drawable npcs : toDraw) {
            if (this.collides((Sprite) npcs) && (!npcs.equals(this))) {
                wizardColliding = true;
                npcCollide = (Sprite)npcs;
            }
        }
        if (!wizardColliding) {
            if ((keyPresses.get("RIGHT"))) {
                this.move(1, 0);
            }
            if ((keyPresses.get("LEFT"))) {
                this.move(-1, 0);
            }
            if ((keyPresses.get("UP"))) {
                this.move(0, -1);
            }
            if ((keyPresses.get("DOWN"))) {
                this.move(0, 1);
            }
        } else {
            assert npcCollide != null;
            float xDifference = this.collisionRectangle(npcCollide).width;
            float yDifference = this.collisionRectangle(npcCollide).height;

            if (Math.abs(npcCollide.getPosition().y - this.getPosition().y) < 15) {
                if (npcCollide.getPosition().x > this.getPosition().x) {
                    this.move(-xDifference, 0);
                }
                if (npcCollide.getPosition().x < this.getPosition().x) {
                    this.move(xDifference, 0);
                }
            } else {
                if (npcCollide.getPosition().y > this.getPosition().y) {
                    this.move(0, -yDifference);
                }
                if (npcCollide.getPosition().y < this.getPosition().y) {
                    this.move(0, yDifference);
                }
            }

            wizardColliding = false;
        }
    }

    public boolean collides(Sprite npc) {
        return this.getGlobalBounds().intersection(npc.getGlobalBounds()) != null;
    }

    public FloatRect collisionRectangle(Sprite npc){
        return this.getGlobalBounds().intersection((npc.getGlobalBounds()));
    }
}
