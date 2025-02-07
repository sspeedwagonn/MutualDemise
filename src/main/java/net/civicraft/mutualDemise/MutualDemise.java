package net.civicraft.mutualDemise;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class MutualDemise extends JavaPlugin implements Listener, CommandExecutor {
    public static MutualDemise instance;
    List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
    List<String> uuidStrings = getConfig().getStringList("immune_players");
    List<UUID> immuneUUIDs = uuidStrings.stream().map(UUID::fromString).toList();

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Enabling MutualDemise...");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("mutualdemise")).setExecutor(new MDCommand());
        removeImmunePlayers();
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("Disabling MutualDemise...");
    }

    public static MutualDemise getInstance() {
        return instance;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (getConfig().getStringList("enabled_worlds").contains(event.getEntity().getWorld().getName())) {
            Player deadPlayer = event.getPlayer();
            onlinePlayers.remove(deadPlayer);

            if (getConfig().getBoolean("everyone_dies")) {
                for (Player player : onlinePlayers) {
                    player.setHealth(0);
                }
            } else {
                int randomKillCount = getConfig().getInt("random_kill_count", 1);
                randomKillCount = Math.min(randomKillCount, onlinePlayers.size());
                Random random = new Random();
                for (int i = 0; i < randomKillCount; i++) {
                    Player player = onlinePlayers.get(random.nextInt(onlinePlayers.size()));
                    player.setHealth(0);
                    onlinePlayers.remove(player);
                }
            }
        }
    }

    private void removeImmunePlayers() {
        for (UUID uuid : immuneUUIDs) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                onlinePlayers.remove(player);
            }
        }
    }
}