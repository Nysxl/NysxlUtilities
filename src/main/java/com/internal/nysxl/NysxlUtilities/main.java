package com.internal.nysxl.NysxlUtilities;

import com.internal.nysxl.NysxlUtilities.ConfigManager.ConfigManager;
import com.internal.nysxl.NysxlUtilities.GUIManager.GUIManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
        private ConfigManager configManager;
        private static Plugin instance;
        private static GUIManager guiManager;

    /**
     * registers all events and config files when the plugin starts.
     */
    @Override
    public void onEnable() {
            instance = this;
            this.configManager = new ConfigManager(this);

            checkPlaceholderAPI();
            registerEvents();
        }

        public void checkPlaceholderAPI(){
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
                getLogger().info("PlaceholderAPI not found, disabling related features.");

            } else {
                getLogger().info("PlaceholderAPI found, enabling features.");
            }
        }

    /**
     * registers all listener events.
     */
    public void registerEvents(){
        guiManager = new GUIManager(this);
    }

    /**
     * all functinos that should run when the server/plugin shuts down
     */
    @Override
        public void onDisable() {
            // Save all configs on disable, if needed
            configManager.saveConfig("config1.yml");
            configManager.saveConfig("config2.yml");
            // Or you could iterate through all known configs and save them
        }

    /**
     * @return returns an instance of the plugin.
     */
    public static Plugin getInstance() {
        return instance;
    }


}