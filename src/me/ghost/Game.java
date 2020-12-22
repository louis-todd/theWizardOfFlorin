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
    private boolean FIRSTSPACE = false;
    private Text dialogueWindowText;
    private Texture tmpTexture;

    /**
     * Constructor for the game class
     */
    public Game() {
        //Create the window
        window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");

        //Create Wizard
        Texture wizardText = new Texture();

        //Load texture
        try {
            wizardText.loadFromFile(Paths.get("resources/smileyface.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Create wizard sprite with wizardText texture
        wizard = new Sprite(wizardText);
        addCharacter(wizard, wizardText);
        //End Wizard

        //Limit the framerate
        window.setFramerateLimit(120);

    }

    /**
     * Set the characters origin to it's centre and put it at the centre of the screen
     * @param characterToAdd The character to add
     * @param resourcePath The texture of the sprite
     */
    public void addCharacter(Sprite characterToAdd, Texture resourcePath){
        characterToAdd.setOrigin(Vector2f.div(new Vector2f(resourcePath.getSize()), 2));
        characterToAdd.setScale(0.05f, 0.05f);
        characterToAdd.setPosition(320, 240);
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
                    if (keyRelease.key == Keyboard.Key.RIGHT || keyRelease.key == Keyboard.Key.LEFT || keyRelease.key == Keyboard.Key.UP || keyRelease.key == Keyboard.Key.DOWN) {
                        handleKeyPress(keyRelease, false);
                    }
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if (keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN) {
                        handleKeyPress(keyEvent, true);
                    }

                    //Special case for when space is pressed
                    if (keyEvent.key == Keyboard.Key.SPACE) {
                        handleKeyPress(keyEvent, !FIRSTSPACE);
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

        //If its the first time space is pressed, draw the text
        if(FIRSTSPACE){
            //Create a font
            Font simpleFont = new Font();

            //Set the font to the downloaded file
            try {
                simpleFont.loadFromFile(Paths.get("resources/Roboto-Regular.ttf"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Set text, size, and position
            dialogueWindowText = new Text("Welcome to the game!", simpleFont, 20){{
                this.setPosition(20, 400);
            }};
        }

    }

    /**
     * Updates the window
     */
    private void updateWindow(){
        window.clear(Color.RED);

        window.draw(wizard);

        //Only draw if it is the first space
        if(FIRSTSPACE) {
            window.draw(dialogueWindowText);
        }

        window.display();
    }

    /**
     * Sets direction flags to true if direction key is pressed
     *
     * @param movementKey direction key
     * @param pressed boolean - whether the key is pressed or not
     */
    private void handleKeyPress(KeyEvent movementKey, boolean pressed) {
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
                FIRSTSPACE = pressed;
                break;
        }
    }
}

