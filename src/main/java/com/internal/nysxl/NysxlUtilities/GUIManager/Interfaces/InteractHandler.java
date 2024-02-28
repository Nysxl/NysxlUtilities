package com.internal.nysxl.NysxlUtilities.GUIManager.Interfaces;

import org.bukkit.event.player.PlayerInteractEvent;

public interface InteractHandler {

    boolean canHandle(PlayerInteractEvent e);
    void handle(PlayerInteractEvent e);

}
