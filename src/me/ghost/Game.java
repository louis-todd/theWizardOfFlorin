<<<<<<< HEAD
package me.ghost;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class Game {
    private final RenderWindow window;

    private Character wizard;
    private Character npc;
    private boolean RIGHT = false;
    private boolean LEFT = false;
    private boolean UP = false;
    private boolean DOWN = false;
    private boolean FIRSTSPACE = false;
    private Text dialogueWindowText;
    private Dialogue interaction;

    /**
     * Constructor for the game class
     */
    public Game() {
        //Create the window and set window name to: 'Welcome Wizards'
        window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");

        wizard = new Character(320, 240, 0.05f, "resources/smileyface.png");
        npc = new Character(250, 300, 0.05f, "resources/smileyface.png");

        //Limit the framerate
        window.setFramerateLimit(120);

    }

    /**
     * Runs the window including inputs and updating the window
     */
    public void run() {
        while (window.isOpen()) {
            handleEvents();

            //instead of moveWizard have draw sprites which draws all sprites in their updated positions
            moveWizard(wizard);

            isDialogue();

            loadMap();

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

    private void isDialogue() {

        //If its the first time space is pressed, set the text
        if(FIRSTSPACE){
            Dialogue genericText = new Dialogue("resources/Roboto-Regular.ttf", "Hello Wizards!");
            dialogueWindowText = genericText.getText();
            interaction = new Dialogue("resources/Roboto-Regular.ttf", "Hello Wizards!");
        }

    }

    /**
     * Updates the window
     */
    private void updateWindow(){
        window.clear(Color.RED);

        window.draw(wizard);
        window.draw(npc);

        //Only draw if it is the first space
        if(FIRSTSPACE) {
            window.draw(dialogueWindowText);
            interaction.draw(window, null);
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

    private void loadMap() {
        File csvFile = new File("resources/map._House.csv");
        if (csvFile.isFile()) {
            String row;
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader("resources/map._House.csv"));
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    int imageNo = Integer.valueOf(data[0]);
                    switch (imageNo){
                        case -1:
                            Texture texture2 = new Texture();

                            try {
                                //Load the image.
                                Image image = new Image();
                                image.loadFromFile(Paths.get("resources/tiles/tile6.png"));

                                //Apply the color mask
                                //image.createMaskFromColor(Color.BLUE);

                                //Load the masked image into the texture
                                texture2.loadFromImage(image);
                            } catch(IOException|TextureCreationException ex) {
                                System.err.println("Something went wrong:");
                                ex.printStackTrace();
                            }
                        default:
                            //draw nothing
                    }
                }
                csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

=======
package me.ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.ghost.Characters.MoveableCharacter;
import me.ghost.Characters.Npc;
import me.ghost.ResourceEnum.FontType;
import me.ghost.ResourceEnum.TextureType;
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
    public Game() {
        this.initKeyPressesMap();

        //Create the window and set window name to: 'Welcome Wizards'
        window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");
        toDraw = new ArrayList<>();

        toDraw.add(wizard);
        Npc npc = new Npc(250, 300, TextureType.SQUARE16.getTexture());
        toDraw.add(npc);
        //Limit the framerate
        window.setFramerateLimit(120);
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
>>>>>>> master
