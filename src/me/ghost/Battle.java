package me.ghost;


import java.util.ArrayList;
import java.util.Map;

import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;


public class Battle {
    Texture texture = new Texture();


    public void battleStartUpdate() {
        window.clear(Color.RED);


        isDialogue();
        window.display();
    }

    public void loadImage(){



        try {
            //Load the image.
            Image image = new Image();
            image.loadFromFile(Paths.get("undertaleBattle1.png"));

            //Apply the color mask
            image.createMaskFromColor(Color.BLUE);

            //Load the masked image into the texture
            texture.loadFromImage(image);
        } catch(IOException|TextureCreationException ex) {
            System.err.println("Something went wrong:");
            ex.printStackTrace();
        }


    }


}
