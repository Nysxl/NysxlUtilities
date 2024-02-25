package com.internal.nysxl.NysxlUtilities.GUIManager;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Button {

    private ItemStack button;
    private int slot;
    private Runnable function;

    /**
     * Constructs a new Button.
     *
     * @param button The item representation of the button.
     * @param slot The inventory slot where the button will be placed.
     * @param function The function to execute when the button is clicked.
     */
    public Button(ItemStack button, int slot, Runnable function) {
        if(button == null) throw new IllegalArgumentException("Button item cannot be null.");
        if(slot < 0) throw new IllegalArgumentException("Slot number cannot be negative.");
        this.button = button;
        this.slot = slot;
        this.function = function;
    }

    /**
     * Checks if this button was clicked.
     *
     * @param slot The slot that was clicked.
     * @param runFunction Whether to run the associated function.
     * @return true if this button was clicked, false otherwise.
     */
    public boolean getClicked(int slot, boolean runFunction){
        if(this.slot == slot){
            if(runFunction && function != null) function.run();
            return true;
        }
        return false;
    }

    /**
     * @return returns the slot the button will be placed in
     */
    public int getSlot() {
        return slot;
    }

    /**
     * @return Returns the runnable function assigned to the button
     */
    public Runnable getFunction() {
        return function;
    }

    /**
     * Places this button's item in the given inventory slot.
     *
     * @param inv The inventory where the button will be placed.
     * @return true if the item was placed successfully, false otherwise.
     */
    public boolean placeItemInInventory(Inventory inv){
        if(inv == null) return false;
        inv.setItem(slot, button);
        return true;
    }
}