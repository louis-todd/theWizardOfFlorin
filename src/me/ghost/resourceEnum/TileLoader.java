package me.ghost.ResourceEnum;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


public class TileLoader {

    public static final ExecutorService THREADS = Executors.newCachedThreadPool();

    private final AtomicBoolean loading = new AtomicBoolean(false);
    private final Texture[] tileTexture = new Texture[3420];

    public TileLoader() {
        THREADS.submit(() -> {
            for (int i = 0; i < 3420; i++) {
                tileTexture[i] = loadTexture(getTilePath(i));
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            this.loading.set(true);
        });
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

    public boolean isLoaded() {
        return this.loading.get();
    }
}
