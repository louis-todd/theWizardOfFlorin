package me.ghost;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Character extends Sprite {

    private Texture spriteTexture;

    public Character (float xPosition, float yPosition, float scale, String imagePath){

        //Set picture for the sprite
        spriteTexture = new Texture();
        try {
            spriteTexture.loadFromFile(Paths.get(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.setTexture(spriteTexture);
        this.setOrigin(Vector2f.div(new Vector2f(spriteTexture.getSize()), 2));
        this.setPosition(xPosition, yPosition);
        this.setScale(scale, scale);

    }


}
