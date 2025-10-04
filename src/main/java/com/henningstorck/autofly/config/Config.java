package com.henningstorck.autofly.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Config {
	private final JavaPlugin plugin;
	private final FileConfiguration config;

	public Config(JavaPlugin plugin) {
		this.plugin = plugin;
		this.config = plugin.getConfig();
		this.initialize();
	}

	private void initialize() {
		this.config.addDefault("worlds", new String[]{"world"});
		this.config.options().copyDefaults(true);
		this.plugin.saveConfig();
	}

	public List<String> getWorlds() {
		return this.config.getStringList("worlds");
	}
}
