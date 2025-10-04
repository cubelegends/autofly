package com.henningstorck.autofly.worldguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGuardFlag {
	public static final String FLAG_NAME = "fly";
	public static StateFlag FLAG;

	public static void initialize(JavaPlugin plugin) {
		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

		try {
			StateFlag flag = new StateFlag(FLAG_NAME, false);
			registry.register(flag);
			FLAG = flag;
		} catch (FlagConflictException e) {
			plugin.getLogger().info("WorldGuard flag is already used by another plugin.");
			Flag<?> existing = registry.get(FLAG_NAME);

			if (existing instanceof StateFlag) {
				FLAG = (StateFlag) existing;
			} else {
				plugin.getLogger().info("WorldGuard flag type is incompatible.");
			}
		}
	}
}
