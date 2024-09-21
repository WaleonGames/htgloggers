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
            plugin.getLogger().severe("Plugin instance cannot be null!");
            throw new IllegalArgumentException("Plugin instance cannot be null!");
        }
        plugin = pluginInstance;
        plugin.getLogger().info("HTGLoggersAPI initialized successfully.");
    }

    public static void createLogFile(String pluginName) {
        if (plugin == null) {
            plugin.getLogger().severe("HTGLoggersAPI has not been initialized!");
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
                plugin.getLogger().info("Log file already exists for plugin: " + pluginName);
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred while creating the log file for plugin " + pluginName, e);
        }
    }

    public static HTGLoggers getPluginInstance() {
        return plugin;
    }

    public static void log(String message) {
        if (plugin != null) {
            plugin.getLogger().info(message);
        } else {
            System.out.println("HTGLoggersAPI not initialized: " + message);
        }
    }

    public static void logError(String message, Exception e) {
        if (plugin != null) {
            plugin.getLogger().log(Level.SEVERE, message, e);
        } else {
            System.err.println("HTGLoggersAPI not initialized: " + message);
            e.printStackTrace();
        }
    }
}
