package com.internal.nysxl.NysxlUtilities.GUIManager;

import com.internal.nysxl.NysxlUtilities.GUIManager.Buttons.DynamicButton;
import com.internal.nysxl.NysxlUtilities.GUIManager.Buttons.DynamicToggleButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents a dynamic GUI that can hold buttons with associated actions.
 */
public class DynamicGUI implements InventoryHolder {

    private final Inventory inv;
    private ItemStack fillItem;
    private final Map<Integer, DynamicButton> actionButtons = new HashMap<>();
    private Consumer<Player> onCloseAction;

    /**
     * Constructs a new DynamicGUI with a specified name and size.
     * @param name The name of the inventory.
     * @param size The size of the inventory, must be a multiple of 9.
     */
    public DynamicGUI(String name, int size){
        this.inv = Bukkit.createInventory(this, size, name);
    }

    /**
     *
     * @param name
     * @param size
     * @param fillItem
     */
    public DynamicGUI(String name, int size, ItemStack fillItem){
        this.inv = Bukkit.createInventory(this, size, name);
        this.fillItem = fillItem;
        fillItem();
    }

    /**
     * Sets an action to be run when the inventory is closed.
     * @param onCloseAction The action to run when the inventory is closed.
     */
    public void setOnCloseAction(Consumer<Player> onCloseAction) {
        this.onCloseAction = onCloseAction;
    }

    /**
     * Adds a button to the GUI at the specified slot with an associated action.
     * @param slot The slot where the item should appear.
     * @param button The ItemStack to be placed in the GUI as a button.
     * @param action The action to be executed when the button is pressed.
     */
    public void addButton(int slot, ItemStack button, Consumer<Player> action){
        DynamicButton dynamicButton = new DynamicButton(slot, button, action);
        inv.setItem(slot, button);
        actionButtons.put(slot, dynamicButton);
    }

    /**
     * adds a toggle button to the gui.
     * @param slot the slot the toggle button should be added to.
     * @param button the itemstack to be placed in the GUI as a button when not toggled.
     * @param toggledButton  the itemstack to be placed in the GUI as a button when not toggled.
     * @param action  the action to be run when the button is not toggled.
     * @param toggledAction the action to be run when the button is toggled.
     */
    public void addGUIToggleButton(int slot, ItemStack button, ItemStack toggledButton, Consumer<Player> action, Consumer<Player> toggledAction){
        DynamicButton dynamicButton = new DynamicToggleButton(slot, button, toggledButton, action, toggledAction);
        inv.setItem(slot, button);
        actionButtons.put(slot, dynamicButton);
    }

    /**
     * sets the item you want the background of the GUI filled with.
     * @param item the itemstack you want the inventory filled with.
     */
    public void setFillItem(ItemStack item){
        this.fillItem = item;
    }

    /**
     * @return returns this object. not needed for the most part.
     */
    public DynamicGUI fillItem(){
        if(fillItem == null) return null;
        for(int i = 0; i < getInventory().getSize(); i++){
            inv.setItem(i, fillItem);
        }
        return this;
    }

    /**
     * Adds a button to the GUI that, when clicked, opens another specified GUI.
     * @param slot The slot where the GUI button should be placed.
     * @param button The ItemStack representing the button.
     * @param targetGUI The target GUI to open when the button is clicked.
     * @return true if the button was successfully added, false otherwise.
     */
    public boolean addGUIButton(int slot, ItemStack button, DynamicGUI targetGUI) {
        if (targetGUI == this || slot < 0 || slot >= inv.getSize()) {
            return false;
        }

        Consumer<Player> openTargetGUI = player -> player.openInventory(targetGUI.getInventory());
        addButton(slot, button, openTargetGUI);
        return true;
    }

    /**
     * Opens the GUI for a specified player.
     * @param player The player for whom the GUI should be opened.
     */
    public void open(Player player){
        player.openInventory(inv);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    /**
     * Handles a click within the inventory by executing the associated action if any.
     * @param whoClicked The player who clicked inside the inventory.
     * @param slot The slot that was clicked.
     */
    public void handleClick(Player whoClicked, int slot){
        if (actionButtons.containsKey(slot)) {
            actionButtons.get(slot).runAction(whoClicked);
            updateGUI();
        }
    }

    /**
     * Executes the onClose action when the inventory is closed by a player.
     * @param whoClosed The player who closed the inventory.
     */
    public void onClose(Player whoClosed){
        if (onCloseAction != null) {
            onCloseAction.accept(whoClosed);
        }
    }

    /**
     * updates the gui with the updated buttons.
     */
    public void updateGUI(){
        fillItem();
        actionButtons.keySet().parallelStream().
                forEach(s -> inv.setItem(actionButtons.get(s).
                        getSlot(), actionButtons.get(s).getButton()));
    }

    /**
     * clears the GUI of all items
     */
    public void ClearInventory(){
        inv.clear();
    }


}
