package com.henningstorck.autofly.conditions;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Optional;

public interface Condition {
	Optional<Boolean> process(GameMode gameMode, Player player);
}
