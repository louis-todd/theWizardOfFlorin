package me.ghost.map;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class Tile extends RectangleShape implements Drawable {
    private final ArrayList<Drawable> toDraw;

    public Tile(Vector2f position, Texture setTexture) {
        super(position);
        toDraw = new ArrayList<>();
        this.setPosition(position.x, position.y);
        this.setTexture(setTexture);
        this.setSize(new Vector2f(16, 16));
        toDraw.add(this);
    }

}
