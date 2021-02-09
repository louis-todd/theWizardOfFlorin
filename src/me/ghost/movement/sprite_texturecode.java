package me.ghost.movement;

public class sprite_texturecode {

    Texture sprite_left = new Texture();

    try {
        //Try to load the texture from file
        sprite_left.loadFromFile(Paths.get("sprite_left.png"));

        //Texture was loaded successfully - retrieve and print size
        Vector2i size = sprite_left.getSize();
        System.out.println("The texture is " + size.x + "x" + size.y);
    } catch(IOException ex) {
        // if wrong
        ex.printStackTrace();
    }

    }
