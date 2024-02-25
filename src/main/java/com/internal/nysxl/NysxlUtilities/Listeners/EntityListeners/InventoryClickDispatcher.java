package com.internal.nysxl.NysxlUtilities.Listeners.EntityListeners;

import com.internal.nysxl.NysxlUtilities.Listeners.EntityListeners.EventHandler.InventoryClickHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;

public class InventoryClickDispatcher implements Listener {

    /**
     * a list of the events for the dispatcher
     */
    private final List<InventoryClickHandler> handlers;

    /**
     * @param handlers a list of handlers from other classes for the event to be distributed to.
     */
    public InventoryClickDispatcher(InventoryClickHandler... handlers){
        this.handlers = Arrays.asList(handlers);
    }

    /**
     * @param e InventoryClickEvent
     */
    @EventHandler
    public void dispatch(InventoryClickEvent e){
        for(InventoryClickHandler handler: handlers){
            if(handler.canHandle(e)){
                handler.handle(e);
            }
        }
    }
}
