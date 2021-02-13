package me.ghost.battle.dodge;

import me.ghost.CaseInsensitiveMap;
import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import me.ghost.data.TextureType;
import me.ghost.battle.BattleWindow;
import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class DodgeGame {

    private final BattleWindow battleWindow = new BattleWindow();

    private final Npc battleNpc;
    //private final Stack<Projectile> projectileStack = new Stack<>();
    private final MoveableCharacter wizard;
    private final Stack<Projectile> projectileInMotion = new Stack<>();
    private final boolean battleOpen = true;
    private int lives = 3;
    private boolean invincible = false;
    private int cooldown;
    private boolean battleWon = false;
    private boolean battleLost = false;
    private List<Stack<Projectile>> levels = new ArrayList<>();

    public DodgeGame(Npc setBattleNpc, String difficulty) {
        this.battleNpc = new Npc(setBattleNpc.getName(), battleWindow.getGhostAreaCentre().x - 16, battleWindow.getGhostAreaCentre().y - 16, (Texture) setBattleNpc.getTexture());
        this.wizard = new MoveableCharacter("Wizard", battleWindow.getPlayerAreaCentre().x - 16, battleWindow.getPlayerAreaCentre().y - 16, TextureType.SQUARE16.getTexture());
        this.addProjectilesToStack(1000);

        this.battleWindow.getToDraw().add(this.battleNpc);
        this.battleWindow.getToDraw().add(this.wizard);
        this.setDifficulty(difficulty);
        this.throwObject();
    }

    private Stack<Projectile> addProjectilesToStack(int numberProjectiles){
        int minSides = 0;
        int maxSides = 10;
        Stack<Projectile> projectileStack = new Stack<>();
        for(int i = 0; i < numberProjectiles; i++){
            Projectile push = projectileStack.push(new Projectile(6, ThreadLocalRandom.current().nextInt(maxSides - minSides + 1)));

            push.thrown(this.battleNpc);
        }
        return projectileStack;
    }

    private void setDifficulty(String difficulty){

        switch (difficulty) {
            case "EASY":
                for (int i = 1; i <= 4; i++) {
                    this.levels.add(addProjectilesToStack(i * 100));
                }
                break;
            case "INTERMEDIATE":
                for (int i = 1; i <= 5; i++) {
                    this.levels.add(addProjectilesToStack(i * 200));
                }
                break;
            case "HARD":
                for (int i = 1; i <= 6; i++) {
                    this.levels.add(addProjectilesToStack(i * 400));
                }
                break;
        }
    }

    private void throwObject() {
        this.projectileInMotion.addAll(this.levels.get(0));
        this.levels.get(0).clear();

        for (Projectile projectile : this.projectileInMotion) {
            this.battleWindow.getToDraw().add(projectile);
        }
    }

    public void draw(RenderWindow window) {
        for(Projectile p : projectileInMotion){

            collideProjectile(p);
        }
        this.battleWindow.getToDraw().forEach(window::draw);
        this.projectileInMotion.forEach(Projectile::applyVelocity);
    }

    private void collideProjectile(Projectile p){
        if(cooldown > 0){
            cooldown-=1;
        } else {
            invincible = false;
        }
        if((p.getGlobalBounds().intersection(wizard.getGlobalBounds()) != null) && !invincible){
            lives-=1;
            invincible = true;
            cooldown = 100000;
            if(lives == 0){
                battleLost = true;
                System.out.println("BATTLE LOST");
            }
        }
    }

    public void handleInput(KeyEvent event) {
        if(event.type == Event.Type.KEY_PRESSED){
            if (event.key == Keyboard.Key.W) {
                this.wizard.setPosition(this.wizard.getPosition().x, this.wizard.getPosition().y - 3);
            }
            if (event.key == Keyboard.Key.A) {
                this.wizard.setPosition(this.wizard.getPosition().x - 3, this.wizard.getPosition().y);
            }
            if (event.key == Keyboard.Key.S) {
                this.wizard.setPosition(this.wizard.getPosition().x, this.wizard.getPosition().y + 3);
            }
            if (event.key == Keyboard.Key.D) {
                this.wizard.setPosition(this.wizard.getPosition().x + 3, this.wizard.getPosition().y);
            }
        }
    }

}
