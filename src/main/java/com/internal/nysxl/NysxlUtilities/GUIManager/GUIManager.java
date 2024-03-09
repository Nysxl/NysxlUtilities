package com.internal.nysxl.NysxlUtilities.GUIManager;

import com.internal.nysxl.NysxlUtilities.GUIManager.Buttons.DynamicButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class GUIManager implements Listener {

    private Plugin plugin;

    /**
     * the constructor method
     * @param plugin instance of the main plugin.
     */
    public GUIManager(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * handles all Dynamic GUI's and runs the associated action for that button.
     * @param event the event that occurs when a player clicks inside an inventory
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof DynamicGUI dynamicGUI)) return;
        dynamicGUI.onClick(event);
        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();


        // Using Optional to avoid explicit null checks.
        Optional.ofNullable(event.getInventory().getHolder())
                .filter(holder -> holder instanceof DynamicGUI)
                .map(holder -> (DynamicGUI) holder)
                .ifPresent(gui -> {
                    // Determine the ClickType using a helper method
                    determineClickType(event).ifPresent(clickType ->
                            gui.executeAction(player, clickType, slot));
                });
    }

    private Optional<DynamicButton.ClickType> determineClickType(InventoryClickEvent event) {
        if (event.isShiftClick()) {
            return Optional.of(event.isLeftClick() ? DynamicButton.ClickType.SHIFT_LEFT_CLICK : DynamicButton.ClickType.SHIFT_RIGHT_CLICK);
        } else if (event.isLeftClick()) {
            return Optional.of(DynamicButton.ClickType.LEFT_CLICK);
        } else if (event.isRightClick()) {
            return Optional.of(DynamicButton.ClickType.RIGHT_CLICK);
        } else if (event.getClick().isMouseClick()) {
            return Optional.of(DynamicButton.ClickType.MIDDLE_CLICK);
        }
        return Optional.empty();
    }


    /**
     * handles all Dynamic GUI's and runs the associated onClose action for the gui
     * @param event the event that occurs when a player closes the inventory
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        if(event.getInventory().getHolder() instanceof DynamicGUI){
            ((DynamicGUI) event.getInventory().getHolder()).onClose((Player) event.getPlayer());
        }
    }
}
