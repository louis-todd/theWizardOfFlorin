package me.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsfml.graphics.*;
// import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
// import java.io.IOException;
// import java.nio.file.Paths;

public class Game {
    private final RenderWindow window;

    private Character wizard;
    private Character npc;
    private boolean somethingPressed = false;
    private Dialogue interaction;
    private ArrayList<Drawable> toDraw;
    private Map<String, Boolean> keyPresses;

    /**
     * Constructor for the game class
     */
    public Game() {
        //Add the keypresses to a hashmap with their respective direction
        keyPresses = new HashMap<String, Boolean>() {
            private static final long serialVersionUID = 1L;
            {
            put("RIGHT", false);
            put("LEFT", false);
            put("UP", false);
            put("DOWN", false);
            put("FIRSTSPACE", false);
        }};

        //Create the window and set window name to: 'Welcome Wizards'
        window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");
        toDraw = new ArrayList<Drawable>();

        wizard = new Character(320, 240, 0.05f, "resources/smileyface.png");
        toDraw.add(wizard);

        npc = new Character(250, 300, 0.05f, "resources/smileyface.png");
        toDraw.add(npc);

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
                    if ((keyRelease.key == Keyboard.Key.RIGHT || keyRelease.key == Keyboard.Key.LEFT || keyRelease.key == Keyboard.Key.UP || keyRelease.key == Keyboard.Key.DOWN)) {
                        handleKeyPress(keyRelease, false);
                    }
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if ((keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN) && !(keyPresses.get("FIRSTSPACE"))) {
                        handleKeyPress(keyEvent, true);
                    }

                    //Special case for when space is pressed
                    if (keyEvent.key == Keyboard.Key.SPACE) {
                        somethingPressed=false;
                        //Check if anything else is being pressed
                        for (Map.Entry<String, Boolean> entry : keyPresses.entrySet()) {
                            if(entry.getValue() && entry.getKey()!="SPACE"){
                                somethingPressed=true;
                            }
                        }
                        //only if something else isn't being pressed, handle space
                        if(!somethingPressed || keyPresses.get("FIRSTSPACE")){
                            handleKeyPress(keyEvent, !(keyPresses.get("FIRSTSPACE")));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Moves the wizard if the direction flags are true
     * @param wizard wizard sprite
     */
    private void moveWizard(Sprite wizard) {
        if ((keyPresses.get("RIGHT"))) {
            wizard.move(1, 0);
        }
        if ((keyPresses.get("LEFT"))) {
            wizard.move(-1, 0);
        }
        if ((keyPresses.get("UP"))) {
            wizard.move(0, -1);
        }
        if ((keyPresses.get("DOWN"))) {
            wizard.move(0, 1);
        }
    }

    private void isDialogue() {

        //If its the first time space is pressed, set the text
        if((keyPresses.get("FIRSTSPACE"))){
            interaction = new Dialogue("resources/Roboto-Regular.ttf", "resources/DialogueBoard.png", "Name Placeholder", "Content Placeholder");
            interaction.draw(window, null);
        }
    }

    /**
     * Updates the window
     */
    private void updateWindow(){
        window.clear(Color.RED);

        for(Drawable item : toDraw){
            window.draw(item);
        }
        isDialogue();
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
                keyPresses.put("RIGHT", pressed);
                // RIGHT = pressed;
                break;
            case LEFT:
                keyPresses.put("LEFT", pressed);
                // LEFT = pressed;
                break;
            case UP:
                keyPresses.put("UP", pressed);
                // UP = pressed;
                break;
            case DOWN:
                keyPresses.put("DOWN", pressed);
                // DOWN = pressed;
                break;
            case SPACE:
                keyPresses.put("FIRSTSPACE", pressed);
                // FIRSTSPACE = pressed;
                break;
            default:
                break;
        }
    }
}

