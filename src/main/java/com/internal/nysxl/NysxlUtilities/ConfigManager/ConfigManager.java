package com.internal.nysxl.NysxlUtilities.ConfigManager;

import com.internal.nysxl.NysxlUtilities.ConfigManager.DataTypes.DataType;
import com.internal.nysxl.NysxlUtilities.Utility.ConsumerInterfaces.TriConsumerReturn;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages configuration files for a Bukkit/Spigot plugin.
 * This class allows for the dynamic handling of multiple configuration files.
 */
public class ConfigManager {
    private final Plugin plugin;
    private final Map<String, FileConfiguration> configMap = new HashMap<>();
    private final Map<String, File> configFileMap = new HashMap<>();

    HashMap<DataType, TriConsumerReturn<FileConfiguration,String,Object,?>> consumer = new HashMap<>();

    /**
     * Constructs a new ConfigManager associated with the given plugin. and adds consumers to a hashmap.
     * @param plugin The plugin this ConfigManager is for.
     */
    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;

        consumer.put(DataType.STRING, (config, path, defaultValue) -> config.getString(path, (String) defaultValue));
        consumer.put(DataType.INTEGER, (config, path, defaultValue) -> config.getInt(path, (Integer) defaultValue));
        consumer.put(DataType.BOOLEAN, (config, path, defaultValue) -> config.getBoolean(path, (Boolean) defaultValue));
        consumer.put(DataType.DOUBLE, (config, path, defaultValue) -> config.getDouble(path, (Double) defaultValue));
        consumer.put(DataType.LONG, (config, path, defaultValue) -> config.getLong(path, (Long) defaultValue));
        consumer.put(DataType.OBJECT, (config, path, defaultValue) -> config.get(path, defaultValue));
        consumer.put(DataType.LIST, (config, path, defaultValue) -> config.getList(path));
    }

    /**
     * Retrieves the configuration for the specified file name.
     * If the configuration file does not exist, it will be created.
     * @param fileName The name of the configuration file to retrieve.
     * @return The FileConfiguration object associated with the given file name.
     */
    public FileConfiguration getConfig(String fileName) {
        if (configMap.containsKey(fileName)) {
            return configMap.get(fileName);
        }

        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        configMap.put(fileName, config);
        configFileMap.put(fileName, configFile);

        return config;
    }

    /**
     * Retrieves a configuration value from a specified path within a configuration file. This method allows for type-safe retrieval of configuration values by specifying the expected return type along with a default value to be returned in case the path does not exist or the data type does not match.
     *
     * @param <T> The expected return type of the configuration value. This type should match the type parameter.
     * @param config The {@link FileConfiguration} object from which the configuration value is to be retrieved.
     * @param path The path within the configuration file where the desired value is located.
     * @param dataType The {@link DataType} enum value indicating the expected type of the configuration value. This should correspond to the type of value stored at the specified path.
     * @param defaultValue The default value to return in case the specified path does not exist in the configuration file or if there is a type mismatch. This value must be of type T.
     * @param type The {@link Class} object representing the type T. This is used for type casting the retrieved value to ensure type safety.
     * @return The configuration value of type T retrieved from the specified path. If the path does not exist or if there is a type mismatch, the defaultValue is returned.
     * @throws ClassCastException if the object found at the specified path cannot be cast to type T.
     */
    public <T> T getConfiguration(FileConfiguration config, String path, DataType dataType, T defaultValue, Class<T> type) {
        if(!consumer.containsKey(dataType)) return defaultValue;
        Object result = consumer.get(dataType).apply(config, path, defaultValue);
        return type.cast(result);
    }

    /**
     * Saves the configuration to the file for the specified file name.
     * If the configuration or file does not exist, a warning will be logged.
     * @param fileName The name of the configuration file to save.
     */
    public void saveConfig(String fileName) {
        if (!configMap.containsKey(fileName) || !configFileMap.containsKey(fileName)) {
            plugin.getLogger().warning("Config file '" + fileName + "' not found.");
            return;
        }

        try {
            configMap.get(fileName).save(configFileMap.get(fileName));
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config to " + fileName);
        }
    }

    /**
     * Reloads the configuration from the file for the specified file name.
     * If the file is not currently managed, it attempts to load it as a new configuration.
     * @param fileName The name of the configuration file to reload.
     */
    public void reloadConfig(String fileName) {
        if (configFileMap.containsKey(fileName)) {
            File configFile = configFileMap.get(fileName);
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            configMap.put(fileName, config);
        } else {
            getConfig(fileName);
        }
    }

    /**
     * Reloads all configurations that are currently managed by this ConfigManager.
     */
    public void reloadAllConfigs() {
        for (String fileName : configFileMap.keySet()) {
            reloadConfig(fileName);
        }
    }
}
