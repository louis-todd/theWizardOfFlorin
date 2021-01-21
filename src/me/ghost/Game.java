
package me.ghost;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.ghost.characters.MoveableCharacter;
import me.ghost.characters.Npc;
import me.ghost.map.GameMap;
import me.ghost.resourceEnum.FontType;
import me.ghost.resourceEnum.TextureType;
import org.jsfml.graphics.*;

import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

public class Game {

    private final RenderWindow window;
    private Dialogue interaction = new Dialogue(FontType.ROBOTO.getFont(), TextureType.BOARD.getTexture(), "Name Placeholder", "Content Placeholder");
    private final MoveableCharacter wizard = new MoveableCharacter(320, 240, TextureType.SQUARE16.getTexture());
    private final List<Drawable> toDraw;
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();

    private String[] testSpriteText = {"Page 1", "Page 2", "Page 3"};
    private int charsCurrentIndex = 1;

    /**
     * Constructor for the game class
     */
    public Game() throws FileNotFoundException {
        this.initKeyPressesMap();

        //Create the window and set window name to: 'Welcome Wizards'
        window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");
        toDraw = new ArrayList<>();

        toDraw.add(wizard);
        Npc npc = new Npc(250, 300, TextureType.SQUARE16.getTexture());
        toDraw.add(npc);

        //Limit the framerate
        window.setFramerateLimit(120);
        GameMap mapHouse = new GameMap("resources/map._House.csv");
        toDraw.add(mapHouse.getTile(3, 3));
    }

    private void initKeyPressesMap() {
        this.keyPresses.put("RIGHT", false);
        this.keyPresses.put("LEFT", false);
        this.keyPresses.put("UP", false);
        this.keyPresses.put("DOWN", false);
        this.keyPresses.put("SPACE", false);
    }

    /**
     * Runs the window including inputs and updating the window
     */
    public void run() {
        while (window.isOpen()) {
            handleEvents();
            wizard.moveCharacter(keyPresses, toDraw);
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
                        if ((keyEvent.key == Keyboard.Key.RIGHT || keyEvent.key == Keyboard.Key.LEFT || keyEvent.key == Keyboard.Key.UP || keyEvent.key == Keyboard.Key.DOWN)) {
                            handleKeyPress(keyEvent, true);
                        }
                        if(keyEvent.key == Keyboard.Key.SPACE){
                            //If space has already been pressed
                            if(keyPresses.get("SPACE")){
                                //if still tiles to step through do
                                if(charsCurrentIndex<testSpriteText.length){
                                    interaction.setTextContent(String.valueOf(testSpriteText[charsCurrentIndex]));
                                    charsCurrentIndex++;
                                }
                                //if at tile limit, close
                                else{
                                    keyPresses.put("SPACE", false);
                                    charsCurrentIndex=1;
                                }
                            }
                            //if first space, set to display first tile
                            else{
                                interaction.setTextContent(testSpriteText[0]);
                                handleKeyPress(keyEvent, true);
                            }
                        }
                    break;
                case MOUSE_BUTTON_PRESSED:
                    //if still tiles left to show, step through them
                    if(charsCurrentIndex<testSpriteText.length && keyPresses.get("SPACE")){
                        interaction.setTextContent(String.valueOf(testSpriteText[charsCurrentIndex]));
                        charsCurrentIndex++;
                    }
                    //if have read all tiles, act as if space has been clicked to close the dialogue box
                    else if (charsCurrentIndex>=testSpriteText.length && keyPresses.get("SPACE")){
                        keyPresses.put("SPACE", !(keyPresses.get("SPACE")));
                        charsCurrentIndex=1;
                    }
                default:
                    break;
            }
        }
        }


    private void isDialogue() {
        //If its the first time space is pressed, set the text
        if((keyPresses.get("SPACE"))){
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
                keyPresses.put("SPACE", pressed);
                break;
            default:
                break;
        }
    }


}

