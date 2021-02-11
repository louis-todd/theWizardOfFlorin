package me.ghost.movement;

public class frame {

    Texture sprite_left = new Texture();
try {
        sprite_left.loadFromFile(Paths.get("sprite_left.png"));
    } catch(IOException ex) {
        ex.printStackTrace();
    }

    //Create the  sprite and make it show the first frame only
    Sprite movement = new Sprite(movementLeft);
movement.setTextureRect(new IntRect(0, 0, 64, 64));

    //Keep a frame counter that tells us what frame we are currently displaying (we start counting at zero)
    int frame = 0;

    //Create a clock for an animation timer
    Clock animClock = new Clock();

//Main loop
while(window.isOpen()) {
        // ... (drawing and displaying)
        // ... (event handling)

        //Change the frame every 50 mill
        if(animClock.getElapsedTime.asMilliseconds() >= 50) {
            //Restart the clock
            animClock.restart();

            //Increase the frame counter by one
            frame++;

            //The animation has 32 frames, when we surpass that, repeat
            if(frame > 31) frame = 0;

            //Calculate the position of the new frame and set it on the sprite
            int frameRow = frame / 8;
            int frameCol = frame % 8;
            movement.setTextureRect(new IntRect(frameCol * 64, frameRow * 64, 64, 64));
        }
    }

}
