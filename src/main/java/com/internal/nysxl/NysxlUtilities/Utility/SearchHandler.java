package com.internal.nysxl.NysxlUtilities.Utility;// Import necessary Bukkit/Spigot API classes
import com.internal.nysxl.NysxlUtilities.GUIManager.DynamicGUI;
import com.internal.nysxl.NysxlUtilities.GUIManager.DynamicListGUI;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class SearchHandler implements Listener {

    //a map of player currently searching
    private static final Map<Player, BiConsumer<Player, String>> searching = new HashMap<>();

    /**
     * puts the player into the searching list. this way it's not missed when the player types something in chat
     * @param player the player searching.
     * @param consumer the BIConsumer function that will be executed when the player types
     */
    public static void startSearchInteraction(Player player, BiConsumer<Player, String> consumer) {
        player.sendMessage("Enter your search term:");
        searching.put(player, consumer);
    }

    /**
     * the asyncChatListener
     * @param e the event that occurs when a player chats.
     */
    @EventHandler
    public void asyncChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!searching.containsKey(player)) return;

        String searchTerm = e.getMessage();
        BiConsumer<Player, String> consumer = searching.get(player);

        // Perform the search operation or any other action associated with the consumer
        if (consumer != null) {
            consumer.accept(player, searchTerm);
        }

        // Clean up after handling
        searching.remove(player);
    }


}
