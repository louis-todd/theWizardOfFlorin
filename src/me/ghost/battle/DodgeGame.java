package me.ghost.battle;

import me.ghost.Characters.Npc;

public class DodgeGame{

    private Npc battleNpc;
    public DodgeGame(Npc setBattleNpc) {
        BattleWindow battleWindow = new BattleWindow();
        this.battleNpc = setBattleNpc;
        this.battleNpc.setPosition(battleWindow.getGhostAreaCentre());
    }
}
