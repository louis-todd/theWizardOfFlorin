package me.ghost.battle.dodge;

import me.ghost.CaseInsensitiveMap;
import me.ghost.Mechanics;
import me.ghost.battle.WinLoseScreen;
import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import me.ghost.data.TextureType;
import me.ghost.battle.BattleWindow;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;


public class DodgeGame {

    private final BattleWindow battleWindow = new BattleWindow();

    private final Npc battleNpc;
    private final MoveableCharacter wizard;
    private final Stack<Projectile> projectileInMotion = new Stack<>();
    private final boolean battleOpen = true;
    private int lives = 3;
    private boolean invincible = false;
    private boolean battleWon = false;
    private boolean battleLost = false;
    private boolean wonOrLost = false;
    private final List<Stack<Projectile>> levels = new ArrayList<>();
    private int maxLevel;
    private int currentLevel = 0;
    private boolean projectilesOnScreen = false;
    private boolean projectileStillOnScreen = false;
    private int collideTime;
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();
    private List<String> battleDialogue = new ArrayList<>();
    private int dialogueIndex = 0;
    private Mechanics game;
    private Boolean finishedDialogue = false;
    private boolean mouseButtonclicked;
    private Vector2f mousePosition;
    private Boolean attemptedToClose = false;
    private int stepIndex = 0;
    private int walkFrameControl = 0;
    private int walkingPace = 5;
    private boolean livesChaged = false;

    public DodgeGame(Npc setBattleNpc, String difficulty, Mechanics game) {
        this.battleNpc = new Npc(setBattleNpc.getName(), battleWindow.getGhostAreaCentre().x - 16, battleWindow.getGhostAreaCentre().y - 80, (Texture) setBattleNpc.getTexture());
        this.wizard = new MoveableCharacter("Wizard", battleWindow.getPlayerAreaCentre().x - 16, battleWindow.getPlayerAreaCentre().y - 16, TextureType.FRONT1.getTexture());
        this.addProjectilesToStack(1000);
        this.game = game;
        Vector2f dimensions = new Vector2f(1,1);
        wizard.setScale(dimensions);

        this.battleWindow.getToDraw().add(this.battleNpc);
        this.battleWindow.getToDraw().add(this.wizard);
        this.setDifficulty(difficulty);
        this.initKeyPresses();
        addDialogue();

    }

    private void initKeyPresses(){
        this.keyPresses.put("RIGHT", false);
        this.keyPresses.put("LEFT", false);
        this.keyPresses.put("UP", false);
        this.keyPresses.put("DOWN", false);
        this.keyPresses.put("SPACE", false);
    }

    private void addDialogue(){
        this.battleDialogue.add("FIRST TEXT");
        this.battleDialogue.add("SECOND TEXT");
        this.battleDialogue.add("THIRD TEXT");
    }


    private Stack<Projectile> addProjectilesToStack(int numberProjectiles){
        int minSides = 3;
        int maxSides = 10;
        Stack<Projectile> projectileStack = new Stack<>();
        for(int i = 0; i < numberProjectiles; i++){
            Projectile push = projectileStack.push(new Projectile(6, ThreadLocalRandom.current().nextInt(maxSides - minSides + 1) + minSides));

            push.thrown(this.battleNpc);
        }
        return projectileStack;
    }

    private void setDifficulty(String difficulty){

        switch (difficulty) {
            case "EASY":
                for (int i = 1; i <= 10; i++) {
                    this.levels.add(addProjectilesToStack(i * 5));
                }
                maxLevel = 9;
                break;
            case "INTERMEDIATE":
                for (int i = 1; i <= 15; i++) {
                    this.levels.add(addProjectilesToStack(i * 20));
                }
                maxLevel = 14;
                break;
            case "HARD":
                for (int i = 1; i <= 20; i++) {
                    this.levels.add(addProjectilesToStack(i * 30));
                }
                maxLevel = 19;
                break;
        }
    }

    private void throwObject() {
        this.projectileInMotion.addAll(this.levels.get(currentLevel));
        this.levels.get(currentLevel).clear();

        for (Projectile projectile : this.projectileInMotion) {
            this.battleWindow.getToDraw().add(projectile);
        }
    }

