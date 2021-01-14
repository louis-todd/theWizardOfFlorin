package me.ghost.Characters;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

public abstract class Character extends Sprite {

    private Texture spriteTexture;


    public Character(float xPosition, float yPosition, String imagePath) {

        //Set picture for the sprite
        Texture spriteTexture = new Texture();
        try {
            spriteTexture.loadFromFile(Paths.get(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.setTexture(spriteTexture);
        this.setPosition(xPosition, yPosition);

    }


}
