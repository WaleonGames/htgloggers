package pl.htgmc.htgloggers.api;

import pl.htgmc.htgloggers.HTGLoggers;

import java.io.File;
import java.io.IOException;

public class HTGLoggersAPI {

    public static HTGLoggers plugin;

    public static void initialize(HTGLoggers plugin) {
        HTGLoggersAPI.plugin = plugin;
    }

    public static void createLogFile(String pluginName) {
        if (plugin == null) {
            throw new IllegalStateException("HTGLoggersAPI.plugin is not set!");
        }

        File dataFolder = plugin.getDataFolder();
        File logFile = new File(dataFolder, pluginName + "-log.log");

        try {
            if (!logFile.exists() && !logFile.createNewFile()) {
                plugin.getLogger().severe("Could not create log file for plugin: " + pluginName);
            } else {
                plugin.getLogger().info("Log file created or loaded for plugin: " + pluginName);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("An error occurred while creating the log file for plugin " + pluginName + ": " + e.getMessage());
        }
    }
}
