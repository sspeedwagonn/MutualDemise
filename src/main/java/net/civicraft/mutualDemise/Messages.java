package net.civicraft.mutualDemise;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class Messages {
    //colors
    public static final TextColor PRIMARY = TextColor.color(223, 42, 37);
    public static final TextColor SECONDARY = TextColor.color(219, 183, 183);

    //prefix
    public static final Component PREFIX = Component.text("[MutualDemise] ").color(PRIMARY);

    // plugin messages
    public static final Component CONFIG_RELOADED = PREFIX.append(Component.text("Config.yml has been reloaded!").color(SECONDARY));
    public static final Component NOT_FOUND = PREFIX.append(Component.text("Player not found!").color(SECONDARY));
    public static final Component NOT_PLAYER = PREFIX.append(Component.text("Only players can use this command.").color(SECONDARY));
    public static final Component NO_PERMISSION = PREFIX.append(Component.text("You do not have permission to use this command.").color(SECONDARY));

    // Usage
    public static final Component USAGE_ALL = PREFIX.append(Component.text("Usage: /mutualdemise <add|remove|reload> <player>").color(SECONDARY));
    public static final Component USAGE_REMOVE = PREFIX.append(Component.text("Usage: /mutualdemise remove <player>").color(SECONDARY));
    public static final Component USAGE_ADD = PREFIX.append(Component.text("Usage: /mutualdemise add <player>").color(SECONDARY));


    // Player messages
    public static Component PLAYER_ADDED(Player target) {
        return PREFIX.append(Component.text(target.getName() + " has been added to the immune list.").color(SECONDARY));
    }

    public static Component PLAYER_REMOVED(Player target) {
        return PREFIX.append(Component.text(target.getName() + " has been removed from the immune list.").color(SECONDARY));
    }

    public static Component NOT_IMMUNE(Player target) {
        return PREFIX.append(Component.text(target.getName() + " is not immune!").color(SECONDARY));
    }

    public static Component ALREADY_IMMUNE(Player target) {
        return PREFIX.append(Component.text(target.getName() + " is already immune!").color(SECONDARY));
    }
}
