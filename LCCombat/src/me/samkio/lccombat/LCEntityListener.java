package me.samkio.lccombat;

import java.util.HashMap;

import me.samkio.levelcraftcore.LCChat;
import me.samkio.levelcraftcore.LevelFunctions;
import me.samkio.levelcraftcore.Whitelist;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

public class LCEntityListener extends EntityListener {
	public LCCombat plugin;
	public HashMap<Entity, Block> doneBefore = new HashMap<Entity, Block>();

	public LCEntityListener(LCCombat instance) {
		plugin = instance;
	}

	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity() instanceof Player) {
			if (plugin.entListener.doneBefore != null) {
				if (plugin.entListener.doneBefore
						.containsKey(event.getEntity())) {
					plugin.entListener.doneBefore.remove(event.getEntity());
					return;
				} else {
					plugin.entListener.doneBefore.put(event.getEntity(), null);
				}
			} else {

				plugin.entListener.doneBefore.put(event.getEntity(), null);
			}
		}

		if (event instanceof EntityDamageByEntityEvent) {
			if (((EntityDamageByEntityEvent) event).getDamager() instanceof Player
					&& Whitelist.worldCheck(event.getEntity().getWorld())
					&& Whitelist.hasLevel(
							(Player) ((EntityDamageByEntityEvent) event)
									.getDamager(), plugin.thisPlug)) {

				if (!(event instanceof EntityDamageByProjectileEvent)) {

					plugin.entListener
							.onEDamageByE((EntityDamageByEntityEvent) event);
					return;
				}
			}
		}
	}

	public void onEDamageByE(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player))
			return;
		if (event.getEntity() instanceof Player) {
			Player Damager = (Player) ((EntityDamageByEntityEvent) event)
					.getDamager();
			Player Damagee = (Player) event.getEntity();
			if (Damager == Damagee)
				return;
		}
		if (event.getEntity() instanceof Player
				&& plugin.LCConfiguration.pvpRangeEnable) {
			Player Damager = (Player) ((EntityDamageByEntityEvent) event)
					.getDamager();
			Player Damagee = (Player) event.getEntity();
			int DamageeLevel = LevelFunctions
					.getLevel(Damagee, plugin.thisPlug);
			int DamagerLevel = LevelFunctions
					.getLevel(Damager, plugin.thisPlug);
			int difference = Math.abs(DamageeLevel - DamagerLevel);
			if (difference > plugin.LCConfiguration.pvpRange) {
				LCChat.warn(Damager, "You can only attack players within a "
						+ plugin.LCConfiguration.pvpRange
						+ " level diffence in Combat.");
				event.setCancelled(true);
				return;
			}
		}
		Player player = (Player) event.getDamager();
		int iih = player.getItemInHand().getTypeId();
		int level = LevelFunctions.getLevel(player, plugin.thisPlug);
		if (level < plugin.LCConfiguration.IronSword && iih == 267) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ plugin.LCConfiguration.IronSword);
			event.setCancelled(true);
		} else if (level < plugin.LCConfiguration.GoldSword && iih == 283) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ plugin.LCConfiguration.GoldSword);
			event.setCancelled(true);
		} else if (level < plugin.LCConfiguration.DiamondSword && iih == 276) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ plugin.LCConfiguration.DiamondSword);
			event.setCancelled(true);
		} else if (level < plugin.LCConfiguration.StoneSword && iih == 272) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ plugin.LCConfiguration.StoneSword);
			event.setCancelled(true);
		} else if (level < plugin.LCConfiguration.WoodSword && iih == 268) {
			LCChat.warn(player, "Cannot use this tool. Required Level:"
					+ plugin.LCConfiguration.WoodSword);
			event.setCancelled(true);
		} else {

			LevelFunctions.addExp(player, plugin.thisPlug,
					plugin.LCConfiguration.ExpPerDamage * event.getDamage());

		}

	}

}
