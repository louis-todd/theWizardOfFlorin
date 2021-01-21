package me.ghost.map;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class Tile extends RectangleShape implements Drawable {
    Texture tileTexture;

    public Tile(Vector2f vector2f, Texture setTexture) {
        super(vector2f);
        this.tileTexture = setTexture;
    }
/*
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
            renderTarget.draw(this);
    }

 */
}
