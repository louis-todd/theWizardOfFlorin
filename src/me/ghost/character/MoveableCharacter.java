package me.ghost.character;

import me.ghost.data.TextureType;
import me.ghost.Item;
import me.ghost.map.GameMap;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class MoveableCharacter extends Character {

    private boolean wizardColliding;
    private boolean isFirst = false;

    public MoveableCharacter(String characterName, float xPosition, float yPosition, Texture characterTexture) {
        super(characterName, xPosition, yPosition, characterTexture);
        wizardColliding = false;
    }

    public MoveableCharacter(String characterName, float xPosition, float yPosition, Texture characterTexture, ArrayList<Item> items) {
        super(characterName, xPosition, yPosition, characterTexture, items);
        wizardColliding = false;
    }

    public void moveCharacter(Map<String, Boolean> keyPresses, List<Drawable> toDraw, View worldView, GameMap currentMap) {
        Npc npcCollide = null;
        Item itemCollide = null;

        int index = 0;
        for (Drawable obstacle : toDraw) {
            if (this.collides(obstacle)) {
                if (obstacle instanceof Npc) {
                    if (((Npc) obstacle).shouldDraw()) {
                        npcCollide = (Npc) obstacle;
                        wizardColliding = true;
                    }
                }
                if (obstacle instanceof Item) {
                    if (!((Item) obstacle).isFound() && ((Item) obstacle).availableToCollect()) {
                        itemCollide = (Item) obstacle;
                        wizardColliding = true;
                    }
                }
            }
            index++;
        }
        if (!wizardColliding) {
            isFirst = !isFirst;
            if ((keyPresses.get("RIGHT") && !keyPresses.get("SPACE"))) {
                if (this.getPosition().x - currentMap.getMapBounds().width < 0) {
                    if(isFirst){
                        this.setTexture(TextureType.RIGHTSPRITE.getTexture());
                    }
                    else{
                        this.setTexture(TextureType.RIGHTSTILL.getTexture());
                    }
                    this.move(1, 0);
                }
            }
            if ((keyPresses.get("LEFT") && !keyPresses.get("SPACE"))) {
                if (this.getPosition().x > currentMap.getMapBounds().left) {
                    if(isFirst){
                        this.setTexture(TextureType.LEFTSPRITE.getTexture());
                    }
                    else{
                        this.setTexture(TextureType.LEFTSTILL.getTexture());
                    }
                    this.move(-1, 0);
                }
            }
            if ((keyPresses.get("UP") && !keyPresses.get("SPACE"))) {
                if (this.getPosition().y > currentMap.getMapBounds().top) {
                    this.move(0, -1);
                }
            }
            if ((keyPresses.get("DOWN") && !keyPresses.get("SPACE"))) {
                if (this.getPosition().y - currentMap.getMapBounds().height < 0) {
                    this.move(0, 1);
                }
            }
            setViewPosition(worldView, this.getPosition(), currentMap);
        } else if (npcCollide != null) {
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
        } else if (itemCollide != null) {
            float xDifference = this.collisionRectangle(itemCollide).width;
            float yDifference = this.collisionRectangle(itemCollide).height;

            if (Math.abs(itemCollide.getPosition().y - this.getPosition().y) < 15) {
                if (itemCollide.getPosition().x > this.getPosition().x) {
                    this.move(-xDifference, 0);
                }
                if (itemCollide.getPosition().x < this.getPosition().x) {
                    this.move(xDifference, 0);
                }
            } else {
                if (itemCollide.getPosition().y > this.getPosition().y) {
                    this.move(0, -yDifference);
                }
                if (itemCollide.getPosition().y < this.getPosition().y) {
                    this.move(0, yDifference);
                }
            }

            wizardColliding = false;
        }
    }

    private boolean collides(Object obstacle) {
        if (obstacle instanceof Npc || obstacle instanceof Item) {
            return this.getGlobalBounds().intersection(((Sprite) obstacle).getGlobalBounds()) != null;
        }
        return false;
    }

    private FloatRect collisionRectangle(Drawable obstacle) {
        if (obstacle instanceof Npc || obstacle instanceof Item) {
            return this.getGlobalBounds().intersection((((Sprite) obstacle).getGlobalBounds()));
        }
        return null;
    }

    public boolean dialogueAreaCollide(Drawable obstacle) {
        if (obstacle instanceof Npc) {
            return this.getGlobalBounds().intersection(((Npc) obstacle).dialogueArea(4)) != null;
        } else if (obstacle instanceof Item) {
            return this.getGlobalBounds().intersection(((Item) obstacle).dialogueArea(4)) != null;
        }
        return false;
    }

    private void setViewPosition(View mapView, Vector2f position, GameMap currentMap) {
        // new Vector2f(0, 0);
        float x = this.getPosition().x;
        float y = this.getPosition().y;
        boolean changed = false;
        if (position.x > (currentMap.getDrawWidth() + 1) * 16 - mapView.getSize().x / 2) {
            x = (currentMap.getDrawWidth() + 1) * 16 - mapView.getSize().x / 2;
            changed = true;
        }
        if (position.x < mapView.getSize().x / 2) {
            x = mapView.getSize().x / 2;
            changed = true;
        }
        if (position.y > (currentMap.getDrawHeight() + 1) * 16 - mapView.getSize().y / 2) {
            y = (currentMap.getDrawHeight() + 1) * 16 - mapView.getSize().y / 2;
            changed = true;
        }
        if (position.y < mapView.getSize().y / 2) {
            y = mapView.getSize().y / 2;
            changed = true;
        }
        Vector2f viewPosition;
        if (!changed) {
            viewPosition = this.getPosition();
        } else {
            viewPosition = new Vector2f(x, y);
        }
        mapView.setCenter(viewPosition);
    }

}
