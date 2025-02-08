package net.civicraft.mutualDemise;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public final class MutualDemise extends JavaPlugin {
    public static MutualDemise instance;
    private List<UUID> immuneUUIDs;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Enabling MutualDemise...");
        saveDefaultConfig();
        reloadImmuneLists();
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        Objects.requireNonNull(getCommand("mutualdemise")).setExecutor(new MDCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("Disabling MutualDemise...");
    }

    public static MutualDemise getInstance() {
        return instance;
    }

    public void reloadImmuneLists() {
        List<String> uuidStrings = getConfig().getStringList("immune_players");
        immuneUUIDs = uuidStrings.stream()
                .map(UUID::fromString)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<UUID> getImmuneUUIDs() {
        return immuneUUIDs;
    }
}