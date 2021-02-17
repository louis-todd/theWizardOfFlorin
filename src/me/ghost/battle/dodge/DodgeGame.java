package me.ghost.battle.dodge;

import me.ghost.CaseInsensitiveMap;
import me.ghost.Dialogue;
import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import me.ghost.data.FontType;
import me.ghost.data.TextureType;
import me.ghost.battle.BattleWindow;
import org.jsfml.graphics.*;
import org.jsfml.graphics.Font;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.awt.*;
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
    private final List<Stack<Projectile>> levels = new ArrayList<>();
    private int maxLevel;
    private int currentLevel = 0;
    private boolean projectilesOnScreen = false;
    private boolean projectileStillOnScreen = false;
    private int collideTime;
    private boolean dialogueFinished = false;
    private final Map<String, Boolean> keyPresses = new CaseInsensitiveMap<>();

    public DodgeGame(Npc setBattleNpc, String difficulty) {
        this.battleNpc = new Npc(setBattleNpc.getName(), battleWindow.getGhostAreaCentre().x - 16, battleWindow.getGhostAreaCentre().y - 80, (Texture) setBattleNpc.getTexture());
        this.wizard = new MoveableCharacter("Wizard", battleWindow.getPlayerAreaCentre().x - 16, battleWindow.getPlayerAreaCentre().y - 16, TextureType.SQUARE16.getTexture());
        this.addProjectilesToStack(1000);

        this.battleWindow.getToDraw().add(this.battleNpc);
        this.battleWindow.getToDraw().add(this.wizard);
        this.setDifficulty(difficulty);
        this.initKeyPresses();
    }

    private void initKeyPresses(){
        this.keyPresses.put("W", false);
        this.keyPresses.put("A", false);
        this.keyPresses.put("S", false);
        this.keyPresses.put("D", false);
        this.keyPresses.put("SPACE", false);
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
        if (!dialogueFinished) {
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

                if (lives <= 0) {
                    System.out.println("YOU LOST");
                    battleLost = true;
                } else {
                    System.out.println("YOU WON");
                    battleWon = true;
                }
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
            System.out.println("LIFE LOST");
        }
    }

    public void handleInput(KeyEvent event) {
        handleWizardMovement();
        handleSpace();
        if(event.type == Event.Type.KEY_PRESSED){
            if (event.key == Keyboard.Key.W) {
                keyPresses.put("W", true);
            }
            if (event.key == Keyboard.Key.A) {
                keyPresses.put("A", true);
            }
            if (event.key == Keyboard.Key.S) {
                keyPresses.put("S", true);
            }
            if (event.key == Keyboard.Key.D) {
                keyPresses.put("D", true);
            }
            if(event.key == Keyboard.Key.SPACE){
                keyPresses.put("SPACE", true);
            }
        }
        if(event.type == Event.Type.KEY_RELEASED){
            if (event.key == Keyboard.Key.W) {
                keyPresses.put("W", false);
            }
            if (event.key == Keyboard.Key.A) {
                keyPresses.put("A", false);
            }
            if (event.key == Keyboard.Key.S) {
                keyPresses.put("S", false);
            }
            if (event.key == Keyboard.Key.D) {
                keyPresses.put("D", false);
            }
            if(event.key == Keyboard.Key.SPACE){
                keyPresses.put("SPACE", false);
            }
        }

    }

    private void handleWizardMovement(){
        if(keyPresses.get("W")){
            this.wizard.setPosition(this.wizard.getPosition().x, this.wizard.getPosition().y - 3);
        }
        if(keyPresses.get("A")){
            this.wizard.setPosition(this.wizard.getPosition().x - 3, this.wizard.getPosition().y);
        }
        if(keyPresses.get("S")){
            this.wizard.setPosition(this.wizard.getPosition().x, this.wizard.getPosition().y + 3);
        }
        if(keyPresses.get("D")){
            this.wizard.setPosition(this.wizard.getPosition().x + 3, this.wizard.getPosition().y);
        }

    }

    private void handleSpace() {
        if (keyPresses.get("SPACE")) {
            this.battleWindow.setBattleText("NEW TEXT");
        }
    }

}
