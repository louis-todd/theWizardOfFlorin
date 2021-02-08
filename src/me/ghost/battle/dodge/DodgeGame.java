package me.ghost.battle.dodge;

import me.ghost.Characters.MoveableCharacter;
import me.ghost.Characters.Npc;
import me.ghost.battle.BattleWindow;
import org.jsfml.graphics.ConvexShape;

public class DodgeGame{

    private Npc battleNpc;
    private MoveableCharacter wizard;
    public DodgeGame(Npc setBattleNpc, MoveableCharacter setWizard) {
        BattleWindow battleWindow = new BattleWindow();
        this.battleNpc = setBattleNpc;
        this.battleNpc.setPosition(battleWindow.getGhostAreaCentre());
        this.wizard = setWizard;
        this.wizard.setPosition(battleWindow.getPlayerAreaCentre());

    }
}
