package me.ghost;

import me.ghost.battle.BattleWindow;
import me.ghost.battle.dodge.DodgeGame;
import me.ghost.PauseMenu;
import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mechanics {

    private final Map<String, Boolean> keyPresses;
    private final RenderWindow window;
    private final Dialogue interaction;

    private Npc interactingNPC;
    private Item interactingItem;

    private boolean battleScreenOpen = false;
    private boolean pauseMenuOpen = false;
    private DodgeGame dodgeGame;
    private PauseMenu pauseMenu;
    private BattleWindow battleWindow;

    private ArrayList<Npc> NPCs;
    private ArrayList<Item> ITEMS;

    private int overarchingLives = 3;

    public Mechanics(Map<String, Boolean> keyPresses, RenderWindow window, ArrayList<Npc> NPCs, ArrayList<Item> ITEMS, Dialogue interaction, BattleWindow battleWindow) {
        this.keyPresses = keyPresses;
        this.window = window;
        this.interaction = interaction;
        this.NPCs = NPCs;
        this.ITEMS = ITEMS;
        this.battleWindow = battleWindow;

        this.initKeyPressesMap();
    }

    public void initKeyPressesMap() {
        this.keyPresses.put("RIGHT", false);
        this.keyPresses.put("LEFT", false);
        this.keyPresses.put("UP", false);
        this.keyPresses.put("DOWN", false);
        this.keyPresses.put("SPACE", false);
        this.keyPresses.put("B", false);
        this.keyPresses.put("ESCAPE", false);
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
            case ESCAPE:
                keyPresses.put("ESCAPE", pressed);
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

                    if (this.pauseMenuOpen) {
                        this.pauseMenu.handleInput(keyRelease);
                    }
                    break;
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if ((keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN)) {
                        handleKeyPress(keyEvent, true);
                        if(this.dodgeGame!=null){
                            this.dodgeGame.handleInput(keyEvent);
                        }
                        break;
                    }

                    if (this.pauseMenuOpen) {
                        this.pauseMenu.handleInput(keyEvent);
                    }

                    if (keyEvent.key == Keyboard.Key.ESCAPE) {
                        // If B has already been pressed
                        if (keyPresses.get("ESCAPE")) {
                            keyPresses.put("ESCAPE", false);
                            pauseMenuOpen = false;
                        }
                        // if first B, set to display battle window
                        else {
                            pauseMenu = new PauseMenu(true);
                            handleKeyPress(keyEvent, true);
                        }
                    }

                    // Calculate which NPC is being interacted with
                    interactingNPC = null;
                    for (Npc npc : NPCs) {
                        if (wizard.dialogueAreaCollide(npc) && npc.shouldDraw()) {
                            interactingNPC = npc;
                        }
                    }

                    // Calculate which NPC is being interacted with
                    interactingItem = null;
                    for (Item item : ITEMS) {
                        if (wizard.dialogueAreaCollide(item) && !(item.isFound()) && item.availableToCollect()) {
                            interactingItem = item;
                        }
                    }

                    if (interactingNPC == null && interactingItem == null) {
                        break;
                    }

                    if (keyEvent.key == Keyboard.Key.SPACE && !pauseMenuOpen) {
                        // If space has already been pressed
                        if (keyPresses.get("SPACE")) {
                            if (interactingNPC != null & interactingItem == null && interactingNPC.shouldDraw() && !battleScreenOpen) {
                                // if still tiles to step through do
                                if (interactingNPC.getCurrentIndex() < interactingNPC.getScript().size()) {
                                    interaction.setTextContent(String.valueOf(interactingNPC.getScript().get(interactingNPC.getCurrentIndex())));
                                    interactingNPC.incrementCurrentIndex();
                                }
                                // if at tile limit, close
                                else {
                                    keyPresses.put("SPACE", false);
                                    interactingNPC.resetScript();
                                }
                            } else if (battleScreenOpen) {
                                //Step through battle dialogue
                                if (interactingNPC.getCurrentBattleIndex() < interactingNPC.getBattleScript().size()) {
                                    if (!dodgeGame.isFinishedDialogue()) {
                                        dodgeGame.setTextContent(String.valueOf(interactingNPC.getBattleScript().get(interactingNPC.getCurrentBattleIndex())));
                                        interactingNPC.incrementCurrentBattleIndex();
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
                            if (!battleScreenOpen) {
                                if (interactingNPC != null && interactingItem == null && interactingNPC.shouldDraw()) {
                                    interaction.setCharacterName(interactingNPC.getName());
                                    interaction.setTextContent(interactingNPC.getScript().get(0));
                                    handleKeyPress(keyEvent, true);
                                }
                                if (interactingItem != null) {
                                    interactingItem.setAsFound(true);
                                }
                            } else {
                                if (!dodgeGame.isFinishedDialogue()) {
                                    dodgeGame.setTextContent(interactingNPC.getScript().get(0));
                                    // dodgeGame.setCharacterName(interactingNPC.getName());
                                    handleKeyPress(keyEvent, true);
                                }
                            }
                        }
                        break;
                    }
                    if (keyEvent.key == Keyboard.Key.B && !pauseMenuOpen) {
                        // If B has already been pressed
                        // if (keyPresses.get("B")) {
                        //     keyPresses.put("B", false);
                        //     battleScreenOpen = false;
                        // }
                        // if first B, set to display battle window
                        // else {
                        if (!interactingNPC.hasCompletedBattle()) {
                            dodgeGame = new DodgeGame(interactingNPC, "EASY", this);
                            handleKeyPress(keyEvent, true);
                        }
                        // }
                    }
                case MOUSE_BUTTON_PRESSED:
                    if(!battleScreenOpen) {
                        if (keyPresses.get("SPACE") && interactingNPC != null && interactingItem == null && interactingNPC.shouldDraw() && !pauseMenuOpen) {
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
                    } else {
                        if(dodgeGame != null){
                            dodgeGame.setMouseButtonclicked(true);
                            if(event.asMouseButtonEvent()!=null){
                                dodgeGame.setMousePosition(new Vector2f(event.asMouseButtonEvent().position));
                            }
                            if(dodgeGame.attemptedToClose()){
                                keyPresses.put("B", false);
                                battleScreenOpen = false;
                            }
                        }
                    }
                    if(pauseMenu!=null){
                        pauseMenu.setMouseButtonclicked(true);
                        if(event.asMouseButtonEvent()!=null){
                            pauseMenu.setMousePosition(new Vector2f(event.asMouseButtonEvent().position));
                        }
                    }
                    if(dodgeGame!=null){
                        if(dodgeGame.isEndScreenOpen()){
                            dodgeGame.getLostScreen().setMouseButtonclicked(true);
                            if(event.asMouseButtonEvent()!=null){
                                System.out.println("QUIT SCREEN PRESSED");
                                dodgeGame.getLostScreen().setMousePosition(new Vector2f(event.asMouseButtonEvent().position));
                            }
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
            if(dodgeGame!=null){
                if(dodgeGame.isFinishedDialogue()){
                    interaction.draw(window, null);
                }
            }
            else{
                interaction.draw(window, null);
            }
        }
        if ((keyPresses.get("B")) && !this.pauseMenuOpen) {
            if(dodgeGame!=null){
                dodgeGame.draw(this.window);
                battleScreenOpen = true;
            }
        }
        if ((keyPresses.get("ESCAPE"))) {
            pauseMenu.draw(this.window);
            pauseMenuOpen = true;
        }
    }

    public boolean isBattleScreenOpen() {
        return battleScreenOpen;
    }

    public boolean isPauseMenuOpen() {
        return pauseMenuOpen;
    }

    public boolean hasPlayerQuit() {
        if(pauseMenu != null){
            return pauseMenu.playerHasQuit();
        }
        if(dodgeGame != null) {
            if (dodgeGame.isEndScreenOpen()) {
                return dodgeGame.getLostScreen().playerHasQuit();
            }
        }
        return false;
    }

    public int getOverarchingLives() {
        return overarchingLives;
    }

    public void setOverarchingLives(int overarchingLives) {
        this.overarchingLives = overarchingLives;
    }

    public void setPauseMenuOpen(boolean pauseMenuOpen) {
        this.pauseMenuOpen = pauseMenuOpen;
    }
}
