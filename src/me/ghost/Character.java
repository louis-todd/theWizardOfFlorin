package me.ghost;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Character extends Sprite {

    private Texture spriteTexture;

    public Character (Texture setSpriteTexture, float xPosition, float yPosition, float scale){
        this.setTexture(setSpriteTexture);
        this.setOrigin(Vector2f.div(new Vector2f(setSpriteTexture.getSize()), 2));
        this.setPosition(xPosition, yPosition);
        this.setScale(scale, scale);


    }


}
