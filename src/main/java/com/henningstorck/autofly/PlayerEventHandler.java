package com.henningstorck.autofly;

import com.henningstorck.autofly.conditions.ConditionProcessor;
import com.henningstorck.autofly.config.Config;
import com.henningstorck.autofly.config.ConfigCondition;
import com.henningstorck.autofly.worldguard.WorldGuardFlagCondition;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerEventHandler implements Listener {
	public static final int DELAY_TICKS = 20;

	private final JavaPlugin plugin;
	private final ConditionProcessor conditionProcessor;

	public PlayerEventHandler(JavaPlugin plugin, Config config) {
		this.plugin = plugin;
		this.conditionProcessor = new ConditionProcessor(new WorldGuardFlagCondition(), new ConfigCondition(config));
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		conditionProcessor.process(event.getPlayer().getGameMode(), event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		conditionProcessor.process(event.getPlayer().getGameMode(), event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		// The delay is required as a workaround since the event is triggered before the game mode is changed.
		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			conditionProcessor.process(event.getNewGameMode(), event.getPlayer());
		}, DELAY_TICKS);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		conditionProcessor.process(event.getPlayer().getGameMode(), event.getPlayer());
	}
}
