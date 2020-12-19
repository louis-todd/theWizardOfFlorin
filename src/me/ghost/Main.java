package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    static boolean RIGHT = false;
    static boolean LEFT = false;
    static boolean UP = false;
    static boolean DOWN = false;

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
        window.setFramerateLimit(120);

        //me.ghost.Main loop
        while (window.isOpen()) {

            window.clear(Color.RED);

            moveWizard(wizard);

            window.draw(wizard);

            window.display();

            //Handle events
            for (Event event : window.pollEvents()) {
                switch (event.type) {
                    case CLOSED:
                        window.close();
                        break;

                    case KEY_RELEASED:
                        KeyEvent keyRelease = event.asKeyEvent();
                        if (keyRelease.key == Keyboard.Key.RIGHT || keyRelease.key == Keyboard.Key.LEFT || keyRelease.key == Keyboard.Key.UP || keyRelease.key == Keyboard.Key.DOWN) {
                            arrowReleased(keyRelease);
                        }
                        break;
                    case KEY_PRESSED:
                        KeyEvent keyEvent = event.asKeyEvent();
                        if (keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN) {
                            arrowPressed(keyEvent);
                        }
                        break;
                }
            }
        }
    }

    public static void moveWizard(Sprite wizard){
        if (RIGHT) {
            wizard.move(1, 0);
        }
        if (LEFT) {
            wizard.move(-1, 0);
        }
        if (UP) {
            wizard.move(0, -1);
        }
        if (DOWN) {
            wizard.move(0, 1);
        }
    }

    public static void arrowPressed(KeyEvent movementKey) {
        if (movementKey.key == Keyboard.Key.RIGHT) {
            RIGHT = true;
        }
        if (movementKey.key == Keyboard.Key.LEFT) {
            LEFT = true;
        }
        if (movementKey.key == Keyboard.Key.UP) {
            UP = true;
        }
        if (movementKey.key == Keyboard.Key.DOWN) {
            DOWN = true;
        }
    }

    public static void arrowReleased(KeyEvent movementKey) {
        if (movementKey.key == Keyboard.Key.RIGHT) {
            RIGHT = false;
        }
        if (movementKey.key == Keyboard.Key.LEFT) {
            LEFT = false;
        }
        if (movementKey.key == Keyboard.Key.UP) {
            UP = false;
        }
        if (movementKey.key == Keyboard.Key.DOWN) {
            DOWN = false;
        }
    }
}

