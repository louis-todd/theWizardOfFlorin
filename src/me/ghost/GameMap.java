package me.ghost;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class GameMap {
    public GameMap() {

    }

    private void loadMap() {
        File csvFile = new File("resources/map._House.csv");
        if (csvFile.isFile()) {
            String row;
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader("map._House.csv"));
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    int imageNo = Integer.parseInt(data[0]);
                    switch (imageNo){
                        case -1:
                            Texture texture2 = new Texture();

                            try {
                                //Load the image.
                                Image image = new Image();
                                image.loadFromFile(Paths.get("resources/tiles/tile6.png"));

                                //Apply the color mask
                                //image.createMaskFromColor(Color.BLUE);

                                //Load the masked image into the texture
                                texture2.loadFromImage(image);
                            } catch(IOException | TextureCreationException ex) {
                                System.err.println("Something went wrong:");
                                ex.printStackTrace();
                            }
                        default:
                            //draw nothing
                    }
                }
                csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
