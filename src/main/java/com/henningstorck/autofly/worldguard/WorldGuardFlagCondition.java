package com.henningstorck.autofly.worldguard;

import com.henningstorck.autofly.conditions.Condition;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Optional;

public class WorldGuardFlagCondition implements Condition {
	@Override
	public Optional<Boolean> process(GameMode gameMode, Player player) {
		if (gameMode != GameMode.SURVIVAL) {
			return Optional.empty();
		}

		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
		Location location = localPlayer.getLocation();
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();
		StateFlag.State state = query.queryState(location, localPlayer, WorldGuardFlag.FLAG);

		if (state == null) {
			return Optional.empty();
		}

		return Optional.of(state == StateFlag.State.ALLOW);
	}
}
