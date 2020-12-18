package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        //Create the window
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(640, 480), "Hello JSFML!");

        Texture wizardText = new Texture();

        //Load texture
        try {
            wizardText.loadFromFile(Paths.get("resources/smileyface.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Sprite wizard = new Sprite(wizardText);

        //Set its origin to its center and put it at the center of the screen
        wizard.setOrigin(Vector2f.div(new Vector2f(wizardText.getSize()), 2));
        wizard.setScale(0.05f, 0.05f);
        wizard.setPosition(320, 240);


        //Limit the framerate
        window.setFramerateLimit(30);

        //me.ghost.Main loop
        while (window.isOpen()) {

            window.clear(Color.RED);

            window.draw(wizard);

            window.display();

            //Handle events
            for (Event event : window.pollEvents()) {
                switch (event.type) {
                    case CLOSED:
                        window.close();
                        break;
                    case KEY_PRESSED:
                        KeyEvent keyEvent = event.asKeyEvent();
                        if (keyEvent.key == Keyboard.Key.RIGHT) {
                            wizard.move(2, 0);
                        } else if (keyEvent.key == Keyboard.Key.LEFT) {
                            wizard.move(-2, 0);
                        } else if (keyEvent.key == Keyboard.Key.UP) {
                            wizard.move(0, -2);
                        } else if (keyEvent.key == Keyboard.Key.DOWN) {
                            wizard.move(0, 2);
                        }
                }
            }
        }
    }
}
