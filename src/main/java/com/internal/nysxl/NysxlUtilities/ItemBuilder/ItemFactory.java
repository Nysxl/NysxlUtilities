package com.internal.nysxl.NysxlUtilities.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A utility class for building and modifying ItemStacks with fluent API design.
 */
public class ItemFactory {

    private ItemStack itemStack;
    private ItemMeta meta;

    private final Map<TriggerTypes, List<BiConsumer<Player, Object>>> effects = new HashMap<>();

    /**
     * Creates an instance of ItemFactory based on an existing ItemStack.
     *
     * @param itemStack The ItemStack to modify or build upon.
     */
    public ItemFactory(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.meta = itemStack.getItemMeta(); // Automatically fetch ItemMeta
    }

    /**
     * Sets or replaces the ItemMeta of the ItemStack.
     *
     * @param meta The new ItemMeta to use.
     * @return This ItemFactory instance for chaining.
     */
    public ItemFactory withMeta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Sets the lore (description) of the ItemStack.
     *
     * @param lore The lore lines to set.
     * @return This ItemFactory instance for chaining.
     */
    public ItemFactory setLore(String... lore) {
        if (meta != null) {
            meta.setLore(Arrays.asList(lore));
        }
        return this;
    }

    /**
     * Sets the display name of the ItemStack.
     *
     * @param name The display name to set.
     * @return This ItemFactory instance for chaining.
     */
    public ItemFactory setItemDisplayName(String name) {
        if (meta != null) {
            meta.setDisplayName(name);
        }
        return this;
    }

    /**
     * Adds enchantments to the ItemStack.
     *
     * @param enchantments A map of enchantments and their corresponding levels.
     * @return This ItemFactory instance for chaining.
     */
    public ItemFactory addEnchantments(Map<Enchantment, Integer> enchantments) {
        if (meta != null) {
            enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
        }
        return this;
    }

    /**
     * Sets custom model data for the ItemStack. Custom model data allows for the customization of item models in resource packs.
     *
     * @param data The custom model data.
     * @return This ItemFactory instance for chaining.
     */
    public ItemFactory setCustomModelData(Integer data) {
        if (meta != null) {
            meta.setCustomModelData(data);
        }
        return this;
    }

    /**
     * Adds item flags to the ItemStack to hide certain properties from the client.
     *
     * @param flags The item flags to add.
     * @return This ItemFactory instance for chaining.
     */
    public ItemFactory addItemFlags(ItemFlag... flags) {
        if (meta != null) {
            meta.addItemFlags(flags);
        }
        return this;
    }

    /**
     * Builds the ItemStack with all modifications applied.
     *
     * @return The modified ItemStack.
     */
    public ItemStack buildItem() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * builds the item with a players skull texture.
     * @param player the players skull to copy.
     * @return returns the ItemStack with the players head texture.
     */
    public ItemStack withSkullOfPlayer(Player player) {
        if (!this.itemStack.getType().equals(Material.PLAYER_HEAD)) return null;
        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(player);
            this.itemStack.setItemMeta(skullMeta);
        }
        return this.itemStack;
    }

    /**
     * adds an effect to this ItemStack
     * @param triggerType Type of event to trigger this effect
     * @param consumer The effect the player wants to be triggered
     */
    public void addEffect(TriggerTypes triggerType, BiConsumer<Player, Object> consumer){
        if(!effects.containsKey(triggerType)) effects.put(triggerType, new ArrayList<>());
        effects.get(triggerType).add(consumer);
    }
}
