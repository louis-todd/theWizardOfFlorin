package me.ghost.map;

import me.ghost.data.TileLoader;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class Tile extends RectangleShape {

    private boolean collideable;
    private int tileNumber;
    public Tile(Vector2f position, Texture setTexture, int setTileNumber, TileLoader tileLoader) {
        super(position);
        this.setPosition(position.x, position.y);
        this.setTexture(setTexture);
        this.setSize(new Vector2f(16, 16));
        this.tileNumber = setTileNumber;
        this.setCollideable(tileLoader);
    }

    private void setCollideable(TileLoader tileLoader){
        collideable = tileLoader.getCollidableTiles().contains(tileNumber);
    }

    public boolean isCollideable() {
        return collideable;
    }
}
