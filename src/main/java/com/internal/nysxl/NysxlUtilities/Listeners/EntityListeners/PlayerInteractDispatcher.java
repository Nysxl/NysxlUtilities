package com.internal.nysxl.NysxlUtilities.Listeners.EntityListeners;

import com.internal.nysxl.NysxlUtilities.Listeners.EntityListeners.EventHandler.InteractHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class PlayerInteractDispatcher implements Listener {
    private final List<InteractHandler> handlers;

    public PlayerInteractDispatcher() {
        this.handlers = Arrays.asList(

        );
    }
}
