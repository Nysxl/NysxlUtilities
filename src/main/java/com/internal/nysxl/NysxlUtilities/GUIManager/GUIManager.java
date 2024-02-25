package com.internal.nysxl.NysxlUtilities.GUIManager;

import com.internal.nysxl.NysxlUtilities.Listeners.EntityListeners.EventHandler.InventoryClickHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class GUIManager implements InventoryClickHandler {

    private final List<GUIFactory> guis = new ArrayList<>();

    public void addGUI(GUIFactory gui){
        this.guis.add(gui);
    }

    @Override
    public boolean canHandle(InventoryClickEvent e) {
        return guis.parallelStream().anyMatch(s->s.getInv().equals(e.getClickedInventory()));
    }

    @Override
    public void handle(InventoryClickEvent e) {
        GUIFactory gui = guis.parallelStream().filter(s->s.getInv().equals(e.getClickedInventory())).findFirst().orElse(null);
        if(gui == null) return;

        gui.handleInventoryClick(e.getSlot(), true);
    }
}
