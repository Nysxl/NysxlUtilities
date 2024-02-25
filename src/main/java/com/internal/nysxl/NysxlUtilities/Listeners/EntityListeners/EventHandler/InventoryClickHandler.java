package com.internal.nysxl.NysxlUtilities.Listeners.EntityListeners.EventHandler;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryClickHandler {
    boolean canHandle(InventoryClickEvent e);
    void handle(InventoryClickEvent e);
}
