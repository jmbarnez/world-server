// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.game.player.content;

import com.rs.game.Entity;
import com.rs.game.player.Player;
import com.rs.lib.Constants;
import com.rs.lib.game.SpotAnim;

public enum Effect {
	SKULL {
		@Override
		public void apply(Entity entity) {
			if (entity instanceof Player player)
				player.getAppearance().generateAppearanceData();
		}
		
		@Override
		public void expire(Entity entity) {
			if (entity instanceof Player player)
				player.getAppearance().generateAppearanceData();
		}
	},
	ANTIPOISON("poison immunity") {
		@Override
		public void apply(Entity player) {
			player.getPoison().reset();
		}
	},
	STUN,
	FREEZE,
	FREEZE_BLOCK,
	TELEBLOCK("teleblock"),
	DOUBLE_XP("double xp"),
	ANTIFIRE("antifire"),
	SUPER_ANTIFIRE("super-antifire"),
	PRAYER_RENEWAL("prayer renewal") {
		@Override
		public void tick(Entity entity, long tickNumber) {
			if (entity instanceof Player player) {
				if (!player.getPrayer().hasFullPoints()) {
					player.getPrayer().restorePrayer((((double) (player.getSkills().getLevelForXp(Constants.PRAYER) * 4.3) / 600.0)) + 0.2);
					if (tickNumber % 25 == 0)
						player.setNextSpotAnim(new SpotAnim(1295));
				}
			}
		}
	},
	JUJU_MINING("juju mining potion"),
	JUJU_MINE_BANK,
	JUJU_WOODCUTTING("juju woodcutting potion"),
	JUJU_WC_BANK,
	JUJU_FARMING("juju farming potion"),
	JUJU_FISHING("juju fishing potion"),
	SCENTLESS("scentless potion"),
	JUJU_HUNTER("juju hunter potion"),
	SARA_BLESSING("Saradomin's blessing"),
	GUTHIX_GIFT("Guthix's gift"),
	ZAMMY_FAVOR("Zamorak's favour"),
	
	BONFIRE("bonfire boost") {
		@Override
		public void apply(Entity entity) {
			if (entity instanceof Player player)
				player.getEquipment().refreshConfigs(false);
		}
		
		@Override
		public void expire(Entity entity) {
			if (entity instanceof Player player)
				player.getEquipment().refreshConfigs(false);
		}
	},
	
	OVERLOAD("overload") {
		@Override
		public void apply(Entity entity) {
			if (entity instanceof Player player)
				Potions.applyOverLoadEffect(player);
		}
		
		@Override
		public void tick(Entity entity, long tick) {
			if (tick % 25 == 0 && entity instanceof Player player)
				Potions.applyOverLoadEffect(player);
		}
		
		@Override
		public void expire(Entity entity) {
			if (entity instanceof Player player) {
				if (!player.isDead()) {
					int actualLevel = player.getSkills().getLevel(Constants.ATTACK);
					int realLevel = player.getSkills().getLevelForXp(Constants.ATTACK);
					if (actualLevel > realLevel)
						player.getSkills().set(Constants.ATTACK, realLevel);
					actualLevel = player.getSkills().getLevel(Constants.STRENGTH);
					realLevel = player.getSkills().getLevelForXp(Constants.STRENGTH);
					if (actualLevel > realLevel)
						player.getSkills().set(Constants.STRENGTH, realLevel);
					actualLevel = player.getSkills().getLevel(Constants.DEFENSE);
					realLevel = player.getSkills().getLevelForXp(Constants.DEFENSE);
					if (actualLevel > realLevel)
						player.getSkills().set(Constants.DEFENSE, realLevel);
					actualLevel = player.getSkills().getLevel(Constants.MAGIC);
					realLevel = player.getSkills().getLevelForXp(Constants.MAGIC);
					if (actualLevel > realLevel)
						player.getSkills().set(Constants.MAGIC, realLevel);
					actualLevel = player.getSkills().getLevel(Constants.RANGE);
					realLevel = player.getSkills().getLevelForXp(Constants.RANGE);
					if (actualLevel > realLevel)
						player.getSkills().set(Constants.RANGE, realLevel);
					player.heal(500);
				}
				player.sendMessage("<col=480000>The effects of overload have worn off and you feel normal again.");
			}
		}
	},
	
	;
	
	private String name;
	
	private Effect(String name) {
		this.name = name;
	}
	
	private Effect() {
		name = null;
	}
	
	public void apply(Entity player) {
		
	}
	
	public void tick(Entity player, long tickNumber) {
		
	}
	
	public void expire(Entity player) {
		
	}
	
	public boolean sendWarnings() {
		return name != null;
	}
	
	public String get30SecWarning() {
		return "<col=FF0000>Your " + name + " is going to run out in 30 seconds!";
	}

	public String getExpiryMessage() {
		return "<col=FF0000>Your " + name + " has run out!";
	}

}
