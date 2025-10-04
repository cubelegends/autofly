package com.henningstorck.autofly;

import com.henningstorck.autofly.config.Config;
import com.henningstorck.autofly.worldguard.WorldGuardFlag;
import com.henningstorck.autofly.worldguard.WorldGuardFlagHandler;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.session.SessionManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoFly extends JavaPlugin implements Listener {
	@Override
	public void onLoad() {
		WorldGuardFlag.initialize(this);
	}

	@Override
	public void onEnable() {
		Config config = new Config(this);

		this.getServer().getPluginManager().registerEvents(new PlayerEventHandler(this, config), this);

		SessionManager sessionManager = WorldGuard.getInstance().getPlatform().getSessionManager();
		sessionManager.registerHandler(new WorldGuardFlagHandler.Factory(config), null);
	}
}
