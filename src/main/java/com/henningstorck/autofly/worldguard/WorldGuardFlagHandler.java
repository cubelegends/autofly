package com.henningstorck.autofly.worldguard;

import com.henningstorck.autofly.conditions.ConditionProcessor;
import com.henningstorck.autofly.config.Config;
import com.henningstorck.autofly.config.ConfigCondition;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.FlagValueChangeHandler;
import com.sk89q.worldguard.session.handler.Handler;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldGuardFlagHandler extends FlagValueChangeHandler<StateFlag.State> {
	public static final int NO_DAMAGE_TICKS = 20 * 10;

	private final ConditionProcessor conditionProcessor;

	public static class Factory extends Handler.Factory<WorldGuardFlagHandler> {
		private final Config config;

		public Factory(Config config) {
			this.config = config;
		}

		@Override
		public WorldGuardFlagHandler create(Session session) {
			return new WorldGuardFlagHandler(config, session);
		}
	}

	public WorldGuardFlagHandler(Config config, Session session) {
		super(session, WorldGuardFlag.FLAG);
		this.conditionProcessor = new ConditionProcessor(new ConfigCondition(config));
	}

	@Override
	protected void onInitialValue(LocalPlayer localPlayer, ApplicableRegionSet set, StateFlag.State value) {
		// Do nothing
	}

	@Override
	protected boolean onSetValue(LocalPlayer localPlayer, Location from, Location to, ApplicableRegionSet toSet, StateFlag.State currentValue, StateFlag.State lastValue, MoveType moveType) {
		handleFlagValueChange(localPlayer, currentValue);
		return true;
	}

	@Override
	protected boolean onAbsentValue(LocalPlayer localPlayer, Location from, Location to, ApplicableRegionSet toSet, StateFlag.State lastValue, MoveType moveType) {
		handleFlagValueChange(localPlayer, null);
		return true;
	}

	private void handleFlagValueChange(LocalPlayer localPlayer, StateFlag.State currentValue) {
		CommandSender commandSender = WorldGuardPlugin.inst().unwrapActor(localPlayer);

		if (!(commandSender instanceof Player player)) {
			return;
		}

		GameMode gameMode = player.getGameMode();

		if (gameMode != GameMode.SURVIVAL) {
			return;
		}

		if (currentValue == null) {
			conditionProcessor.process(player.getGameMode(), player);
		} else {
			boolean flyAllowed = currentValue == StateFlag.State.ALLOW;

			if (!flyAllowed && player.isFlying()) {
				player.setNoDamageTicks(NO_DAMAGE_TICKS);
			}

			player.setAllowFlight(flyAllowed);
		}
	}
}
