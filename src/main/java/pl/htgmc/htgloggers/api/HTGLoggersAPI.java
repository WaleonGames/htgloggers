package pl.htgmc.htgloggers.api;

import pl.htgmc.htgloggers.HTGLoggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class HTGLoggersAPI {

    private static HTGLoggers plugin;
    private static final String MINIMUM_API_VERSION = "2.1.0.0-release"; // Minimalna wersja API
    private static final String DEFAULT_API_VERSION = "2.1.3.0-beta-gui-api"; // Domyślna wersja API

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
                plugin.getLogger().info("Log file already exists for plugin: " + pluginName);
            }
        } catch (IOException e) {
            logError("An error occurred while creating the log file for plugin " + pluginName, e);
        }
    }

    public static void log(String message) {
        if (plugin != null) {
            plugin.getLogger().info(message);
        } else {
            System.err.println("HTGLoggersAPI not initialized: " + message);
        }
    }

    public static void logToFile(String message, String pluginName) {
        if (plugin == null) {
            throw new IllegalStateException("HTGLoggersAPI has not been initialized!");
        }

        File logFile = new File(plugin.getDataFolder(), pluginName + "-log.log");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            logError("An error occurred while writing to the log file for plugin " + pluginName, e);
        }
    }

    public static void logError(String message, Exception e) {
        if (plugin != null) {
            plugin.getLogger().log(Level.SEVERE, message, e);
        } else {
            System.err.println("HTGLoggersAPI not initialized: " + message);
            if (e != null) {
                e.printStackTrace();
            } else {
                System.err.println("Error occurred, but exception is null.");
            }
        }
    }

    public static boolean isInitialized() {
        return plugin != null;
    }

    public static String getMinimumApiVersion() {
        return MINIMUM_API_VERSION;
    }

    public static String getDefaultApiVersion() {
        return DEFAULT_API_VERSION;
    }

    // Metoda do sprawdzenia wersji API
    public static boolean isVersionSupported(String version) {
        return version.compareTo(MINIMUM_API_VERSION) >= 0; // Sprawdzenie, czy wersja jest zgodna
    }
}
