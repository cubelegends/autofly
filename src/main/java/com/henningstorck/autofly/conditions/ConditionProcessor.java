package com.henningstorck.autofly.conditions;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConditionProcessor {
	private final List<Condition> conditions;

	public ConditionProcessor(Condition... conditions) {
		this.conditions = Arrays.asList(conditions);
	}

	public void process(GameMode gameMode, Player player) {
		for (Condition condition : conditions) {
			Optional<Boolean> result = condition.process(gameMode, player);

			if (result.isPresent()) {
				player.setAllowFlight(result.get());
				return;
			}
		}
	}
}
