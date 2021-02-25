package me.ghost;

import me.ghost.battle.dodge.DodgeGame;
import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;
import java.util.Map;

/**
 * The game loop was abstracted into the Game class, and the remaining state handling was moved into this class.
 * Mechanics is responsible for state handling, and in particular handling user input.
 * States handled include: whether the player has started a battle, paused the game or quit the game.
 */

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

    private ArrayList<Npc> NPCs;
    private ArrayList<Item> ITEMS;

    private int overarchingLives = 3;
    private boolean endScreenClicked = false;

    private boolean dialogueOpen = false;
    private PauseMenu winScreen;
    private boolean winScreenOpen = false;

    /**
     * Sole constructor for the machanics class.
     * @param keyPresses required to manage the user input.
     * @param window sets the window the game is running in.
     * @param NPCs sets the NPCs which can be interacted with.
     * @param ITEMS sets the items which can be collected throughout the game.
     * @param interaction
     */
    public Mechanics(Map<String, Boolean> keyPresses, RenderWindow window, ArrayList<Npc> NPCs, ArrayList<Item> ITEMS, Dialogue interaction) {
        this.keyPresses = keyPresses;
        this.window = window;
        this.interaction = interaction;
        this.NPCs = NPCs;
        this.ITEMS = ITEMS;

        this.initKeyPressesMap();
    }

    /**
     * Maintains state for key presses; defaulting all unpressed.
     */
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
     * Sets state of interactable keys.
     * @param movementKey sets the key which is being pressed.
     * @param pressed sets whether the key is pressed or not.
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
     * Handles keys pressed, and released by the user.
     * States handled include: primary game events, battle events, dialogue, and battle dialogue.
     * @param wizard sets the interactor.
     */
    public void handleEvents(MoveableCharacter wizard) {
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
                    if ((keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN)) {
                        handleKeyPress(keyEvent, true);
                        if(this.dodgeGame!=null){
                            this.dodgeGame.handleInput(keyEvent);
                        }
                        break;
                    }

                    if (keyEvent.key == Keyboard.Key.ESCAPE) {
                        if (keyPresses.get("ESCAPE")) {
                            keyPresses.put("ESCAPE", false);
                            pauseMenuOpen = false;
                        }
                        else {
                            pauseMenu = new PauseMenu(true, false);
                            handleKeyPress(keyEvent, true);
                        }
                    }

                    interactingNPC = null;
                    for (Npc npc : NPCs) {
                        if (wizard.isWithinInteractionRadius(npc) && npc.shouldDraw() && npc.getName()!="Whiskers") {
                            interactingNPC = npc;
                        }
                    }

                    interactingItem = null;
                    for (Item item : ITEMS) {
                        if (wizard.isWithinInteractionRadius(item) && !(item.isFound()) && item.availableToCollect()) {
                            interactingItem = item;
                        }
                    }

                    if (interactingNPC == null && interactingItem == null) {
                        break;
                    }

                    if (keyEvent.key == Keyboard.Key.SPACE && !pauseMenuOpen) {
                        if (keyPresses.get("SPACE")) {
                            if (interactingNPC != null && interactingItem == null && interactingNPC.shouldDraw() && !battleScreenOpen) {

                                if (interactingNPC.getCurrentIndex() < interactingNPC.getScript().size()) {
                                    interaction.setTextContent(String.valueOf(interactingNPC.getScript().get(interactingNPC.getCurrentIndex())));
                                    interactingNPC.incrementCurrentIndex();
                                    String tmp = interaction.getCharacterName();
                                    interaction.setCharacterName(tmp);
                                    if(tmp=="BATTLE"){
                                        if(interactingNPC.getBattleDifficulty()!=""){
                                            dodgeGame = new DodgeGame(interactingNPC, interactingNPC.getBattleDifficulty(), this);
                                        }
                                        else{
                                            dodgeGame = new DodgeGame(interactingNPC, "EASY", this);
                                        }
                                        keyPresses.put("B", true);
                                        interaction.setCharacterName(interaction.getCharacterName());
                                    }
                                    if(tmp=="..."){
                                        if(((Npc) Npc.getCharacterByName("Whiskers")) != null){
                                            ((Npc) Npc.getCharacterByName("Whiskers")).setShouldDraw(true);
                                        }
                                        keyPresses.put("SPACE", true);
                                    }
                                }

                                else {
                                    keyPresses.put("SPACE", false);
                                    interactingNPC.resetScript();
                                    if(interactingNPC.hasCompletedTask() || interactingNPC.getName()=="Mayor"){
                                        if(interactingNPC.getName() != "Gluttony"){
                                            interactingNPC.setAssociatedNPCsToShow();
                                            interactingNPC.setShouldDraw(false);
                                        }
                                        else{
                                            winScreen = new PauseMenu(false, true);
                                            winScreenOpen = true;
                                        }
                                    }
                                    dialogueOpen=false;
                                }
                            } else if (battleScreenOpen) {

                                if (interactingNPC.getCurrentBattleIndex() < interactingNPC.getBattleScript().size()) {
                                    if (!dodgeGame.isFinishedDialogue()) {
                                        dodgeGame.setTextContent(String.valueOf(interactingNPC.getBattleScript().get(interactingNPC.getCurrentBattleIndex())));
                                        interactingNPC.incrementCurrentBattleIndex();
                                        dodgeGame.setFinishedDialogue(false);
                                    }
                                }

                                else {
                                    keyPresses.put("SPACE", false);
                                    dodgeGame.setFinishedDialogue(true);
                                    interactingNPC.setHasCompletedTask(true);
                                    dodgeGame.setTextContent("");
                                }
                            }
                        }

                        else {
                            if (!battleScreenOpen) {
                                if (interactingNPC != null && interactingItem == null && interactingNPC.shouldDraw()) {
                                    interaction.setCharacterName(interactingNPC.getName());
                                    interaction.setOriginalInteractor(interactingNPC.getName());
                                    if(interactingNPC.getName()=="Snuffles"){
                                        interaction.setCharacterName("...");
                                    }
                                    interaction.setTextContent(interactingNPC.getScript().get(0));
                                    handleKeyPress(keyEvent, true);
                                }
                                if (interactingItem != null) {
                                    interactingItem.setAsFound(true);
                                }
                            } else {
                                if (!dodgeGame.isFinishedDialogue()) {
                                    dodgeGame.setTextContent(interactingNPC.getScript().get(0));
                                    handleKeyPress(keyEvent, true);
                                }
                            }
                        }
                        break;
                    }
                case MOUSE_BUTTON_PRESSED:
                    if(!battleScreenOpen) {
                        if (keyPresses.get("SPACE") && interactingNPC != null && interactingItem == null && interactingNPC.shouldDraw() && !pauseMenuOpen) {

                            if (interactingNPC.getCurrentIndex() < interactingNPC.getScript().size()) {
                                interaction.setTextContent(String.valueOf(interactingNPC.getScript().get(interactingNPC.getCurrentIndex())));
                                interactingNPC.incrementCurrentIndex();
                                interaction.setCharacterName(interaction.getCharacterName());
                            }

                            else if (interactingNPC.getCurrentIndex() >= interactingNPC.getScript().size()) {
                                keyPresses.put("SPACE", !(keyPresses.get("SPACE")));
                                interactingNPC.resetScript();
                                dialogueOpen=false;
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
                                if(dialogueOpen){
                                    keyPresses.put("SPACE", true);
                                }
                            }
                        }
                    }
                    if(pauseMenu!=null){
                        pauseMenu.setMouseButtonclicked(true);
                        if(event.asMouseButtonEvent()!=null){
                            pauseMenu.setMousePosition(new Vector2f(event.asMouseButtonEvent().position));
                        }
                    }
                    if(dodgeGame!=null) {
                        if (dodgeGame.isEndScreenOpen()) {
                            dodgeGame.getLostScreen().setMouseButtonclicked(true);
                            if (event.asMouseButtonEvent() != null) {
                                endScreenClicked = true;
                            }
                        }
                    }
                    if(winScreen != null){
                        winScreen.setMouseButtonclicked(true);
                        if(event.asMouseButtonEvent() != null){
                            endScreenClicked = true;
                            window.clear();
                            window.close();
                        }
                    }

                default:
                    break;
            }
        }
    }

    /**
     * Handles which screen is to be displayed, i.e. dialogue, dodge game, battle, or pause
     */

    public void setWindowStates() {
        if ((keyPresses.get("SPACE"))) {
            if(dodgeGame!=null){
                if(dodgeGame.isFinishedDialogue()){
                    interaction.draw(window, null);
                    dialogueOpen=true;
                }
            }
            else{
                interaction.draw(window, null);
                dialogueOpen=true;
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

    /**
     * @return whether the battle screen is currently open.
     */

    public boolean isBattleScreenOpen() {
        return battleScreenOpen;
    }

    /**
     * @return whether the pause menu is currently open.
     */
    public boolean isPauseMenuOpen() {
        return pauseMenuOpen;
    }

    /**
     * @return whether the player has quit the game.
     */

    public boolean hasPlayerQuit() {
        if(pauseMenu != null){
            return pauseMenu.playerHasQuit();
        }
        if(dodgeGame != null) {
            if (dodgeGame.isEndScreenOpen()) {
               if(endScreenClicked){
                   return true;
               }
            }
        }
        return false;
    }

    /** 
     * @return number of remaining primary lives, i.e. those in the main game, not battle.
     */
    public int getOverarchingLives() {
        return overarchingLives;
    }

    /** 
     * @param overarchingLives sets the number of primary remaining lives, i.e. those in the main game, not battle.
     */
    public void setOverarchingLives(int overarchingLives) {
        this.overarchingLives = overarchingLives;
    }

    /** 
     * @param pauseMenuOpen sets whether the pause menu should be displayed.
     */
    public void setPauseMenuOpen(boolean pauseMenuOpen) {
        this.pauseMenuOpen = pauseMenuOpen;
    }
    
    /** 
     * @return the screen which displays the player has won.
     */
    public PauseMenu getWinScreen() {
        return winScreen;
    }

    /** 
     * @return whether the win screen is currently open.
     */
    public boolean isWinScreenOpen() {
        return winScreenOpen;
    }

    /** 
     * If the dodge game is open, use the mechanics of the dodge game to move the wizard around battle.
     */
    public void handleWizardMovement(){
        if(dodgeGame!=null){
            dodgeGame.handleWizardMovement();
        }
    }
    
}
