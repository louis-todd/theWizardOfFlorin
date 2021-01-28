
package me.ghost;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.ghost.characters.MoveableCharacter;
import me.ghost.characters.Npc;
import me.ghost.map.GameMap;
import me.ghost.resourceEnum.FontType;
import me.ghost.resourceEnum.TextureType;
import org.jsfml.graphics.*;

import org.jsfml.window.VideoMode;

public class Game {

    private final RenderWindow window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");;
    private Dialogue interaction = new Dialogue(FontType.ROBOTO.getFont(), TextureType.BOARD.getTexture(), "REPLACE ME", "Content Placeholder");
    private BattleWindow battleWindow = new BattleWindow();
    private final MoveableCharacter wizard = new MoveableCharacter("Name Placeholder", 320, 240, TextureType.SQUARE16.getTexture());
    private final List<Drawable> toDraw = new ArrayList<>();;
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();
    private Npc npc = new Npc("Name Placeholder", 250, 300, TextureType.SQUARE16.getTexture());
    private Mechanics game = new Mechanics(keyPresses, window, npc, interaction, battleWindow);
    private Drawable[] itemsToDraw = {wizard, npc};
    GameMap mapHouse;


    /**
     * Constructor for the game class
     */
    public Game() throws FileNotFoundException {

        game.initKeyPressesMap();
        toDraw.addAll(Arrays.asList(itemsToDraw));
        interaction.setCharacterName(npc.getName());

        mapHouse = new GameMap("resources/map._House.csv");
        //Limit the framerate
        window.setFramerateLimit(120);
    }

    /**
     * Runs the window including inputs and updating the window
     */
    public void run() {
        while (window.isOpen()) {
            game.handleEvents(wizard);
            wizard.moveCharacter(keyPresses, toDraw);
            updateWindow();
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
        game.isDialogue();

        mapHouse.getTile(3, 3).draw(window, RenderStates.DEFAULT);
        for(int i = 0; i <= 10; i++){
            for(int j = 0; j <= 10; j++){
                mapHouse.getTile(i, j).draw(window, RenderStates.DEFAULT);
            }
        }

        window.display();
    }
}

