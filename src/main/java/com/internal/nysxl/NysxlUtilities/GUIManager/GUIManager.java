package com.internal.nysxl.NysxlUtilities.GUIManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

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
        if(event.getInventory().getHolder() instanceof DynamicGUI) {
            if(event.getAction().equals(InventoryAction.PICKUP_ALL)) {
                event.setCancelled(true);
                ((DynamicGUI) event.getInventory().getHolder()).handleClick((Player) event.getWhoClicked(), event.getRawSlot());
            }
        }
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
