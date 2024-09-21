package pl.htgmc.htgloggers.api;

import pl.htgmc.htgloggers.HTGLoggers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class HTGLoggersAPI {

    private static HTGLoggers plugin;

    public static void initialize(HTGLoggers pluginInstance) {
        if (plugin != null) {
            plugin.getLogger().warning("HTGLoggersAPI has already been initialized!");
            return;
        }
        if (pluginInstance == null) {
            throw new IllegalArgumentException("Plugin instance cannot be null!");
        }
        plugin = pluginInstance;
        plugin.getLogger().info("HTGLoggersAPI initialized successfully.");
    }

    public static void createLogFile(String pluginName) {
        if (plugin == null) {
            throw new IllegalStateException("HTGLoggersAPI has not been initialized!");
        }

        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            plugin.getLogger().severe("Failed to create data folder for plugin: " + pluginName);
            return;
        }

        File logFile = new File(dataFolder, pluginName + "-log.log");

        try {
            if (logFile.createNewFile()) {
                plugin.getLogger().info("Log file created for plugin: " + pluginName);
            } else {
                plugin.getLogger().info("Log file loaded for plugin: " + pluginName);
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred while creating the log file for plugin " + pluginName, e);
        }
    }
}
