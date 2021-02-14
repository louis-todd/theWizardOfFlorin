
package me.ghost;

import me.ghost.Characters.MoveableCharacter;
import me.ghost.Characters.Npc;
import me.ghost.ResourceEnum.TileLoader;
//import me.ghost.battle.BattleWindow;
import me.ghost.battle.BattleWindow2;
import me.ghost.map.GameMap;
import me.ghost.ResourceEnum.FontType;
import me.ghost.ResourceEnum.TextureType;
import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Game {

    private final RenderWindow window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");
    private final MoveableCharacter wizard = new MoveableCharacter("Name Placeholder", 320, 240, TextureType.LEFTSTILL.getTexture());
    private final List<Drawable> toDraw = new ArrayList<>();
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();

//    private BattleWindow battleWindow = new BattleWindow();
    private BattleWindow2 battleWindow2 = new BattleWindow2();
    private Npc npc = new Npc("Mayor", 250, 300, TextureType.GHOST.getTexture());
    private Npc npc2 = new Npc("Placeholder2", 150, 300, TextureType.GHOST.getTexture());
    private Npc npc3 = new Npc("Placeholder3", 50, 300, TextureType.GHOST.getTexture());
    private Npc npc4 = new Npc("Placeholder4 ", 700, 300, TextureType.GHOST.getTexture());
    private Npc[] npcArray = {npc, npc2, npc3, npc4};
    private ArrayList<Npc> NPCs = new ArrayList<Npc>(Arrays.asList(npcArray));

    private Drawable[] itemsToDraw = {wizard, npc, npc2, npc3, npc4};
    private TileLoader tileLoader = new TileLoader();
    private GameMap mapHouse = new GameMap("resources/map._House.csv", 50, tileLoader);
    ;
    private GameMap currentMap = mapHouse;
    private View worldView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
    ;
    private View battleView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
    private final Map<String, Integer> drawingBounds = new CaseInsensitiveMap<>();
    private Dialogue interaction = new Dialogue(worldView, FontType.ROBOTO.getFont(), TextureType.BOARD.getTexture(), "REPLACE ME", "Content Placeholder");
    ;
    private Mechanics game = new Mechanics(keyPresses, window, NPCs, interaction, battleWindow2);
    private GameMap baseLayer = new GameMap("resources/finalmap_Base Layer.csv", 250, tileLoader);
    private GameMap topLayer = new GameMap("resources/finalmap_Extra Layer.csv", 250, tileLoader);

    /**
     * Constructor for the game class
     */
    public Game() {

        worldView.setCenter(wizard.getPosition());

        toDraw.addAll(Arrays.asList(itemsToDraw));

        window.setFramerateLimit(120);                                                                                      // Limit the framerate
    }

    /**
     * Runs the window including inputs and updating the window
     */
    public void run() {
        while (window.isOpen()) {
            game.handleEvents(wizard);
            if (!game.isBattleScreenOpen()) {
                wizard.moveCharacter(keyPresses, toDraw, worldView, topLayer);
            }
            updateWindow();
        }
    }

    /**
     * Updates the window
     */
    private void updateWindow() {
        window.clear(Color.RED);

        if (!game.isBattleScreenOpen()) {
            drawTiles();
            window.setView(worldView);
        } else {
            window.setView(battleView);
        }

        for (Drawable item : toDraw) {
            window.draw(item);
        }
        game.isDialogue();

        window.display();
    }

    private void drawTiles() {
        initialiseDrawingBounds();

        for (int i = drawingBounds.get("TopCameraEdge"); i <= drawingBounds.get("BottomCameraEdge"); i++) {
            for (int j = drawingBounds.get("LeftCameraEdge"); j <= drawingBounds.get("RightCameraEdge"); j++) {
                baseLayer.getTile(i, j).draw(window, RenderStates.DEFAULT);
                topLayer.getTile(i, j).draw(window, RenderStates.DEFAULT);
            }
        }

    }

    private void initialiseDrawingBounds() {
        int tileSize = 16;
        int cameraWidth = 40;
        this.drawingBounds.put("LeftCameraEdge",
                Math.max((int) (worldView.getCenter().x / tileSize) - (cameraWidth / 2), 0));
        this.drawingBounds.put("RightCameraEdge",
                Math.min((int) (worldView.getCenter().x / tileSize) + (cameraWidth / 2), topLayer.getDrawWidth()));
        this.drawingBounds.put("TopCameraEdge",
                Math.max((int) (worldView.getCenter().y / tileSize) - (cameraWidth / 2), 0));
        this.drawingBounds.put("BottomCameraEdge",
                Math.min((int) (worldView.getCenter().y / tileSize) + (cameraWidth / 2), topLayer.getDrawHeight()));
    }

}