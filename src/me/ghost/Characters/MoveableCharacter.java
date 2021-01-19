package me.ghost.Characters;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;

import java.util.List;
import java.util.Map;

public class MoveableCharacter extends Character {

    private boolean wizardColliding;

    public MoveableCharacter(float xPosition, float yPosition, Texture characterTexture) {
        super(xPosition, yPosition, characterTexture);
        wizardColliding = false;
    }

    public void moveCharacter(Map<String, Boolean> keyPresses, List<Drawable> toDraw) {
        Npc npcCollide = null;

        for (Drawable npcs : toDraw) {
            if (npcs instanceof Npc) {
                if (this.collides((Npc) npcs)) {
                    wizardColliding = true;
                    npcCollide = (Npc) npcs;
                }

                if (this.dialogueAreaCollide((Npc) npcs)) {
                    System.out.println("Colliding with dialogue");
                }
            }
        }
        if (!wizardColliding) {
            if ((keyPresses.get("RIGHT") && !keyPresses.get("SPACE"))) {
                this.move(1, 0);
            }
            if ((keyPresses.get("LEFT") && !keyPresses.get("SPACE"))) {
                this.move(-1, 0);
            }
            if ((keyPresses.get("UP") && !keyPresses.get("SPACE"))) {
                this.move(0, -1);
            }
            if ((keyPresses.get("DOWN") && !keyPresses.get("SPACE"))) {
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

    private boolean collides(Npc npc) {
        return this.getGlobalBounds().intersection(npc.getGlobalBounds()) != null;
    }

    private FloatRect collisionRectangle(Npc npc){
        return this.getGlobalBounds().intersection((npc.getGlobalBounds()));
    }

    private boolean dialogueAreaCollide(Npc npc){
        return this.getGlobalBounds().intersection(npc.dialogueArea()) != null;
    }
}