    public void draw(RenderWindow window) {

        if (!wonOrLost) {
            if (finishedDialogue) {
                if (currentLevel < maxLevel) {
                    if (this.projectilesOnScreen) {
                        this.battleWindow.getToDraw().forEach(window::draw);
                        this.projectileInMotion.forEach(Projectile::applyVelocity);
                        this.projectileStillOnScreen = false;
                    } else {
                        this.currentLevel++;
                        for (Projectile p : projectileInMotion) {
                            battleWindow.getToDraw().remove(p);
                        }
                        this.projectileInMotion.clear();
                        throwObject();
                        this.battleWindow.getToDraw().forEach(window::draw);
                        this.projectileInMotion.forEach(Projectile::applyVelocity);
                        this.projectilesOnScreen = true;
                    }
                    for (Projectile p : this.projectileInMotion) {
                        collideProjectile(p);
                        if (p.getGlobalBounds().intersection(this.battleWindow.getBackground().getGlobalBounds()) != null) {
                            this.projectileStillOnScreen = true;
                        }
                    }
                    if (!this.projectileStillOnScreen) {
                        this.projectilesOnScreen = false;
                    }
                } else {
                    for (Projectile p : projectileInMotion) {
                        this.battleWindow.getToDraw().remove(p);
                    }
                    this.battleWindow.getToDraw().forEach(window::draw);
                    wonOrLost = true;

                }
            } else {
                this.battleWindow.getToDraw().forEach(window::draw);
            }
        } else {
            this.battleWindow.getToDraw().forEach(window::draw);
            if (lives <= 0) {
                battleLost = true;
                WinLoseScreen loseScreen = new WinLoseScreen(false);
                if(!this.livesChaged){
                    this.game.setOverarchingLives(this.game.getOverarchingLives() - 1);
                    this.livesChaged = true;
                }
                loseScreen.getToDraw().forEach(window::draw);
                checkMouse(loseScreen);
            } else {
                battleWon = true;
                WinLoseScreen winScreen = new WinLoseScreen(true);
                winScreen.getToDraw().forEach(window::draw);
                checkMouse(winScreen);
            }
        }
    }

    private void collideProjectile(Projectile p){
        if(((int)System.currentTimeMillis() - collideTime > 3000)){
            invincible = false;
        }
        if((p.getGlobalBounds().intersection(wizard.getGlobalBounds()) != null) && !invincible){
            lives-=1;
            collideTime = (int) System.currentTimeMillis();
            invincible = true;
            if(this.battleWindow.getHealthCircles().size() > 0) {
                this.battleWindow.getToDraw().remove(this.battleWindow.getHealthCircles().get(this.battleWindow.getHealthCircles().size() - 1));
                this.battleWindow.getHealthCircles().remove(this.battleWindow.getHealthCircles().size() - 1);
            }
            if(lives == 0){
                wonOrLost = true;
            }
        }
    }

    public void handleInput(KeyEvent event) {
        handleWizardMovement();
        if(event.type == Event.Type.KEY_PRESSED){
            if (event.key == Keyboard.Key.UP) {
                keyPresses.put("UP", true);
            }
            if (event.key == Keyboard.Key.LEFT) {
                keyPresses.put("LEFT", true);
            }
            if (event.key == Keyboard.Key.DOWN) {
                keyPresses.put("DOWN", true);
            }
            if (event.key == Keyboard.Key.RIGHT) {
                keyPresses.put("RIGHT", true);
            }
        }
        if(event.type == Event.Type.KEY_RELEASED){
            if (event.key == Keyboard.Key.UP) {
                keyPresses.put("UP", false);
            }
            if (event.key == Keyboard.Key.LEFT) {
                keyPresses.put("LEFT", false);
            }
            if (event.key == Keyboard.Key.DOWN) {
                keyPresses.put("DOWN", false);
            }
            if (event.key == Keyboard.Key.RIGHT) {
                keyPresses.put("RIGHT", false);
            }
        }
    }

