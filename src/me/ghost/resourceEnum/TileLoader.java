package me.ghost.resourceEnum;

import java.io.File;
import java.util.Objects;


public class TileLoader {
    private final File[] tileTexture;
    public TileLoader() {
        File folder = new File("resources/tiles");
        tileTexture = new File[3421];
        loadTextureToArray(folder);
        for(int i = 0; i <= 3420; i++){
            System.out.println(tileTexture[i]);
        }

    }

    private void loadTextureToArray(final File folder){
      try {
          int i = 0;
          for (File f : Objects.requireNonNull(folder.listFiles())) {
              if(f.isDirectory()){
                  loadTextureToArray(f);
              } else {
                  tileTexture[i] = f;
              }
              i++;
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }


}
