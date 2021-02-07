package me.ghost;

import me.ghost.Characters.MoveableCharacter;
import me.ghost.Characters.Npc;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;
import java.util.Map;

public class Mechanics {

    private Map<String, Boolean> keyPresses;
    private RenderWindow window;
    private Dialogue interaction;
    private BattleWindow battleWindow;
    private ArrayList<Npc> NPCs = new ArrayList<>();
    private Npc interactingNPC;
    private boolean battleScreenOpen = false;

    public Mechanics(Map<String, Boolean> keyPresses, RenderWindow window, ArrayList<Npc> NPCs, Dialogue interaction, BattleWindow battleWindow) {
        this.keyPresses = keyPresses;
        this.window = window;
        this.interaction = interaction;
        this.NPCs = NPCs;
        this.battleWindow = battleWindow;
    }

    public void initKeyPressesMap() {
        this.keyPresses.put("RIGHT", false);
        this.keyPresses.put("LEFT", false);
        this.keyPresses.put("UP", false);
        this.keyPresses.put("DOWN", false);
        this.keyPresses.put("SPACE", false);
        this.keyPresses.put("B", false);
    }

    /**
     * Sets direction flags to true if direction key is pressed
     *
     * @param movementKey direction key
     * @param pressed     boolean - whether the key is pressed or not
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
                keyPresses.put("SPACE", pressed);
                break;
            case B:
                keyPresses.put("B", pressed);
                break;
            default:
                break;
        }
    }

    /**
     * Handles the input events
     */
    public void handleEvents(MoveableCharacter wizard) {
        // Handle events
        for (Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case KEY_RELEASED:
                    KeyEvent keyRelease = event.asKeyEvent();
                    if ((keyRelease.key == Keyboard.Key.RIGHT || keyRelease.key == Keyboard.Key.LEFT
                            || keyRelease.key == Keyboard.Key.UP || keyRelease.key == Keyboard.Key.DOWN)) {
                        handleKeyPress(keyRelease, false);
                    }
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if ((keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT
                            || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN)) {
                        handleKeyPress(keyEvent, true);
                        break;
                    }

                    // Calculate which NPC is being interacted with
                    interactingNPC = null;
                    for (Npc npc : NPCs) {
                        if (wizard.dialogueAreaCollide(npc)) {
                            interactingNPC = npc;
                        }
                    }
                    if (interactingNPC == null) {
                        break;
                    }

                    if (keyEvent.key == Keyboard.Key.SPACE) {
                        // If space has already been pressed
                        if (keyPresses.get("SPACE")) {
                            // if still tiles to step through do
                            if (interactingNPC.getCurrentIndex() < interactingNPC.getScript().size()) {
                                interaction.setTextContent(String
                                        .valueOf(interactingNPC.getScript().get(interactingNPC.getCurrentIndex())));
                                interactingNPC.incrementCurrentIndex();
                                ;
                            }
                            // if at tile limit, close
                            else {
                                keyPresses.put("SPACE", false);
                                interactingNPC.resetScript();
                                ;
                            }
                        }
                        // if first space, set to display first tile
                        else {
                            interaction.setTextContent(interactingNPC.getScript().get(0));
                            handleKeyPress(keyEvent, true);
                        }
                    }
                    if (keyEvent.key == Keyboard.Key.B) {
                        // If B has already been pressed
                        if (keyPresses.get("B")) {
                            keyPresses.put("B", false);
                            battleScreenOpen = false;
                        }
                        // if first B, set to display battle window
                        else {
                            handleKeyPress(keyEvent, true);
                        }
                    }
                    break;
                case MOUSE_BUTTON_PRESSED:
                    if (keyPresses.get("SPACE")) {
                        // if still tiles left to show, step through them
                        if (interactingNPC.getCurrentIndex() < interactingNPC.getScript().size()) {
                            interaction.setTextContent(
                                    String.valueOf(interactingNPC.getScript().get(interactingNPC.getCurrentIndex())));
                            interactingNPC.incrementCurrentIndex();
                        }
                        // if have read all tiles, act as if space has been clicked to close the
                        // dialogue box
                        else if (interactingNPC.getCurrentIndex() >= interactingNPC.getScript().size()) {
                            keyPresses.put("SPACE", !(keyPresses.get("SPACE")));
                            interactingNPC.resetScript();
                            ;
                        }
                    }
                default:
                    break;
            }
        }
    }

    public void isDialogue() {
        // If its the first time space is pressed, set the text
        if ((keyPresses.get("SPACE"))) {
            interaction.draw(window, null);
        }
        if ((keyPresses.get("B"))) {
            battleWindow.draw(window, null);
            battleScreenOpen = true;
        }
    }

    public boolean isBattleScreenOpen() {
        return battleScreenOpen;
    }
}
