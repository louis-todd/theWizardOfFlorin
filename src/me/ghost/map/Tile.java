package me.ghost.map;

import me.ghost.data.TileLoader;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class Tile extends RectangleShape {

    private boolean collideable;
    private int tileNumber;

    /**
     * Soul constructor for the tile class which sets this tiles metadata.
     */
    public Tile(Vector2f position, Texture setTexture, int setTileNumber, TileLoader tileLoader) {
        super(position);
        this.setPosition(position.x, position.y);
        this.setTexture(setTexture);
        this.setSize(new Vector2f(16, 16));
        this.tileNumber = setTileNumber;
        this.setCollideable(tileLoader);
    }

    /** 
     * @param tileLoader
     */
    private void setCollideable(TileLoader tileLoader){
        collideable = tileLoader.getCollidableTiles().contains(tileNumber);
    }

    /** 
     * @return boolean
     */
    public boolean isCollideable() {
        return collideable;
    }
}
