
package me.ghost;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.ghost.characters.MoveableCharacter;
import me.ghost.characters.Npc;
import me.ghost.map.GameMap;
import me.ghost.resourceEnum.FontType;
import me.ghost.resourceEnum.TextureType;
import me.ghost.resourceEnum.TileLoader;
import org.jsfml.graphics.*;

import org.jsfml.window.VideoMode;

public class Game {

    private final RenderWindow window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");
    private Dialogue interaction = new Dialogue(FontType.ROBOTO.getFont(), TextureType.BOARD.getTexture(), "REPLACE ME", "Content Placeholder");
    private BattleWindow battleWindow = new BattleWindow();
    private final MoveableCharacter wizard = new MoveableCharacter("Name Placeholder", 320, 240, TextureType.SQUARE16.getTexture());
    private final List<Drawable> toDraw = new ArrayList<>();
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();
    private Npc npc = new Npc("Name Placeholder", 250, 300, TextureType.SQUARE16.getTexture());
    private Mechanics game = new Mechanics(keyPresses, window, npc, interaction, battleWindow);
    private Drawable[] itemsToDraw = {wizard, npc};
    GameMap mapHouse;
    View worldView;
    private final int[] drawingBounds;

    /**
     * Constructor for the game class
     */
    public Game() throws FileNotFoundException {


        TileLoader tileLoader = new TileLoader();
        game.initKeyPressesMap();
        toDraw.addAll(Arrays.asList(itemsToDraw));
        interaction.setCharacterName(npc.getName());

        mapHouse = new GameMap("resources/map._House.csv", 50, tileLoader);
        //Limit the framerate
        window.setFramerateLimit(120);
        worldView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
        worldView.setCenter(wizard.getPosition());
        drawingBounds = new int[4];
    }

    /**
     * Runs the window including inputs and updating the window
     */
    public void run() {
        while (window.isOpen()) {
            game.handleEvents(wizard);
            wizard.moveCharacter(keyPresses, toDraw, worldView);
            updateWindow();
        }
    }

    /**

     * Updates the window
     */
    private void updateWindow(){
        window.clear(Color.RED);

        drawTiles();

        for(Drawable item : toDraw){
            window.draw(item);
        }
        game.isDialogue();

        window.setView(worldView);
        window.display();
    }

    private void drawTiles(){
        drawingBounds();

        for(int i = drawingBounds[2]; i <= drawingBounds[3]; i++){
            for(int j = drawingBounds[0]; j <= drawingBounds[1]; j++){
                mapHouse.getTile(i, j).draw(window, RenderStates.DEFAULT);
            }
        }
    }

    private void drawingBounds(){
        drawingBounds[0] = Math.max((int) (wizard.getPosition().x / 16) - 20, 0);
        drawingBounds[1] = Math.min((int) (wizard.getPosition().x / 16) + 20, 49);
        drawingBounds[2] = Math.max((int) (wizard.getPosition().y / 16) - 20, 0);
        drawingBounds[3] = Math.min((int) (wizard.getPosition().y / 16) + 20, 49);
    }
}

