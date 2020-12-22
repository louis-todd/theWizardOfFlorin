package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

public class Game {
    private final RenderWindow window;
    private Sprite wizard;
    private boolean RIGHT = false;
    private boolean LEFT = false;
    private boolean UP = false;
    private boolean DOWN = false;
    private boolean SPACE = false;
    private Text dialogueWindowText;

    /**
     * Constructor for the game class
     */
    public Game() {
        //Create the window
        window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");

        Texture wizardText = new Texture();

        //Load texture
        try {
            wizardText.loadFromFile(Paths.get("resources/smileyface.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Create wizard sprite with wizardText texture
        wizard = new Sprite(wizardText);

        //Set its origin to its center and put it at the center of the screen
        wizard.setOrigin(Vector2f.div(new Vector2f(wizardText.getSize()), 2));
        wizard.setScale(0.05f, 0.05f);
        wizard.setPosition(320, 240);

        //Limit the framerate
        window.setFramerateLimit(120);

    }

    /**
     * Runs the window including inputs and updating the window
     */
    public void run() {
        while (window.isOpen()) {
            handleEvents();

            moveWizard(wizard);

            isDialogue(wizard);

            updateWindow();
        }
    }

    /**
     * Handles the input events
     */
    private void handleEvents(){
        //Handle events
        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case KEY_RELEASED:
                    KeyEvent keyRelease = event.asKeyEvent();
                    if (keyRelease.key == Keyboard.Key.RIGHT || keyRelease.key == Keyboard.Key.LEFT || keyRelease.key == Keyboard.Key.UP || keyRelease.key == Keyboard.Key.DOWN || keyRelease.key == Keyboard.Key.SPACE) {
                        handleArrowPress(keyRelease, false);
                    }
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if (keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN || keyEvent.key == Keyboard.Key.SPACE) {
                        handleArrowPress(keyEvent, true);
                    }
                    break;
            }
        }
    }

    /**
     * Moves the wizard if the direction flags are true
     * @param wizard wizard sprite
     */
    private void moveWizard(Sprite wizard) {
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

    private void isDialogue(Sprite wizard) {
        //Create a font
        Font simpleFont = new Font();

        //Set the font to the downloaded file
        try {
            simpleFont.loadFromFile(Paths.get("resources/Roboto-Regular.ttf"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set text, size, and position
        dialogueWindowText = new Text("firstText", simpleFont, 20){{
            this.setPosition(320, 240);
        }};


        //Display text when space is pressed
        if (SPACE){
            System.out.println("I should be showin text");
            //draw text
        }
    }

    /**
     * Updates the window
     */
    private void updateWindow(){
        window.clear(Color.RED);

        window.draw(wizard);

        window.draw(dialogueWindowText);

        window.display();
    }

    /**
     * Sets direction flags to true if direction key is pressed
     *
     * @param movementKey direction key
     * @param pressed boolean - whether the key is pressed or not
     */
    private void handleArrowPress(KeyEvent movementKey, boolean pressed) {
        switch (movementKey.key) {
            case RIGHT:
                RIGHT = pressed;
                break;
            case LEFT:
                LEFT = pressed;
                break;
            case UP:
                UP = pressed;
                break;
            case DOWN:
                DOWN = pressed;
                break;
            case SPACE:
                SPACE = pressed;
                break;
        }
    }
}

