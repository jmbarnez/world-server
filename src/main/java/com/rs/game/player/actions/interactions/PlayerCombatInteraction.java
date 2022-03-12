package com.rs.game.player.actions.interactions;

import com.rs.game.Entity;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.interactions.PlayerEntityInteractionAction;

public class PlayerCombatInteraction extends PlayerEntityInteractionAction<PlayerCombat> {

	public PlayerCombatInteraction(Player player, Entity target) {
		super(target, new PlayerCombat(target), PlayerCombat.getAttackRange(player));
	}

	@Override
	public boolean canStart(Player player) {
		player.setNextFaceEntity(target);
		return true;
	}

	@Override
	public boolean checkAll(Player player) {
		setDistance(PlayerCombat.getAttackRange(player));
		player.setNextFaceEntity(target);
		return true;
	}
}
