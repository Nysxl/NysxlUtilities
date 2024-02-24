package com.internal.nysxl.NysxlUtilities;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

        @Override
        public void onEnable() {
            checkPlaceholderAPI();
        }

        public void checkPlaceholderAPI(){
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
                getLogger().info("PlaceholderAPI not found, disabling related features.");

            } else {
                getLogger().info("PlaceholderAPI found, enabling features.");
            }
        }

}