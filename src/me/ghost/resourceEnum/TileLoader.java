package me.ghost.resourceEnum;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

public class TileLoader {
    private final Texture[] tileTexture;
    public TileLoader() {
        tileTexture = new Texture[3420];

        for(int i = 0; i < 3420; i++){
            tileTexture[i] = loadTexture(getTilePath(i));
        }
    }

    private Texture loadTexture(String path){
        Texture texture = new Texture();
        try {
            texture.loadFromStream(Game.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return texture;
    }


    public String getTilePath(int index){
        return("tiles/tile"+index+".png32");
    }

    public Texture getTileTexture(int index){
        return tileTexture[index];
    }
}
