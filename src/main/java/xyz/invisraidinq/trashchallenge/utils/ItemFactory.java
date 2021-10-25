package xyz.invisraidinq.trashchallenge.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Easily create itemstacks, without messing your hands.
 * <i>Note that if you do use this in one of your projects, leave this notice.</i>
 * <i>Please do credit me if you do use this in one of your projects.</i>
 * @author NonameSL, Adaptations by InvisRaidinq
 */
public class ItemFactory {

    private final ItemStack itemStack;

    /**
     * Create a new {@link ItemFactory} from an existing ItemStack
     *
     * @param itemStack The itemstack to input
     */
    public ItemFactory(ItemStack itemStack) {
        this.itemStack = itemStack;
    }


    /**
     * Create a new {@link ItemFactory}
     *
     * @param material The material to use
     * @param amount The amount of the item
     */
    public ItemFactory(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    /**
     * Create a new {@link ItemFactory}
     *
     * @param material the material to use
     */
    public ItemFactory(Material material) {
        this(material, 1);
    }

    /**
     * Create a new {@link ItemFactory}
     *
     * @param material The material to use
     * @param amount The amount of the item
     * @param durability The durability of the item
     */
    public ItemFactory(Material material, int amount, byte durability) {
        this.itemStack = new ItemStack(material, amount, durability);
    }

    /**
     * Set the durability of the item
     *
     * @param durability The durability to be set
     */
    public ItemFactory setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    /**
     * Set the displayname of the item
     *
     * @param name The name to change it to
     */
    public ItemFactory setName(String name) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(CC.colour(name));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add an unsafe enchantment to the item
     *
     * @param enchantment The enchantment to add
     * @param level The level of the enchant
     */
    public ItemFactory addUnsafeEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Remove an enchant from the item
     *
     * @param enchantment The enchantment to remove
     */
    public ItemFactory removeEnchantment(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Hide the enchantments of an item
     *
     * @param hidden Should the enchantments be hidden
     */
    public ItemFactory hideEnchants(boolean hidden) {
        if (hidden) {
            ItemMeta itemMeta = this.itemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            this.itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    /**
     * Set the other of a player skull
     *
     * @param owner The name of the skull owner
     */
    public ItemFactory setSkullOwner(String owner) {
        try {
            SkullMeta itemMeta = (SkullMeta) this.itemStack.getItemMeta();
            itemMeta.setOwner(owner);
            this.itemStack.setItemMeta(itemMeta);
        } catch (ClassCastException ignored) {

        }
        return this;
    }

    /**
     * Set the lore of an item
     *
     * @param lore The lore to set
     */
    public ItemFactory setLore(List<String> lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(CC.colour(lore));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Set the lore of an item
     *
     * @param lore The lore to set
     */
    public ItemFactory setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Add a lore line
     *
     * @param line The lore line to add
     */
    public ItemFactory addLoreLine(String line) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.add(line);
        itemMeta.setLore(CC.colour(lore));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add a lore line
     *
     * @param line The lore line to add
     * @param index The index of the line
     */
    public ItemFactory addLoreLine(String line, int index) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.set(index, line);
        itemMeta.setLore(CC.colour(lore));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Remove a lore line
     *
     * @param line The lore to remove
     */
    public ItemFactory removeLoreLine(String line) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (!lore.contains(line)) {
            return this;
        }
        lore.remove(line);
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Remove a lore line
     *
     * @param index The index of the lore line to remove
     */
    public ItemFactory removeLoreLine(int index) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (index < 0 || index > lore.size()) {
            return this;
        }
        lore.remove(index);
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add item flags to an item
     *
     * @param flags The flags to add
     */
    public ItemFactory setFlags(ItemFlag...flags) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(flags);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }


    /**
     * Remove flags from an item
     *
     * @param flags The flags to remove
     */
    public ItemFactory removeFlags(ItemFlag...flags) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeItemFlags(flags);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add a glow to the item
     */
    public ItemFactory addGlow() {
        this.itemStack.addUnsafeEnchantment(Enchantment.LURE, 1);
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
     * @param color The color to set it to.
     */
    public ItemFactory setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta itemMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
            itemMeta.setColor(color);
            this.itemStack.setItemMeta(itemMeta);
        } catch (ClassCastException ignored) {

        }
        return this;
    }

    /**
     * Build the {@link ItemFactory} into an {@link ItemStack}
     *
     * @return A built {@link ItemStack} object
     */
    public ItemStack build() {
        return this.itemStack;
    }
}
