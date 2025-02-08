package net.civicraft.mutualDemise;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class MDCommand implements CommandExecutor {
    MutualDemise instance = MutualDemise.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length < 1) {
                player.sendMessage(Component.text("[MutualDemise] Usage: /md <add|remove|reload> [player]").color(NamedTextColor.DARK_RED));
                return true;
            }
            FileConfiguration config = instance.getConfig();
            List<String> uuidStrings = instance.uuidStrings;
            List<UUID> immuneUUIDs = instance.immuneUUIDs;

            switch (strings[0].toLowerCase()) {
                case "add":
                    if (strings.length != 2) {
                        player.sendMessage(Component.text("[MutualDemise] Usage: /md add <player>").color(NamedTextColor.DARK_RED));
                        return true;
                    }
                    Player target = Bukkit.getPlayer(strings[1]);
                    if (target == null) {
                        player.sendMessage(Component.text("[MutualDemise] Player not found or offline.").color(NamedTextColor.DARK_RED));
                        return true;
                    }
                    UUID targetUUID = target.getUniqueId();
                    if (immuneUUIDs.contains(targetUUID)) {
                        player.sendMessage(Component.text("[MutualDemise] That player is already immune.").color(NamedTextColor.DARK_RED));
                        return true;
                    }
                    uuidStrings.add(targetUUID.toString());
                    config.set("immune_players", uuidStrings);
                    instance.saveConfig();
                    immuneUUIDs.add(targetUUID);
                    player.sendMessage(Component.text("[MutualDemise] " + target.getName() + " has been added to the immune list.").color(NamedTextColor.DARK_RED));
                    break;

                case "remove":
                    if (strings.length != 2) {
                        player.sendMessage(Component.text("[MutualDemise] Usage: /md remove <player>").color(NamedTextColor.DARK_RED));
                        return true;
                    }
                    Player removeTarget = Bukkit.getPlayer(strings[1]);
                    if (removeTarget == null) {
                        player.sendMessage(Component.text("[MutualDemise] Player not found or offline.").color(NamedTextColor.DARK_RED));
                        return true;
                    }
                    UUID removeUUID = removeTarget.getUniqueId();
                    if (!immuneUUIDs.contains(removeUUID)) {
                        player.sendMessage(Component.text("[MutualDemise] That player is not immune.").color(NamedTextColor.DARK_RED));
                        return true;
                    }
                    immuneUUIDs.remove(removeUUID);
                    config.set("immunePlayers", immuneUUIDs);
                    instance.saveConfig();
                    player.sendMessage(Component.text("[MutualDemise]" + removeTarget.getName() + " has been removed from the immune list.").color(NamedTextColor.DARK_RED));
                    break;
                case "reload":
                    instance.reloadConfig();
                    player.sendMessage(Component.text("[MutualDemise] Configuration reloaded successfully.").color(NamedTextColor.DARK_RED));
                    break;
                default:
                    player.sendMessage(Component.text("[MutualDemise] Invalid option! Use: /mutualdemise <add|remove|reload>").color(NamedTextColor.DARK_RED));
                    break;
            }
        }

        return true;
    }
}
