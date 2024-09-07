package pl.htgmc.htgloggers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.htgmc.htgloggers.api.HTGLoggersAPI;
import pl.htgmc.htgloggers.listener.LogListener;
import pl.htgmc.htgloggers.listener.LogRequestListener;
import pl.htgmc.htgloggers.listener.logs.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HTGLoggers extends JavaPlugin implements Listener {

    private File logFile;
    private FileConfiguration config;
    private Map<String, String> formatProperties = new HashMap<>();

    @Override
    public void onEnable() {
        // Ładowanie konfiguracji
        saveDefaultConfig();
        config = getConfig();

        // Rejestracja API
        HTGLoggersAPI.initialize(this);

        // Rejestrujemy nasłuchiwacze
        getServer().getPluginManager().registerEvents(new LogListener(this), this);
        getServer().getPluginManager().registerEvents(new LogRequestListener(this), this);

        // Rejestracja nasłuchiwaczy dla różnych typów logów
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerAdvancementListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

        getLogger().info("LoggerPlugin has been enabled!");

        // Utwórz lub załaduj plik logu
        HTGLoggersAPI.createLogFile("htgloggers");
    }

    @Override
    public void onDisable() {
        getLogger().info("LoggerPlugin has been disabled!");
    }

    public void createLogFile(String pluginName) {
        File dataFolder = getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            getLogger().severe("Could not create data folder!");
            return;
        }

        logFile = new File(dataFolder, pluginName + "-log.log");
        try {
            if (!logFile.exists() && !logFile.createNewFile()) {
                getLogger().severe("Could not create log file for plugin: " + pluginName);
            } else {
                getLogger().info("Log file created or loaded for plugin: " + pluginName);
            }
        } catch (IOException e) {
            getLogger().severe("An error occurred while creating the log file for plugin " + pluginName + ": " + e.getMessage());
        }
    }

    public boolean isLoggingEnabled(String eventType) {
        return config.getBoolean("events." + eventType, false);
    }

    public void log(String message) {
        getLogger().info(message); // Można logować na konsolę
        logToFile(message); // Logowanie do pliku
    }

    public void logToFile(String message) {
        if (logFile == null) {
            getLogger().severe("Log file is not created!");
            return;
        }

        try (java.io.FileWriter writer = new java.io.FileWriter(logFile, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            getLogger().severe("Error writing to log file: " + e.getMessage());
        }
    }

    public String getFormattedMessage(String formatKey, String playerName, String additionalInfo) {
        String timestamp = new SimpleDateFormat(config.getString("logging.format.timestamp_format")).format(new Date());
        String format = config.getString("logging.format." + formatKey);

        if (format == null) {
            getLogger().warning("Format key '" + formatKey + "' not found in config.yml");
            return "";
        }

        String formattedMessage = format
                .replace("{time}", timestamp)
                .replace("{player}", playerName)
                .replace("{message}", additionalInfo != null ? additionalInfo : "")
                .replace("{command}", additionalInfo != null ? additionalInfo : "");

        // Konwertowanie kodów kolorów '&' i paragrafu '§'
        return ChatColor.translateAlternateColorCodes('&', formattedMessage);
    }

    public void setFormatProperties(Map<String, String> formatProperties) {
        this.formatProperties.putAll(formatProperties);
    }

    public Map<String, String> getFormatProperties() {
        return formatProperties;
    }

    public String getLogFormat(String formatKey) {
        return formatProperties.getOrDefault(formatKey, "");
    }

    public boolean isValidFormat(String formatKey) {
        return formatProperties.containsKey(formatKey);
    }
}
