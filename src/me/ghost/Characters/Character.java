package me.ghost.Characters;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

public abstract class Character extends Sprite {

    private Texture spriteTexture;


    public Character(float xPosition, float yPosition, Texture characterTexture) {

        this.setTexture(characterTexture);
        this.setPosition(xPosition, yPosition);

    }


}
