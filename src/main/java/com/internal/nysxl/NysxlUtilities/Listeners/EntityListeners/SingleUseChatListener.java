package com.internal.nysxl.NysxlUtilities.Listeners.EntityListeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class SingleUseChatListener implements Listener {
    private final Player player;
    private final Consumer<String> onChat;
    private final Plugin plugin;

    public SingleUseChatListener(Plugin plugin, Player player, Consumer<String> onChat) {
        this.plugin = plugin;
        this.player = player;
        this.onChat = onChat;
        // Automatically register this listener
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().equals(player)) {
            event.setCancelled(true); // Optional: Cancel the event to not show the message to others
            String message = event.getMessage();

            // Execute the consumer with the message
            onChat.accept(message);

            // Unregister this listener
            AsyncPlayerChatEvent.getHandlerList().unregister(this);
        }
    }
}