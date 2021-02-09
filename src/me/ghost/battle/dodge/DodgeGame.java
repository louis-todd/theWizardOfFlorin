package me.ghost.battle.dodge;

import me.ghost.Characters.MoveableCharacter;
import me.ghost.Characters.Npc;
import me.ghost.ResourceEnum.TextureType;
import me.ghost.battle.BattleWindow;
import org.jsfml.graphics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class DodgeGame {

    private final BattleWindow battleWindow = new BattleWindow();

    private Npc battleNpc;
    private Stack<Projectile> projectileStack = new Stack<>();
    private MoveableCharacter wizard;
    private Stack<Projectile> projectileInMotion = new Stack<>();
    private boolean battleOpen = true;

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
            Projectile push = projectileStack.push(new Projectile(4, rand.nextInt(maxSides - minSides + 1)));

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
}
