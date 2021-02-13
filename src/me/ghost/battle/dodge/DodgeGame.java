package me.ghost.battle.dodge;

import me.ghost.character.MoveableCharacter;
import me.ghost.character.Npc;
import me.ghost.data.TextureType;
import me.ghost.battle.BattleWindow;
import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.Random;
import java.util.Stack;
import java.util.Timer;

public class DodgeGame {

    private final BattleWindow battleWindow = new BattleWindow();

    private final Npc battleNpc;
    private final Stack<Projectile> projectileStack = new Stack<>();
    private final MoveableCharacter wizard;
    private final Stack<Projectile> projectileInMotion = new Stack<>();
    private final boolean battleOpen = true;
    private int lives = 3;
    private boolean invincible = false;
    private int cooldown;

    public DodgeGame(Npc setBattleNpc) {
        this.battleNpc = new Npc(setBattleNpc.getName(), battleWindow.getGhostAreaCentre().x - 16, battleWindow.getGhostAreaCentre().y - 16, (Texture) setBattleNpc.getTexture());
        this.wizard = new MoveableCharacter("Wizard", battleWindow.getPlayerAreaCentre().x - 16, battleWindow.getPlayerAreaCentre().y - 16, TextureType.SQUARE16.getTexture());
        this.addProjectilesToStack(1000);

        this.battleWindow.getToDraw().add(this.battleNpc);
        this.battleWindow.getToDraw().add(this.wizard);

        this.throwObject();
    }

    private void addProjectilesToStack(int numberProjectiles){
        Random rand = new Random();
        int minSides = 0;
        int maxSides = 10;
        for(int i = 0; i < numberProjectiles; i++){
            Projectile push = projectileStack.push(new Projectile(6, rand.nextInt(maxSides - minSides + 1)));

            push.thrown(this.battleNpc);
        }
    }

    private void throwObject() {
        this.projectileInMotion.addAll(this.projectileStack);
        this.projectileStack.clear();

        for (Projectile projectile : this.projectileInMotion) {
            this.battleWindow.getToDraw().add(projectile);
        }
    }

    public void draw(RenderWindow window) {
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
            cooldown = 10000;
        }
    }

    public void handleInput(KeyEvent event) {

        for(Projectile p : projectileInMotion){
            collideProjectile(p);
        }
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
