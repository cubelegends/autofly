package com.henningstorck.autofly.config;

import com.henningstorck.autofly.conditions.Condition;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ConfigCondition implements Condition {
	private final Config config;

	public ConfigCondition(Config config) {
		this.config = config;
	}

	@Override
	public Optional<Boolean> process(GameMode gameMode, Player player) {
		if (gameMode != GameMode.SURVIVAL) {
			return Optional.empty();
		}

		return Optional.of(config.getWorlds().contains(player.getWorld().getName()));
	}
}
