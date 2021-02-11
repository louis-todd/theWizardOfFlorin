package me.ghost.movement;

public class sprite {

    // ... (create window and load )

    //Create a sprite and make it use the logo texture
    Sprite sprite_left = new Sprite(sprite_left);

//Set its origin to its center and put it at the center of the screen
sprite_left.setOrigin(Vector2f.div(new Vector2f(sprite_left.getSize()), 2));
sprite_left.setPosition(320, 240);

    //Create a frame clock for rotation
    Clock frameClock = new Clock();

//Main loop
while(window.isOpen()) {
        window.clear();
        window.draw(sprite_left);
        window.display();

        // ... (event handling)

        //Get the frame time (dt stands for "delta time" - time difference since last frame)
        float dt = frameClock.restart().asSeconds();

        //Rotate the sprite 45 degrees clock-wise per second
        sprite_left.rotate(dt * 45);
    }
}
