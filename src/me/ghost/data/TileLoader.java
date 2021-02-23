package me.ghost.data;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class TileLoader {

    public static final ExecutorService THREADS = Executors.newCachedThreadPool();

    private final Texture[] tileTexture = new Texture[3420];
    private final AtomicBoolean loaded = new AtomicBoolean(false);

    public TileLoader() {
        THREADS.submit(() -> {
            for (int i = 0; i < 3420; i++) {
                tileTexture[i] = loadTexture(getTilePath(i));
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                System.out.println("Error in loading tiles");
            }

            this.loaded.set(true);
        });
    }

    private Texture loadTexture(String path){
        Texture texture = new Texture();
        try {
            texture.loadFromStream(Game.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error in loading texture");
        }
        return texture;
    }


    public String getTilePath(int index){
        return("tiles/tile"+index+".png32");
    }

    public Texture getTileTexture(int index){
        return tileTexture[index];
    }

    public boolean isLoaded() {
        return this.loaded.get();
    }
}
