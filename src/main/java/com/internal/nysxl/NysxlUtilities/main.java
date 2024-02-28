package com.internal.nysxl.NysxlUtilities;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

        private static Plugin instance;

        @Override
        public void onEnable() {
            checkPlaceholderAPI();

            instance = this;
        }

        public void checkPlaceholderAPI(){
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
                getLogger().info("PlaceholderAPI not found, disabling related features.");

            } else {
                getLogger().info("PlaceholderAPI found, enabling features.");
            }
        }

    public static Plugin getInstance() {
        return instance;
    }
}