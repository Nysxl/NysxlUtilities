package com.internal.nysxl.NysxlUtilities.GUIManager.Buttons;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

/**
 * Represents a button within a DynamicGUI, capable of performing an action when clicked.
 */
public class DynamicButton {

    private final int slot;
    private ItemStack button;
    private Consumer<Player> action;

    /**
     * Creates a new DynamicButton with the specified properties.
     *
     * @param slot   The slot index in the inventory where the button is placed.
     * @param button The visual representation of the button as an ItemStack.
     * @param action The action to perform when the button is clicked.
     */
    public DynamicButton(int slot, ItemStack button, Consumer<Player> action){
        this.slot = slot;
        this.button = button;
        this.action = action;
    }

    /**
     * Updates the display properties of the button, such as its name and lore.
     *
     * @param displayName The new display name of the button.
     * @param lore        The new lore of the button.
     */
    public void updateButtonDisplay(String displayName, java.util.List<String> lore){
        ItemMeta meta = button.getItemMeta();
        if(meta != null){
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            button.setItemMeta(meta);
        }
    }

    /**
     * Executes the button's action for the given player.
     *
     * @param p The player who clicked the button.
     */
    public void runAction(Player p){
        action.accept(p);
    }

    // Getter for slot, button, and action for external access
    public int getSlot() { return slot; }
    public ItemStack getButton() { return button; }
    public Consumer<Player> getAction() { return action; }

    /**
     * sets the itemstack of the button
     * @param button the ItemStack for the button.
     */
    public void setButton(ItemStack button) {
        this.button = button;
    }

    /**
     * sets the action that will be run when the button is clicked.
     * @param action the action
     */
    public void setAction(Consumer<Player> action) {
        this.action = action;
    }
}