    private void checkMouse(WinLoseScreen winLoseScreen){
        if(this.mouseButtonclicked){
            if(mousePosition.x <= winLoseScreen.getExitButton().getPosition().x + winLoseScreen.getExitButton().getSize().x && mousePosition.x >= winLoseScreen.getPosition().x &&
                    mousePosition.y <= winLoseScreen.getExitButton().getPosition().y + winLoseScreen.getExitButton().getSize().y && mousePosition.y >= winLoseScreen.getExitButton().getPosition().y){
                    attemptedToClose=true;
            }
            this.mouseButtonclicked = false;
        }
    }


    private void handleWizardMovement(){
        if(keyPresses.get("UP") && wizard.getPosition().y >= this.battleWindow.getPlayerArea().getPosition().y){
            this.wizard.setPosition(this.wizard.getPosition().x, this.wizard.getPosition().y - 3);
            if(keyPresses.get("LEFT")){
                walkLeft();
            }
            else if(keyPresses.get("RIGHT")){
                walkRight();
            }
            else{
                walkBack();
            }
            wizard.move(0, -(walkingPace));
        }
        if(keyPresses.get("LEFT") && wizard.getPosition().x >= this.battleWindow.getPlayerArea().getPosition().x){
            // this.wizard.setPosition(this.wizard.getPosition().x - 3, this.wizard.getPosition().y);
            this.walkLeft();
            wizard.move(-(walkingPace), 0);
        }
        if(keyPresses.get("DOWN") && wizard.getPosition().y <= this.battleWindow.getPlayerArea().getPosition().y + this.battleWindow.getPlayerArea().getSize().y - wizard.getGlobalBounds().height){
            // this.wizard.setPosition(this.wizard.getPosition().x, this.wizard.getPosition().y + 3);
            if(keyPresses.get("LEFT")){
                walkLeft();
            }
            else if(keyPresses.get("RIGHT")){
                walkRight();
            }
            else{
                walkForward();
            }
            wizard.move(0, (walkingPace));
        }
        if(keyPresses.get("RIGHT") && wizard.getPosition().x <= this.battleWindow.getPlayerArea().getPosition().x + this.battleWindow.getPlayerArea().getSize().x - wizard.getGlobalBounds().width){
            // this.wizard.setPosition(this.wizard.getPosition().x + 3, this.wizard.getPosition().y);
            this.walkRight();
            wizard.move(walkingPace, 0);
        }
    }
    private void walkLeft(){
        wizard.setTexture(TextureType.getLeftTextureByIndex(stepIndex));
        walkFrameControl++;
        if(walkFrameControl%16 == 0){
            stepIndex++;
            walkFrameControl=0;
        }
        if(stepIndex%7 == 0){
            stepIndex=0;
        }
    }

    private void walkRight(){
        wizard.setTexture(TextureType.getRightTextureByIndex(stepIndex));
        walkFrameControl++;
        if(walkFrameControl%16 == 0){
            stepIndex++;
            walkFrameControl=0;
        }
        if(stepIndex%7 == 0){
            stepIndex=0;
        }
    }

    private void walkBack(){
        if(stepIndex>3){
            stepIndex=0;
        }
        wizard.setTexture(TextureType.getBackTextureByIndex(stepIndex));
        walkFrameControl++;
        if(walkFrameControl%16 == 0){
            stepIndex++;
            walkFrameControl=0;
        }
        if(stepIndex%3 == 0){
            stepIndex=0;
        }
    }

    private void walkForward(){
        if(stepIndex>3){
            stepIndex=0;
        }
        wizard.setTexture(TextureType.getFrontTextureByIndex(stepIndex));
        walkFrameControl++;
        if(walkFrameControl%16 == 0){
            stepIndex++;
            walkFrameControl=0;
        }
        if(stepIndex%3 == 0){
            stepIndex=0;
        }
    }


    public void setTextContent(String newText){
        this.battleWindow.setBattleText(newText);
    }

    public Boolean isFinishedDialogue(){
        return finishedDialogue;
    }

    public void setFinishedDialogue(Boolean isFinished){
        finishedDialogue=isFinished;
    }

    public void setMouseButtonclicked(boolean mouseButtonclicked) {
        this.mouseButtonclicked = mouseButtonclicked;
    }

    public void setMousePosition(Vector2f mousePosition) {
        this.mousePosition = mousePosition;
    }

    public Boolean attemptedToClose(){
        return attemptedToClose;
    }
}