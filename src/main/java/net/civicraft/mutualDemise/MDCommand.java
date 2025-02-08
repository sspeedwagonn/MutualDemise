package net.civicraft.mutualDemise;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MDCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Messages.NOT_PLAYER);
            return true;
        }

        if (!player.hasPermission("mutualdemise.admin")) {
            player.sendMessage(Messages.NO_PERMISSION);
            return true;
        }

        if (strings.length < 1) {
            player.sendMessage(Messages.USAGE_ALL);
            return true;
        }

        MutualDemise instance = MutualDemise.getInstance();
        String subCmd = strings[0].toLowerCase();

        switch (subCmd) {
            case "add":
                handleAdd(player, strings, instance);
                break;
            case "remove":
                handleRemove(player, strings, instance);
                break;
            case "reload":
                handleReload(player, instance);
                break;
            default:
                player.sendMessage(Messages.USAGE_ALL);
                break;
        }
        return true;
    }

    private void handleAdd(Player player, String[] strings, MutualDemise instance) {
        if (strings.length != 2) {
            player.sendMessage(Messages.USAGE_ADD);
            return;
        }

        Player target = Bukkit.getPlayer(strings[1]);
        if (target == null) {
            player.sendMessage(Messages.NOT_FOUND);
            return;
        }

        UUID targetUUID = target.getUniqueId();
        if (instance.getImmuneUUIDs().contains(targetUUID)) {
            player.sendMessage(Messages.ALREADY_IMMUNE(target));
            return;
        }

        instance.getImmuneUUIDs().add(targetUUID);
        instance.getConfig().set("immune_players", instance.getImmuneUUIDs().stream().map(UUID::toString).toList());
        instance.saveConfig();
        player.sendMessage(Messages.PLAYER_ADDED(target));
    }

    private void handleRemove(Player player, String[] strings, MutualDemise instance) {
        if (strings.length != 2) {
            player.sendMessage(Messages.USAGE_REMOVE);
            return;
        }

        Player target = Bukkit.getPlayer(strings[1]);
        if (target == null) {
            player.sendMessage(Messages.NOT_FOUND);
            return;
        }

        UUID targetUUID = target.getUniqueId();
        if (!instance.getImmuneUUIDs().remove(targetUUID)) {
            player.sendMessage(Messages.NOT_IMMUNE(target));
            return;
        }

        instance.getConfig().set("immune_players", instance.getImmuneUUIDs().stream().map(UUID::toString).toList());
        instance.saveConfig();
        player.sendMessage(Messages.PLAYER_REMOVED(target));
    }

    private void handleReload(Player player, MutualDemise instance) {
        instance.reloadConfig();
        instance.reloadImmuneLists();
        player.sendMessage(Messages.CONFIG_RELOADED);
    }
}