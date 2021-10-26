package xyz.invisraidinq.trashchallenge.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.invisraidinq.trashchallenge.TrashPlugin;
import xyz.invisraidinq.trashchallenge.utils.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

//This class probably isnt' the most efficient way to do it but as it's only supposed to be a quick plugin it should be fine
public class TrashSpawnTask extends BukkitRunnable {

    private final TrashPlugin plugin;

    private final int radius;
    private final int minTrash;
    private final int maxTrash;
    private final int trashSpawnPerPlayer;

    public TrashSpawnTask(TrashPlugin plugin) {
        this.plugin = plugin;

        this.radius = plugin.getConfigFile().getInt("trash-spawn-radius");
        this.minTrash = plugin.getConfigFile().getInt("minimum-trash-spawn");
        this.maxTrash = plugin.getConfigFile().getInt("maximum-trash-spawn");
        this.trashSpawnPerPlayer = plugin.getConfigFile().getInt("trash-spawn-per-player");
    }

    @Override
    public void run() {
        if (this.plugin.getTrashManager().isActive()) {
            Bukkit.broadcastMessage(CC.colour(this.plugin.getConfigFile().getString("messages.next-dumpster")));

            Bukkit.getScheduler().runTask(this.plugin, () -> {
                Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
                    if (entity instanceof Item) {
                        entity.remove();
                    }
                }));
            });

            for (Player player : Bukkit.getOnlinePlayers()) {
                Location location = player.getLocation();

                final List<Location> possibleLocations = new ArrayList<>();

                int attempts = 0;
                int x = 0;
                while (x < this.trashSpawnPerPlayer) {
                    Location possibleLocation = new Location(
                            location.getWorld(),
                            ThreadLocalRandom.current().nextInt(player.getLocation().getBlockX() - this.radius, player.getLocation().getBlockX() + this.radius),
                            ThreadLocalRandom.current().nextInt(player.getLocation().getBlockY() - 10, player.getLocation().getBlockY() + 10),
                            ThreadLocalRandom.current().nextInt(player.getLocation().getBlockZ() - this.radius, player.getLocation().getBlockZ() + this.radius)
                    );

                    if (possibleLocation.getBlock().getType() == Material.WATER) {
                        possibleLocations.add(possibleLocation);
                    }

                    attempts++;
                    if (attempts == 1000) {
                        break; //So the server doesn't die - usually people will be in the sea so it won't be an issue, but some players might be a pain
                    }
                }

                Bukkit.getScheduler().runTask(this.plugin, () -> {
                    possibleLocations.forEach(spawnLocation -> {
                        ItemStack item = this.plugin.getTrashManager().getTrashItem();
                        item.setAmount(ThreadLocalRandom.current().nextInt(this.minTrash, this.maxTrash));
                        spawnLocation.getWorld().dropItemNaturally(spawnLocation, item);
                    });
                });
            }
        }
    }
}
