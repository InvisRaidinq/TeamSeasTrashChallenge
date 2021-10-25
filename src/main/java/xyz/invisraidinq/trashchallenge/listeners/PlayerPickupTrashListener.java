package xyz.invisraidinq.trashchallenge.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import xyz.invisraidinq.trashchallenge.TrashPlugin;
import xyz.invisraidinq.trashchallenge.utils.CC;

public class PlayerPickupTrashListener implements Listener {

    private final TrashPlugin plugin;

    public PlayerPickupTrashListener(TrashPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTrashPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (event.getItem() == null) {
            return;
        }

        if (event.getItem().getItemStack() == null) {
            return;
        }

        ItemStack itemStack = event.getItem().getItemStack();

        if (!itemStack.equals(this.plugin.getTrashManager().getTrashItem())) {
            return;
        }

        player.getInventory().remove(itemStack);
        if (this.plugin.getTrashManager().isActive()) {
            player.sendMessage(CC.colour(this.plugin.getConfigFile().getString("messages.trash-recycled")
                    .replace("%amount%", String.valueOf(itemStack.getAmount()))));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
            this.plugin.getTrashManager().pickupTrash(itemStack.getAmount());
        }

    }
}
