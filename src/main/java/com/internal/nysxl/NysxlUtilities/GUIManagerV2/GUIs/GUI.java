package com.internal.nysxl.NysxlUtilities.GUIManagerV2.GUIs;

import com.internal.nysxl.NysxlUtilities.GUIManagerV2.Buttons.Button;
import com.internal.nysxl.NysxlUtilities.ItemBuilder.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a GUI with clickable buttons.
 */
public class GUI implements InventoryHolder {

    private final Inventory inventory;
    private final Map<Integer, Button> buttons;
    private final ItemStack fillItem = new ItemFactory(Material.BLACK_STAINED_GLASS_PANE).setItemDisplayName(" ").buildItem();

    private ArrayList<Integer> fillSlots = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53));

    /**
     * Creates a GUI with a specified title and size.
     * @param title The title of the GUI.
     * @param size The number of slots in the GUI (must be a multiple of 9).
     */
    public GUI(String title, int size) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.buttons = new HashMap<>();
    }

    /**
     * Adds a button to the GUI.
     * @param button The button to add.
     */
    public void addButton(Button button) {
        if (button.getSlot() >= 0 && button.getSlot() < inventory.getSize()) {
            inventory.setItem(button.getSlot(), button.getItem());
            buttons.put(button.getSlot(), button);
        }
    }

    public void addAllButtons(){
        buttons.values().forEach(this::addButton);
    }

    /**
     * Removes a button from the GUI at the specified slot.
     * @param slot The slot from which to remove the button.
     */
    public void removeButton(int slot) {
        if (buttons.containsKey(slot)) {
            inventory.clear(slot);
            buttons.remove(slot);
        }
    }

    /**
     * Retrieves a button at a specified slot.
     * @param slot The slot of the button to retrieve.
     * @return The button at the specified slot, or null if no button is present.
     */
    public Button getButton(int slot) {
        return buttons.get(slot);
    }

    /**
     * Executes an action associated with a button click.
     * @param e The inventory click event.
     * @return A string result of the button action execution.
     */
    public String executeAction(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player player) {
            Button button = buttons.get(e.getSlot());
            if (button != null) {
                String result = button.executeAction(e, player);

                if (result.toLowerCase().contains("remove")) {
                    removeButton(e.getSlot());
                }
                return result;
            }
        }
        return "";
    }

    /**
     * returns all buttons
     * @return a map<Integer, Button>
     */
    public Map<Integer, Button> getButtons() {
        return buttons;
    }

    public void initializeFill(){
        for(Integer i : fillSlots){
            this.inventory.setItem(i, fillItem);
        }
    }

    /**
     * opens the gui for a player
     * @param player the player to open the gui for.
     */
    public void open(Player player){
        initializeFill();
        addAllButtons();
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
