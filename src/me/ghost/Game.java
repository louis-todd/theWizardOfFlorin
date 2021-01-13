package me.ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.ghost.Characters.MoveableCharacter;
import me.ghost.Characters.Npc;
import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;


public class Game {

    private final RenderWindow window;
    private final MoveableCharacter wizard = new MoveableCharacter(320, 240, "resources/square-16.png");
    private final Npc npc = new Npc(250, 300, "resources/square-16.png");
    private final List<Drawable> toDraw;
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();
    private boolean wizardColliding = false;

    /**
     * Constructor for the game class
     */
    public Game() {
        this.initKeyPressesMap();

        //Create the window and set window name to: 'Welcome Wizards'
        window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");
        toDraw = new ArrayList<>();

        toDraw.add(wizard);
        toDraw.add(npc);
        //Limit the framerate
        window.setFramerateLimit(120);
    }

    private void initKeyPressesMap() {
        this.keyPresses.put("RIGHT", false);
        this.keyPresses.put("LEFT", false);
        this.keyPresses.put("UP", false);
        this.keyPresses.put("DOWN", false);
        this.keyPresses.put("FIRSTSPACE", false);
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
                        boolean somethingPressed = false;
                        //Check if anything else is being pressed
                        for (Map.Entry<String, Boolean> entry : keyPresses.entrySet()) {
                            if (entry.getValue() && !entry.getKey().equals("SPACE")) {
                                somethingPressed = true;
                                break;
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
    private void moveWizard(MoveableCharacter wizard) {
        Sprite npcCollide = null;

        for (Drawable npcs : toDraw) {
            if (wizard.collides((Sprite) npcs) && (!npcs.equals(wizard))) {
                wizardColliding = true;
                npcCollide = npc;
            }
        }
        if (!wizardColliding) {
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
        } else {
           assert npcCollide != null;
            if(npcCollide.getPosition().x> wizard.getPosition().x){
                wizard.move(-0.1f, 0);
            }
            if (npcCollide.getPosition().x < wizard.getPosition().x) {
                wizard.move(0.1f, 0);
            }
             if (npcCollide.getPosition().y > wizard.getPosition().y) {
                 wizard.move(0, -0.1f);
             }
             if (npcCollide.getPosition().y < wizard.getPosition().y) {
                wizard.move(0, 0.1f);
            }
            wizardColliding = false;
        }
    }

    private void isDialogue() {
        //If its the first time space is pressed, set the text
        if((keyPresses.get("FIRSTSPACE"))){
            Dialogue interaction = new Dialogue("resources/Roboto-Regular.ttf", "resources/DialogueBoard.png", "Name Placeholder", "Content Placeholder");
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
                break;
            case LEFT:
                keyPresses.put("LEFT", pressed);
                break;
            case UP:
                keyPresses.put("UP", pressed);
                break;
            case DOWN:
                keyPresses.put("DOWN", pressed);
                break;
            case SPACE:
                keyPresses.put("FIRSTSPACE", pressed);
                break;
            default:
                break;
        }
    }
}

