package net.civicraft.mutualDemise;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerDeath implements Listener {
    private final MutualDemise instance = MutualDemise.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!isEnabledInWorld(event)) return;
        Player deadPlayer = event.getEntity();
        List<Player> victims = getVictims(deadPlayer);

        if (instance.getConfig().getBoolean("everyone_dies")) {
            killAll(victims);
        } else {
            killRandom(victims);
        }
    }

    private boolean isEnabledInWorld(PlayerDeathEvent event) {
        return instance.getConfig().getStringList("enabled_worlds").contains(event.getEntity().getWorld().getName());
    }

    private List<Player> getVictims(Player deadPlayer) {
        List<Player> players = new ArrayList<>();
        for (Player p : deadPlayer.getServer().getOnlinePlayers()) {
            if (!instance.getImmuneUUIDs().contains(p.getUniqueId()) && p != deadPlayer) {
                players.add(p);
            }
        }
        return players;
    }

    private void killAll(List<Player> players) {
        players.forEach(p -> p.setHealth(0));
    }

    private void killRandom(List<Player> players) {
        int killCount = Math.min(instance.getConfig().getInt("random_kill_count", 1), players.size());

        Random random = new Random();
        List<Player> victims = new ArrayList<>(players);
        for (int i = 0; i < killCount && !victims.isEmpty(); i++) {
            Player target = victims.remove(random.nextInt(victims.size()));
            target.setHealth(0);
        }
    }
}