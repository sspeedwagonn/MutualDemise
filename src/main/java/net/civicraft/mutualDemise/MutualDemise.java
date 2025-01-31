package net.civicraft.mutualDemise;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class MutualDemise extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        getLogger().info("Enabling MutualDemise");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("mutualdemise")).setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling MutualDemise");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getPlayer();
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
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

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
            if (strings.length > 0 && strings[0].equalsIgnoreCase("reload")) {
                if (commandSender.hasPermission("mutualdemise.reload")) {
                    reloadConfig();
                    commandSender.sendMessage(Component.text("[MutualDemise] config.yml has been reloaded! ☠").color(NamedTextColor.DARK_RED));
                    return true;
                } else {
                    commandSender.sendMessage(Component.text("[MutualDemise] You do not have permission to use this command. ☠").color(NamedTextColor.DARK_RED));
                    return false;
                }
            }
        return false;
    }
}