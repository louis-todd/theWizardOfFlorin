
package me.ghost;

import me.ghost.characters.MoveableCharacter;
import me.ghost.characters.Npc;
import me.ghost.resourceEnum.FontType;
import me.ghost.resourceEnum.TileLoader;
import me.ghost.resourceEnum.TextureType;
import me.ghost.map.GameMap;
import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Game {

    private final RenderWindow window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");
    private Dialogue interaction = new Dialogue(FontType.ROBOTO.getFont(), TextureType.BOARD.getTexture(), "REPLACE ME",
            "Content Placeholder");
    private BattleWindow battleWindow = new BattleWindow();
    private final MoveableCharacter wizard = new MoveableCharacter("Name Placeholder", 320, 240,
            TextureType.SQUARE16.getTexture());
    private final List<Drawable> toDraw = new ArrayList<>();
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();

    private Npc npc = new Npc("Placeholder1", 250, 300, TextureType.SQUARE16.getTexture());
    private Npc npc2 = new Npc("Placeholder2", 150, 300, TextureType.SQUARE16.getTexture());
    private Npc npc3 = new Npc("Placeholder3", 50, 300, TextureType.SQUARE16.getTexture());
    private Npc[] npcArray = { npc, npc2, npc3 };
    private ArrayList<Npc> NPCs = new ArrayList<Npc>(Arrays.asList(npcArray));

    private Mechanics game = new Mechanics(keyPresses, window, NPCs, interaction, battleWindow);
    private Drawable[] itemsToDraw = { wizard, npc, npc2, npc3 };
    GameMap mapHouse;
    GameMap currentMap;
    View worldView;
    private final Map<String, Integer> drawingBounds = new CaseInsensitiveMap<>();

    /**
     * Constructor for the game class
     */
    public Game() {

        TileLoader tileLoader = new TileLoader();
        game.initKeyPressesMap();
        toDraw.addAll(Arrays.asList(itemsToDraw));
        interaction.setCharacterName(npc.getName());

        mapHouse = new GameMap("resources/map._House.csv", 50, tileLoader);
        worldView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
        worldView.setCenter(wizard.getPosition());
        currentMap = mapHouse;

        // Limit the framerate
        window.setFramerateLimit(120);
    }

    /**
     * Runs the window including inputs and updating the window
     */
    public void run() {
        while (window.isOpen()) {
            game.handleEvents(wizard);
            wizard.moveCharacter(keyPresses, toDraw, worldView, currentMap);
            updateWindow();
        }
    }

    /**
     * 
     * Updates the window
     */
    private void updateWindow() {
        window.clear(Color.RED);

        drawTiles();

        for (Drawable item : toDraw) {
            window.draw(item);
        }
        game.isDialogue();

        window.setView(worldView);
        window.display();
    }

    private void drawTiles() {
        initialiseDrawingBounds();

        for (int i = drawingBounds.get("TopCameraEdge"); i <= drawingBounds.get("BottomCameraEdge"); i++) {
            for (int j = drawingBounds.get("LeftCameraEdge"); j <= drawingBounds.get("RightCameraEdge"); j++) {
                currentMap.getTile(i, j).draw(window, RenderStates.DEFAULT);
            }
        }
    }

    private void initialiseDrawingBounds() {
        int tileSize = 16;
        int cameraWidth = 40;
        this.drawingBounds.put("LeftCameraEdge",
                Math.max((int) (worldView.getCenter().x / tileSize) - (cameraWidth / 2), 0));
        this.drawingBounds.put("RightCameraEdge",
                Math.min((int) (worldView.getCenter().x / tileSize) + (cameraWidth / 2), currentMap.getDrawWidth()));
        this.drawingBounds.put("TopCameraEdge",
                Math.max((int) (worldView.getCenter().y / tileSize) - (cameraWidth / 2), 0));
        this.drawingBounds.put("BottomCameraEdge",
                Math.min((int) (worldView.getCenter().y / tileSize) + (cameraWidth / 2), currentMap.getDrawHeight()));
    }

}