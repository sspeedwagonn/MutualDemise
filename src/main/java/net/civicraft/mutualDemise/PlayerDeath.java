package net.civicraft.mutualDemise;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.Random;

public class PlayerDeath implements Listener {
    MutualDemise instance = MutualDemise.getInstance();
    List<Player> onlinePlayers = instance.onlinePlayers;
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (instance.getConfig().getStringList("enabled_worlds").contains(event.getEntity().getWorld().getName())) {
            Player deadPlayer = event.getPlayer();
            onlinePlayers.remove(deadPlayer);

            if (instance.getConfig().getBoolean("everyone_dies")) {
                for (Player player : onlinePlayers) {
                    player.setHealth(0);
                }
            } else {
                int randomKillCount = instance.getConfig().getInt("random_kill_count", 1);
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
}
