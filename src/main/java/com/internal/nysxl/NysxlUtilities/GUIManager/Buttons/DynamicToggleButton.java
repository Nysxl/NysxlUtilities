package com.internal.nysxl.NysxlUtilities.GUIManager.Buttons;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

/**
 * Represents a toggle button within a DynamicGUI, capable of switching between two visual and functional states.
 */
public class DynamicToggleButton extends DynamicButton {

    private ItemStack originalButton;
    private ItemStack toggledButton;
    private Consumer<Player> originalAction;
    private Consumer<Player> toggledAction;

    private boolean isToggled = false;

    /**
     * Constructs a DynamicToggleButton with specified properties for both its default and toggled states.
     *
     * @param slot           The inventory slot for this button.
     * @param button         The ItemStack for the button in its default state.
     * @param toggledButton  The ItemStack for the button when toggled.
     * @param action         The action to execute in the default state.
     * @param toggledAction  The action to execute in the toggled state.
     */
    public DynamicToggleButton(int slot, ItemStack button, ItemStack toggledButton, Consumer<Player> action, Consumer<Player> toggledAction) {
        super(slot, button, ClickType.LEFT_CLICK ,action);
        this.originalButton = button;
        this.toggledButton = toggledButton;
        this.originalAction = action;
        this.toggledAction = toggledAction;
    }

    /**
     * Toggles the button's state and updates its appearance and associated action accordingly.
     *
     * @param player The player who interacted with the button.
     */
    @Override
    public void executeAction(Player player, ClickType clickType){
        isToggled = !isToggled;
        updateButtonState();
        super.executeAction(player, clickType);
    }

    /**
     * Updates the button's ItemStack and action based on its current toggled state.
     */
    private void updateButtonState() {
        if(isToggled) {
            super.setButton(toggledButton);
            super.setAction(ClickType.LEFT_CLICK, toggledAction);
        } else {
            super.setButton(originalButton);
            super.setAction(ClickType.LEFT_CLICK, originalAction);
        }
    }
}
