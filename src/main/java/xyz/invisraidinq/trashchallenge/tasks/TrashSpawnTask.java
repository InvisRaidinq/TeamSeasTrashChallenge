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

                //Cache all possible locations so we can randomly get from the map
                final List<Location> possibleLocations = new ArrayList<>();
                for (int x = location.getBlockX() - this.radius; x < location.getBlockX() + this.radius; x++) {
                    for (int y = location.getBlockY() - 20; y < location.getBlockY() + 20; y++) {
                        for (int z = location.getBlockZ() - this.radius; z < location.getBlockZ() + this.radius; x++) {
                            possibleLocations.add(new Location(location.getWorld(), x, y, z));
                        }
                    }
                }

                //Loop through until either the list is empty or all the trash has been spawned
                AtomicInteger x = new AtomicInteger();
                while (x.get() <= this.trashSpawnPerPlayer) {
                    if (possibleLocations.isEmpty()) { //In case we run out of locations (unlikely)
                        break;
                    }

                    Location spawnLocation = possibleLocations.get(ThreadLocalRandom.current().nextInt(possibleLocations.size()));

                    Bukkit.getScheduler().runTask(this.plugin, () -> {
                        if(spawnLocation.getBlock().getType() == Material.WATER) {
                            ItemStack item = this.plugin.getTrashManager().getTrashItem();
                            item.setAmount(ThreadLocalRandom.current().nextInt(this.minTrash, this.maxTrash));
                            spawnLocation.getWorld().dropItemNaturally(spawnLocation, item);
                            x.getAndIncrement();
                        }
                    });

                    possibleLocations.remove(spawnLocation);
                }
            }
        }
    }
}
