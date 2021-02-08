package me.ghost.battle.dodge;

import me.ghost.Characters.MoveableCharacter;
import me.ghost.Characters.Npc;
import me.ghost.battle.BattleWindow;
import org.jsfml.graphics.ConvexShape;

import java.util.Random;
import java.util.Stack;

public class DodgeGame{

    private Npc battleNpc;
    Stack<Projectile> projectileStack = new Stack<>();
    private MoveableCharacter wizard;
    public DodgeGame(Npc setBattleNpc, MoveableCharacter setWizard) {
        BattleWindow battleWindow = new BattleWindow();
        this.battleNpc = setBattleNpc;
        this.battleNpc.setPosition(battleWindow.getGhostAreaCentre());
        this.wizard = setWizard;
        this.wizard.setPosition(battleWindow.getPlayerAreaCentre());

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
