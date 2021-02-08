package me.ghost.battle.dodge;

import me.ghost.Characters.MoveableCharacter;
import me.ghost.Characters.Npc;
import me.ghost.battle.BattleWindow;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.Texture;

import java.util.Random;
import java.util.Stack;

public class DodgeGame{

    private Npc battleNpc;
    Stack<Projectile> projectileStack = new Stack<>();
    private MoveableCharacter wizard;

    public DodgeGame(Npc setBattleNpc, MoveableCharacter setWizard) {
        BattleWindow battleWindow = new BattleWindow();
        this.battleNpc = new Npc(setBattleNpc.getName(), battleWindow.getGhostAreaCentre().x, battleWindow.getGhostAreaCentre().y, (Texture) setBattleNpc.getTexture());
        this.wizard = new MoveableCharacter(setWizard.getName(), battleWindow.getPlayerAreaCentre().x, battleWindow.getPlayerAreaCentre().y, (Texture) setWizard.getTexture());
        this.addProjectilesToStack(1000);
    }

    private void addProjectilesToStack(int numberOfProjectiles){
        Random rand = new Random();
        int minSides = 0;
        int maxSides = 10;
        for(int i = 0; i < numberOfProjectiles; i++){
            projectileStack.push(new Projectile(rand.nextInt(maxSides - minSides + 1)));
        }
    }

}
