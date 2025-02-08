package net.civicraft.mutualDemise;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class MutualDemise extends JavaPlugin {
    public static MutualDemise instance;
    List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
    List<String> uuidStrings = getConfig().getStringList("immune_players");
    List<UUID> immuneUUIDs = uuidStrings.stream().map(UUID::fromString).toList();

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Enabling MutualDemise...");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
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

    private void removeImmunePlayers() {
        for (UUID uuid : immuneUUIDs) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                onlinePlayers.remove(player);
            }
        }
    }
}