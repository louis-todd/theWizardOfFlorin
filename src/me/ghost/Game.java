
package me.ghost;

import me.ghost.battle.BattleWindow;
import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import me.ghost.data.TileLoader;
import me.ghost.map.GameMap;
import me.ghost.data.FontType;
import me.ghost.data.TextureType;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Game is used to control the game loop and main handling/updating of state.
 */

public class Game {

    private static final Vector2f TEXT_POSITION = new Vector2f(200, 200);
    private static final Vector2f LOADING_BAR_POSITION = new Vector2f(50, 400);

    private final RenderWindow window = new RenderWindow(new VideoMode(640, 480), "Welcome Wizards");
    private final List<Drawable> toDraw = new ArrayList<>();
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();
    private final TileLoader tileLoader = new TileLoader();
    private final View worldView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
    private final View battleView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
    private final View pauseView = new View(window.getDefaultView().getCenter(), window.getDefaultView().getSize());
    private BattleWindow battleWindow = new BattleWindow();

    private Item duck = new Item("duck", 2914, 2114, TextureType.DUCK.getTexture());
    private Item ice = new Item("ice", 907, 3582, TextureType.ICE.getTexture());
    private Item tambourine = new Item("tambourine", 2312, 2413, TextureType.TAMBOURINE.getTexture());
    private Item teddy = new Item("teddy", 3545, 327, TextureType.TEDDY.getTexture());
    private Item whisky = new Item("whisky", 1901, 1930, TextureType.WHISKY.getTexture());
    private Item whisky2 = new Item("whisky2", 2697, 3596, TextureType.WHISKY.getTexture());
    private Item plank = new Item("plank", 692, 616, TextureType.WOOD.getTexture());
    private Item yarn = new Item("yarn", 3655, 3895, TextureType.YARN.getTexture());

    private Item[] itemArray = { duck, ice, tambourine, teddy, whisky, plank, yarn, whisky2 };
    private ArrayList<Item> ITEMS = new ArrayList<Item>(Arrays.asList(itemArray));

    private final MoveableCharacter wizard = new MoveableCharacter("Name Placeholder", 320, 150, TextureType.FRONT1.getTexture(), ITEMS);

    private Npc Whiskers = new Npc("Whiskers", 300, 170, TextureType.WHISKERS.getTexture(), 0, "");
    private Npc Mayor = new Npc("Mayor", 260, 150, TextureType.GHOST.getTexture(), 0, "");
    private Npc CrazyJoe = new Npc("CrazyJoe", 3155, 3176, TextureType.GHOST.getTexture(), 4, "");
    private Npc Gluttony = new Npc("Gluttony", 3029, 1062, TextureType.GHOST.getTexture(), 0, "HARD");
    private Npc PirateJack = new Npc("PirateJack", 2697, 588, TextureType.GHOST.getTexture(), 1, "");
    private Npc Sibirius = new Npc("Sibirius", 580, 3727, TextureType.GHOST.getTexture(), 0, "INTERMEDIATE");
    private Npc Snuffles = new Npc("Snuffles", 349, 140, TextureType.SNUFFLES.getTexture(), 0, "EASY");
    private Npc Summer = new Npc("Summer", 2402, 1469, TextureType.GHOST.getTexture(), 3, "");
    private Npc Tree = new Npc("Tree", 990, 1853, TextureType.TREE.getTexture(), 0, "EASY");

    private Npc[] npcArray = { Mayor, CrazyJoe, Gluttony, PirateJack, Sibirius, Snuffles, Summer, Tree, Whiskers };
    private ArrayList<Npc> NPCs = new ArrayList<Npc>(Arrays.asList(npcArray));

    private Drawable[] itemsToDraw = { wizard, Mayor, CrazyJoe, Gluttony, PirateJack, Sibirius, Snuffles,  Summer, Tree, Whiskers, duck, ice, tambourine, teddy, whisky, plank, yarn, whisky2 };
    private final Dialogue interaction = new Dialogue(worldView, FontType.ROBOTO.getFont(), TextureType.BOARD.getTexture(), "REPLACE ME", "Content Placeholder");
    private Mechanics game = new Mechanics(keyPresses, window, NPCs, ITEMS, interaction);

    private final Map<String, Integer> drawingBounds = new CaseInsensitiveMap<>();
    private final GameMap baseLayer = new GameMap("resources/finalmapv2_Base_Layer.csv", 250, tileLoader);
    private final GameMap topLayer = new GameMap("resources/finalmapv2_Extra_Layer.csv", 250, tileLoader);

    private int loadingBarCounter = 0;
    private Text loadingText = null;
    private RectangleShape loadingBar = null;

    private final List<Sprite> liveDisplay = new ArrayList<>(Arrays.asList(createHeart(1), createHeart(2), createHeart(3)));
    private final RectangleShape dashBoard = new RectangleShape(new Vector2f(655, 45));


