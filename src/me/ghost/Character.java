package me.ghost;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

public class Character extends Sprite {

    private Texture spriteTexture;


    public Character(float xPosition, float yPosition, String imagePath) {

        //Set picture for the sprite
        spriteTexture = new Texture();
        try {
            spriteTexture.loadFromFile(Paths.get(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.setTexture(spriteTexture);
        this.setPosition(xPosition, yPosition);

    }

    public boolean collides(Sprite npc) {
        return this.getGlobalBounds().intersection(npc.getGlobalBounds()) != null;
    }

    public void move(){

    }


}
