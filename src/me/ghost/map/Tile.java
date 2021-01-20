package me.ghost.map;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Tile extends RectangleShape {
    Texture tileTexture;

    public Tile(Vector2f vector2f, Texture setTexture) {
        super(vector2f);
        this.tileTexture = setTexture;
    }
}
