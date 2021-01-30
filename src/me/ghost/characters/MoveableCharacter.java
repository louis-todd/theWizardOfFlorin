package me.ghost.characters;

import me.ghost.map.GameMap;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import java.util.List;
import java.util.Map;

public class MoveableCharacter extends Character {

    private boolean wizardColliding;

    public MoveableCharacter(String characterName, float xPosition, float yPosition, Texture characterTexture) {
        super(characterName, xPosition, yPosition, characterTexture);
        wizardColliding = false;
    }

    public void moveCharacter(Map<String, Boolean> keyPresses, List<Drawable> toDraw, View worldView, FloatRect mapBounds, GameMap currentMap) {
        Npc npcCollide = null;

        for (Drawable npcs : toDraw) {
            if (npcs instanceof Npc) {
                if (this.collides((Npc) npcs)) {
                    wizardColliding = true;
                    npcCollide = (Npc) npcs;
                }

            }
        }
        if (!wizardColliding) {
            if ((keyPresses.get("RIGHT") && !keyPresses.get("SPACE"))) {
                if (this.getPosition().x - mapBounds.width < 0) {
                    this.move(1, 0);
                    worldView.move(-1, 0);
                }
            }
            if ((keyPresses.get("LEFT") && !keyPresses.get("SPACE"))) {
                if (this.getPosition().x > mapBounds.left) {
                    this.move(-1, 0);
                    worldView.move(1, 0);
                }
            }
            if ((keyPresses.get("UP") && !keyPresses.get("SPACE"))) {
                if (this.getPosition().y > mapBounds.top) {
                    this.move(0, -1);
                    worldView.move(0, 1);
                }
            }
            if ((keyPresses.get("DOWN") && !keyPresses.get("SPACE"))) {
                if (this.getPosition().y - mapBounds.height < 0) {
                    this.move(0, 1);
                    worldView.move(0, -1);
                }
            }
            setViewPosition(worldView, this.getPosition(), currentMap);
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

    public boolean dialogueAreaCollide(Npc npc){
        return this.getGlobalBounds().intersection(npc.dialogueArea(4)) != null;
    }


    private void setViewPosition(View mapView, Vector2f position, GameMap currentMap){
        Vector2f viewPosition = new Vector2f(0, 0);
        float x = this.getPosition().x;
        float y = this.getPosition().y;
        boolean changed = false;
        if(position.x > (currentMap.getDrawWidth()+1)*16 - mapView.getSize().x/2){
            x = (currentMap.getDrawWidth()+1)*16 - mapView.getSize().x/2;
            changed = true;
        }
        if(position.x < mapView.getSize().x/2){
            x = mapView.getSize().x/2;
            changed = true;
        }
        if(position.y > (currentMap.getDrawHeight()+1)*16 - mapView.getSize().y/2){
            y = (currentMap.getDrawHeight()+1)*16 - mapView.getSize().y/2;
            changed = true;
        }
        if(position.y < mapView.getSize().y/2){
            y = mapView.getSize().y/2;
            changed = true;
        }
        if (!changed){
            viewPosition = this.getPosition();
        } else {
            viewPosition = new Vector2f(x, y);
        }
        mapView.setCenter(viewPosition);
    }



}
