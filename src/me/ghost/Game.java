
package me.ghost;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import me.ghost.battle.BattleWindow;
import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import me.ghost.data.TileLoader;
import me.ghost.map.GameMap;
import me.ghost.data.FontType;
import me.ghost.data.TextureType;
import me.ghost.map.Tile;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private static final Vector2f TEXT_POSITION = new Vector2f(200, 200);
    private static final Vector2f LOADING_BAR_POSITION = new Vector2f(50, 400);

    private final RenderWindow window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");

    private final List<Drawable> toDraw = new ArrayList<>();
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();

    private final TileLoader tileLoader = new TileLoader();
    private final GameMap mapHouse = new GameMap("resources/map._House.csv", 50, tileLoader);
    private final GameMap currentMap = mapHouse;
    private final View worldView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
    private final View battleView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
    private final View pauseView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
    private BattleWindow battleWindow = new BattleWindow();

    private Item axe = new Item("axe", 300, 300, TextureType.SQUARE16.getTexture());

    private Item[] itemArray = { axe };
    private ArrayList<Item> ITEMS = new ArrayList<>(Arrays.asList(itemArray));

    private final MoveableCharacter wizard = new MoveableCharacter("Name Placeholder", 320, 150, TextureType.FRONT1.getTexture(), ITEMS);

    private Npc npc = new Npc("Mayor", 250, 300, TextureType.GHOST.getTexture());
    private Npc npc2 = new Npc("TestPerson", 150, 300, TextureType.GHOST.getTexture());
    private Npc npc3 = new Npc("Placeholder3", 50, 300, TextureType.GHOST.getTexture());

    private Npc[] npcArray = { npc, npc2, npc3 };
    private ArrayList<Npc> NPCs = new ArrayList<>(Arrays.asList(npcArray));

    private Drawable[] itemsToDraw = { wizard, npc, npc2, npc3, axe };
    private final Dialogue interaction = new Dialogue(worldView, FontType.ROBOTO.getFont(), TextureType.BOARD.getTexture(), "REPLACE ME", "Content Placeholder");
    private Mechanics game = new Mechanics(keyPresses, window, NPCs, ITEMS, interaction, battleWindow);

    private final Map<String, Integer> drawingBounds = new CaseInsensitiveMap<>();
    private final GameMap baseLayer = new GameMap("resources/finalmapv2_Base Layer.csv", 250, tileLoader);
    private final GameMap topLayer = new GameMap("resources/finalmapv2_Extra Layer.csv", 250, tileLoader);

    private int loadingBarCounter = 0;
    private Text loadingText = null;
    private RectangleShape loadingBar = null;

    private List<CircleShape> livesDisplay = new ArrayList<>(Arrays.asList(lifeCircle(1), lifeCircle(2), lifeCircle(3)));


    /**
     * Constructor for the game class
     */
    public Game() {
        worldView.setCenter(wizard.getPosition());
        toDraw.addAll(Arrays.asList(itemsToDraw));
        window.setFramerateLimit(120);
    }

    /**
     * Runs the window including inputs and updating the window
     */
    public void run() {
        while (window.isOpen()) {
            if (!this.tileLoader.isLoaded()) {
                this.drawLoadingScreen(window);
                continue;
            }

            if (!this.topLayer.hasLoaded()) {
                this.topLayer.loadMap();
            }

            if (!this.baseLayer.hasLoaded()) {
                this.baseLayer.loadMap();
            }

            game.handleEvents(wizard);
            if (!game.isBattleScreenOpen()) {
                wizard.moveCharacter(keyPresses, toDraw, worldView, topLayer);
            }
            updateWindow();
            if(game.hasPlayerQuit()){
                window.clear();
                window.close();
            }
        }

        TileLoader.THREADS.shutdown();
    }

    private void drawLoadingScreen(RenderWindow window) {
        window.clear(Color.BLACK);

        RectangleShape loadingBar = this.getLoadingBar();

        loadingBar.setSize(new Vector2f(loadingBarCounter, 50));
        loadingBarCounter += 2;

        window.draw(loadingBar);
        window.draw(this.getLoadingText());
        window.display();
    }

    private Text getLoadingText() {
        if (this.loadingText == null) {
            Text text = new Text();

            text.setPosition(TEXT_POSITION);
            text.setString("Ghost Game v 1.0.0");
            text.setColor(Color.WHITE);
            text.setCharacterSize(20);
            text.setFont(FontType.ROBOTO.getFont());
            this.loadingText = text;
        }

        return this.loadingText;
    }

    private RectangleShape getLoadingBar() {
        if (this.loadingBar == null) {
            RectangleShape loadingBar = new RectangleShape();

            loadingBar.setPosition(LOADING_BAR_POSITION);
            loadingBar.setSize(new Vector2f(loadingBarCounter, 50));
            loadingBar.setFillColor(Color.WHITE);
            this.loadingBar = loadingBar;
        }

        return this.loadingBar;
    }

    /**
     * Updates the window
     */
    private void updateWindow() {
        window.clear(Color.RED);

        if(game.isBattleScreenOpen()){
            window.setView(battleView);
        }
        else if(game.isPauseMenuOpen()){
            window.setView(pauseView);
        }
        else{
            drawTiles();
            window.setView(worldView);
            for(CircleShape circleShape : livesDisplay){
                window.draw(circleShape);
            }
            updateLifePosition(worldView);
        }

        // if (!game.isBattleScreenOpen()) {
        //     drawTiles();
        //     window.setView(worldView);
        // } else {
        //     window.setView(battleView);
        // }

        for (Drawable item : toDraw) {
            if (item instanceof Item) {
                if (!((Item) item).isFound() && ((Item) item).availableToCollect()) {
                    window.draw(item);
                }
            }
            // if(item instanceof Npc){
            // if(((Npc) item).shouldDraw()) {
            // window.draw(item);
            // }
            // }
            // move wizard
            else {
                window.draw(item);
            }
        }
        game.isDialogue();

        window.display();
        if(this.livesDisplay.size() > this.game.getOverarchingLives() && this.livesDisplay.size() != 0){
            this.livesDisplay.remove(this.livesDisplay.size() - 1);
        }
        System.out.println(this.game.getOverarchingLives());
    }

    private void drawTiles() {
        initialiseDrawingBounds();
        for (int i = drawingBounds.get("TopCameraEdge"); i <= drawingBounds.get("BottomCameraEdge"); i++) {
            for (int j = drawingBounds.get("LeftCameraEdge"); j <= drawingBounds.get("RightCameraEdge"); j++) {
                baseLayer.getTile(i, j).draw(window, RenderStates.DEFAULT);
                topLayer.getTile(i, j).draw(window, RenderStates.DEFAULT);
                if(topLayer.getTile(i, j).isCollideable()) {
                    wizard.setNearbyTiles(topLayer.getTile(i, j));
                }
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

    private CircleShape lifeCircle(int positionNumber){
        CircleShape lifeCircle = new CircleShape(6);
        lifeCircle.setFillColor(Color.RED);
        lifeCircle.setPosition(640-16*positionNumber, 5);
        return lifeCircle;
    }

    private void updateLifePosition(View worldView){
        int i = 1;
        for(CircleShape circleShape : livesDisplay){
            circleShape.setPosition(worldView.getCenter().x + worldView.getSize().x/2 - i * 16, worldView.getCenter().y - worldView.getSize().y/2 + 5);
            i++;
        }
    }

}