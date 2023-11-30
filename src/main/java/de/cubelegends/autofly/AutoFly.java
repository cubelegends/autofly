package de.cubelegends.autofly;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoFly extends JavaPlugin implements Listener {
    public static final int DELAY_TICKS = 20;

    private Config config;

    @Override
    public void onEnable() {
        this.config = new Config(this);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        autoEnableFly(event.getPlayer().getGameMode(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        autoEnableFly(event.getPlayer().getGameMode(), event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        // The delay is required as a workaround since the event is triggered before the game mode is changed.
        Bukkit.getScheduler().runTaskLater(this, () -> {
            autoEnableFly(event.getNewGameMode(), event.getPlayer());
        }, DELAY_TICKS);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        autoEnableFly(event.getPlayer().getGameMode(), event.getPlayer());
    }

    private void autoEnableFly(GameMode gameMode, Player player) {
        if (gameMode != GameMode.SURVIVAL) {
            return;
        }

        boolean allowedToFly = config.getWorlds().contains(player.getWorld().getName());
        player.setAllowFlight(allowedToFly);
    }
}
