package me.ghost.map;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class Tile extends RectangleShape implements Drawable {

    public Tile(Vector2f position, Texture setTexture) {
        super(position);
        this.setPosition(position.x, position.y);
        this.setTexture(setTexture);
        this.setSize(new Vector2f(16, 16));

    }

}
