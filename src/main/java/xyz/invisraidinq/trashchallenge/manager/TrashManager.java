package xyz.invisraidinq.trashchallenge.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.invisraidinq.trashchallenge.TrashPlugin;
import xyz.invisraidinq.trashchallenge.utils.ConfigFile;
import xyz.invisraidinq.trashchallenge.utils.ItemFactory;

public class TrashManager {

    private final TrashPlugin plugin;

    private boolean active = true;

    private final ItemStack trashItem;

    private int trashPickedUp;
    private double moneyDonated;
    private final double moneyPerItem;

    public TrashManager(TrashPlugin plugin) {
        this.plugin = plugin;

        ConfigFile config = plugin.getConfigFile();
        this.trashItem = new ItemFactory(Material.valueOf(config.getString("trash-item.material")))
                .setName(config.getString("trash-item.name"))
                .setLore(config.getStringList("trash-item.lore"))
                .build();

        this.moneyPerItem = this.plugin.getConfigFile().getDouble("money-per-item");
    }

    public boolean isActive() {
        return this.active;
    }

    public void stopTrash() {
        this.active = false;
    }

    public void startTrash() {
        this.active = true;
    }

    public ItemStack getTrashItem() {
        return this.trashItem;
    }

    public int getTrashPickedUp() {
        return this.trashPickedUp;
    }

    public void setTrashPickedUp(int trashPickedUp) {
        this.trashPickedUp = trashPickedUp;
    }

    public void pickupTrash(int amount) {
        this.trashPickedUp = this.trashPickedUp + amount;
        this.moneyDonated = this.moneyDonated + (this.moneyPerItem * amount);
    }

    public double getMoneyDonated() {
        return this.moneyDonated;
    }

    public void setMoneyDonated(double moneyDonated) {
        this.moneyDonated = moneyDonated;
    }
}