    /**
     * Sole constructor for Game class which sets the view, adds sprites to the game, sets framerate, and adds the dashboard.
     */
    public Game() {
        worldView.setCenter(wizard.getPosition());
        toDraw.addAll(Arrays.asList(itemsToDraw));
        Mayor.setShouldDraw(true);
        CrazyJoe.setShouldDraw(true);
        window.setFramerateLimit(120);
        this.dashBoard.setFillColor(Color.BLACK);
    }

    /**
     * Updates states depending on which screen the user is viewing.
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
            if(game.isBattleScreenOpen()){
                game.handleWizardMovement();
            }
            if (!game.isBattleScreenOpen()) {
                wizard.moveCharacter(keyPresses, toDraw, worldView, topLayer, Whiskers);
            }
            updateWindow();
            if(game.hasPlayerQuit()){
                window.clear();
                window.close();
            }
        }
        TileLoader.THREADS.shutdown();
    }

    /**
     * @param window sets the window in which the loading screen should be drawn on.
     */
    private void drawLoadingScreen(RenderWindow window) {
        window.clear(Color.BLACK);

        RectangleShape loadingBar = this.getLoadingBar();

        loadingBar.setSize(new Vector2f(loadingBarCounter, 50));
        loadingBarCounter += 2;

        window.draw(loadingBar);
        window.draw(this.getLoadingText());
        window.display();
    }

    /**
     * @return the formatted text to be used on the loading screen.
     */
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

    /**
     * Gets the graphic or a bar loading.
     * @return the graphic of a bar loading.
     */

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
     * Updates window state based on current screen.
     */
    private void updateWindow() {
        window.clear(Color.RED);

            if (game.isBattleScreenOpen()) {
                window.setView(battleView);
            }
            else if (game.isPauseMenuOpen()) {
                window.setView(pauseView);
            }
            else if(this.game.isWinScreenOpen()){
                    this.window.clear();
                    this.window.setView(battleView);
                    this.game.getWinScreen().draw(window);
            }
            else {
                drawTiles();
                window.setView(worldView);
                updateLifePosition(worldView);
                updateItemPosition(worldView);


                updateDashBoardPosition(worldView);

                window.draw(dashBoard);

                for(Sprite heart : liveDisplay){
                    window.draw(heart);
                }
                for(Item itemToFind : Item.getItemsToDrawOnDashboard()){
                    window.draw(itemToFind);
                }
                
                for (Drawable item : toDraw) {
                    if (item instanceof Item) {
                        if (!((Item) item).isFound() && ((Item) item).availableToCollect()) {
                            window.draw(item);
                        }
                    }
                    else if(item instanceof Npc){
                        if(((Npc) item).shouldDraw()) {
                            window.draw(item);
                        }
                    }
                    else {
                        window.draw(item);
                    }
                }
            }
    
        game.setWindowStates();

        window.display();
        if (this.liveDisplay.size() > this.game.getOverarchingLives() && this.liveDisplay.size() != 0) {
            this.liveDisplay.remove(this.liveDisplay.size() - 1);
        }
    }

    /**
     * Draw the tiles in the current view of the map.
     */

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

    /**
     * Set the range of the tiles in the current view.
     */
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

    /** 
     * Draws a health unit.
     * @param positionNumber sets the position of the heart being drawn.
     * @return the graphic of a heart to represent a health unit.
     */
    private Sprite createHeart(int positionNumber){
        Sprite heartShape = new Sprite(TextureType.HEART.getTexture());
        heartShape.setPosition(640 - 32 * positionNumber, 5);
        return heartShape;
    }

    /** 
     * Update the position of the heart on the dashboard dependent on how many lives are remaining.
     * @param worldView sets the current world view so the heart is drawn respectively to the view.
     */
    private void updateLifePosition(View worldView){
        int i = 1;
        for(Sprite heart : liveDisplay){
            heart.setPosition(worldView.getCenter().x + worldView.getSize().x/2 - i * 35, worldView.getCenter().y + worldView.getSize().y/2 - 32);
            i++;
        }
    }

    private void updateItemPosition(View worldView){
        int i = 1;
        for(Item itemToFind : Item.getItemsToDrawOnDashboard()){
            itemToFind.setPosition(worldView.getCenter().x - worldView.getSize().x/2 + i * 36, worldView.getCenter().y + worldView.getSize().y/2 - 30);
            i++;
        }
    }

    /** 
     * Sets the position of the dashboard in respect to the current view.
     * @param worldView sets the current view.
     */
    private void updateDashBoardPosition(View worldView){
        this.dashBoard.setPosition(worldView.getCenter().x - worldView.getSize().x/2 - 10, worldView.getCenter().y + worldView.getSize().y/2 - 35);

    }

}