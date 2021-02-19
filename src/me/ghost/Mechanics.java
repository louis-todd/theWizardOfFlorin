package me.ghost;

import me.ghost.battle.dodge.DodgeGame;
import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.List;
import java.util.Map;

public class Mechanics {

    private final Map<String, Boolean> keyPresses;
    private final RenderWindow window;
    private final Dialogue interaction;
    private final List<Npc> NPCs;

    private Npc interactingNPC;
    private boolean battleScreenOpen = false;
    private DodgeGame dodgeGame;


    public Mechanics(Map<String, Boolean> keyPresses, RenderWindow window, List<Npc> NPCs, Dialogue interaction) {
        this.keyPresses = keyPresses;
        this.window = window;
        this.interaction = interaction;
        this.NPCs = NPCs;
        this.initKeyPressesMap();
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

                    if (this.battleScreenOpen) {
                        this.dodgeGame.handleInput(keyRelease);
                    }
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if ((keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT
                            || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN)) {
                        handleKeyPress(keyEvent, true);
                        break;
                    }

                    if (this.battleScreenOpen) {
                        this.dodgeGame.handleInput(keyEvent);
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
                            if (!battleScreenOpen){
                                if (interactingNPC.getCurrentIndex() < interactingNPC.getScript().size()) {
                                    interaction.setTextContent(String
                                            .valueOf(interactingNPC.getScript().get(interactingNPC.getCurrentIndex())));
                                    interactingNPC.incrementCurrentIndex();
                                }
                                // if at tile limit, close
                                else {
                                    keyPresses.put("SPACE", false);
                                    interactingNPC.resetScript();
                                }
                            }
                            else{
                                //Step through battle dialogue
                                if (interactingNPC.getCurrentBattleIndex() < interactingNPC.getBattleScript().size()) {
                                    if(!dodgeGame.isFinishedDialogue()){
                                        dodgeGame.setTextContent(String.valueOf(interactingNPC.getBattleScript().get(interactingNPC.getCurrentBattleIndex())));
                                        interactingNPC.incrementCurrentBattleIndex();
                                        System.out.println(interactingNPC.getCurrentBattleIndex());
                                        dodgeGame.setFinishedDialogue(false);
                                    }
                                }
                                // if at tile limit, close
                                else {
                                    keyPresses.put("SPACE", false);
                                    dodgeGame.setFinishedDialogue(true);
                                    interactingNPC.setHasCompletedBattle(true);
                                    dodgeGame.setTextContent("");
                                }
                            }
                        }
                        // if first space, set to display first tile
                        else {
                            if(!battleScreenOpen){
                                interaction.setTextContent(interactingNPC.getScript().get(0));
                                interaction.setCharacterName(interactingNPC.getName());
                                handleKeyPress(keyEvent, true);
                            }
                            else{
                                if(!dodgeGame.isFinishedDialogue()){
                                    dodgeGame.setTextContent(interactingNPC.getScript().get(0));
                                    // dodgeGame.setCharacterName(interactingNPC.getName());
                                    handleKeyPress(keyEvent, true);
                                }
                            }
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
                            if(!interactingNPC.hasCompletedBattle()){
                                dodgeGame = new DodgeGame(interactingNPC, "HARD", this);
                                handleKeyPress(keyEvent, true);
                            }
                        }
                    }
                    break;
                case MOUSE_BUTTON_PRESSED:
                    if (keyPresses.get("SPACE")) {
                        // if still tiles left to show, step through them
                        if (interactingNPC.getCurrentIndex() < interactingNPC.getScript().size()) {
                            interaction.setTextContent(String.valueOf(interactingNPC.getScript().get(interactingNPC.getCurrentIndex())));
                            interactingNPC.incrementCurrentIndex();
                        }
                        // if have read all tiles, act as if space has been clicked to close the
                        // dialogue box
                        else if (interactingNPC.getCurrentIndex() >= interactingNPC.getScript().size()) {
                            keyPresses.put("SPACE", !(keyPresses.get("SPACE")));
                            interactingNPC.resetScript();
                        }
                    }
                default:
                    break;
            }
        }
    }

    public void isDialogue() {
        // If its the first time space is pressed, set the text
        if ((keyPresses.get("SPACE")) && dodgeGame.isFinishedDialogue()) {
            interaction.draw(window, null);
        }
        if ((keyPresses.get("B"))) {
            dodgeGame.draw(this.window);
            battleScreenOpen = true;
        }
    }

    public boolean isBattleScreenOpen() {
        return battleScreenOpen;
    }
}
