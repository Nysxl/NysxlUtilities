package com.internal.nysxl.NysxlUtilities.GUIManager;

import com.internal.nysxl.NysxlUtilities.GUIManager.Buttons.DynamicButton;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DynamicInteractiveGUI extends DynamicGUI{

    private Map<Integer, DynamicButton> droppedButtons = new HashMap<>();

    public DynamicInteractiveGUI(String name, int size) {
        super(name, size);
    }

    public DynamicInteractiveGUI(String name, int size, ItemStack fillItem) {
        super(name, size, fillItem);
    }

    @Override
    public void onClick(InventoryClickEvent e){
        //checks if the click action was dropping an item.
        if(!e.getAction().equals(InventoryAction.DROP_ALL_CURSOR)) {
            e.setCancelled(true);
            return;
        }

        //checks that the slot an item has been attempted to be dropped in already exists or not.
        if(droppedButtons.containsKey(e.getSlot()) || super.getActionButtons().containsKey(e.getSlot())){
            e.setCancelled(true);
            return;
        }

        //creates a dynamic button.
        DynamicButton button = new DynamicButton(e.getSlot(), e.getCurrentItem());

        droppedButtons.put(e.getSlot(), button);
    }


}
