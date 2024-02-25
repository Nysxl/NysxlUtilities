package com.internal.nysxl.NysxlUtilities.GUIManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.HashMap;
import java.util.Map;

/**
 * A builder class for creating and managing a GUI in a Bukkit plugin.
 * It allows for easy setup and manipulation of inventory interfaces with customizable buttons.
 */
public class GUIFactory {
    private Inventory inv;
    private Map<Integer, Button> buttons;
    private boolean canTakeItems = false;

    /**
     * Creates a GUIBuilder instance with a specified inventory size and title.
     *
     * @param size  The size of the inventory to be created. Must be a multiple of 9.
     * @param title The title of the inventory GUI.
     */
    public GUIFactory(int size, String title) {
        this.inv = Bukkit.createInventory(null, size, title);
        this.buttons = new HashMap<>();
    }

    /**
     * Adds or replaces buttons in the GUI.
     *
     * @param buttons A map of buttons where the key is the inventory slot and the value is the Button object.
     *                This replaces any previously added buttons.
     * @return This GUIBuilder object for chaining method calls.
     */
    public GUIFactory withButtons(Map<Integer, Button> buttons) {
        this.buttons = buttons;
        return this;
    }

    /**
     * Removes a button from a specific slot in the GUI.
     *
     * @param slot The inventory slot from which to remove the button.
     * @return This GUIBuilder object for chaining method calls.
     */
    public GUIFactory removeButton(int slot) {
        this.buttons.remove(slot);
        return this;
    }

    /**
     * Builds or rebuilds the inventory GUI based on the current set of buttons.
     * This should be called after all buttons have been added or modified.
     *
     * @return This GUIBuilder object for chaining method calls.
     */
    public GUIFactory buildInventory() {
        inv.clear(); // Clear the inventory to rebuild it with the current set of buttons
        for (Button button : buttons.values()) {
            if (button != null) {
                button.placeItemInInventory(inv);
            }
        }
        return this;
    }

    /**
     * Opens the inventory GUI for a specific player.
     *
     * @param player The player for whom to open the inventory GUI.
     */
    public void showInventory(Player player) {
        player.openInventory(inv);
    }

    /**
     * Handles an inventory click event. This method should be called from an event listener
     * to process clicks on the inventory GUI and execute button-specific actions if necessary.
     *
     * @param slot        The inventory slot that was clicked.
     * @param runFunction A boolean indicating whether the button's action should be executed.
     * @return true if a button in the specified slot was clicked and processed, false otherwise.
     */
    public boolean handleInventoryClick(int slot, boolean runFunction) {
        Button clickedButton = buttons.get(slot);
        if (clickedButton != null) {
            return clickedButton.getClicked(slot, runFunction);
        }
        return false;
    }

    /**
     * @return returns this inventory
     */
    public Inventory getInv() {
        return inv;
    }
}
