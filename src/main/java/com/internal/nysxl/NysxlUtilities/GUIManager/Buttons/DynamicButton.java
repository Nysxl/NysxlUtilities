package com.internal.nysxl.NysxlUtilities.GUIManager.Buttons;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

public class DynamicButton {

    private final String id;
    private int slot;
    private ItemStack button;
    private final EnumMap<ClickType, Consumer<Player>> clickActions;

    public static enum ClickType {
        SHIFT_LEFT_CLICK,
        SHIFT_RIGHT_CLICK,
        LEFT_CLICK,
        RIGHT_CLICK,
        MIDDLE_CLICK
    }

    /**
     * Creates a new DynamicButton with the specified properties.
     *
     * @param slot The slot index in the inventory where the button is placed.
     * @param button The visual representation of the button as an ItemStack.
     */
    public DynamicButton(String id, int slot, ItemStack button) {
        this.id = id;
        this.slot = slot;
        this.button = button;
        this.clickActions = new EnumMap<>(ClickType.class);
    }

    public DynamicButton(String id, int slot, ItemStack button, EnumMap<ClickType, Consumer<Player>> actions) {
        this.id = id;
        this.slot = slot;
        this.button = button;
        this.clickActions = actions;
    }

    public DynamicButton(String id, int slot, ItemStack button, ClickType clickType, Consumer<Player> actions) {
        this.id = id;
        this.slot = slot;
        this.button = button;
        this.clickActions = new EnumMap<>(ClickType.class);
        setAction(clickType, actions);
    }

    /**
     * Updates the display properties of the button, such as its name and lore.
     *
     * @param displayName The new display name of the button.
     * @param lore The new lore of the button.
     */
    public void updateButtonDisplay(String displayName, List<String> lore) {
        ItemMeta meta = button.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            button.setItemMeta(meta);
        }
    }

    /**
     * Executes the action associated with the given click type for the specified player.
     *
     * @param p The player who clicked the button.
     * @param clickType The type of click.
     */
    public String executeAction(Player p, ClickType clickType) {
        Consumer<Player> action = clickActions.get(clickType);
        if (action != null) {
            action.accept(p);
        }
        return id;
    }

    /**
     * returns the slot the item is to be placed in.
     * @return integer of the slot.
     */
    public int getSlot() {
        return slot;
    }

    /**
     * gets the button itemstack.
     * @return itemstack of the button.
     */
    public ItemStack getButton() {
        return button;
    }

    /**
     * Sets the action to be run when the button is clicked with the specified type.
     *
     * @param clickType The type of click.
     * @param action The action to be executed.
     */
    public void setAction(ClickType clickType, Consumer<Player> action) {
        clickActions.put(clickType, action);
    }

    /**
     * returns all click actions.
     * @return EnumMap of click actions.
     */
    public EnumMap<ClickType, Consumer<Player>> getClickActions() {
        return clickActions;
    }

    /**
     * Sets the ItemStack of the button.
     *
     * @param button The ItemStack for the button.
     */
    public void setButton(ItemStack button) {
        this.button = button;
    }

    /**
     * get the button's id
     * @return get the buttons id.
     */
    public String getId() {
        return id;
    }
}
