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
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class HTGLoggers extends JavaPlugin implements Listener {

    private File logFile;
    private FileConfiguration config;
    private Map<String, String> formatProperties = new HashMap<>();

    @Override
    public void onEnable() {
        // Ładowanie konfiguracji
        saveDefaultConfig();
        config = getConfig();

        // Inicjalizacja API i rejestracja pluginu
        HTGLoggersAPI.initialize(this);

        // Rejestracja eventów - można dodać warunki na podstawie konfiguracji
        registerListeners();

        getLogger().info("LoggerPlugin has been enabled!");

        // Tworzenie lub ładowanie pliku logu
        HTGLoggersAPI.createLogFile("htgloggers");
    }

    @Override
    public void onDisable() {
        getLogger().info("LoggerPlugin has been disabled!");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new LogListener(this), this);
        getServer().getPluginManager().registerEvents(new LogRequestListener(this), this);

        // Rejestracja nasłuchiwaczy dla różnych typów logów na podstawie konfiguracji
        if (isLoggingEnabled("player_join")) {
            getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        }
        if (isLoggingEnabled("player_quit")) {
            getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        }
        if (isLoggingEnabled("chat")) {
            getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        }
        if (isLoggingEnabled("command")) {
            getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        }
        if (isLoggingEnabled("player_teleport")) {
            getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);
        }
        if (isLoggingEnabled("advancement")) {
            getServer().getPluginManager().registerEvents(new PlayerAdvancementListener(this), this);
        }
        if (isLoggingEnabled("block_break")) {
            getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        }
    }

    public boolean isLoggingEnabled(String eventType) {
        return config.getBoolean("events." + eventType, false);
    }

    public void log(String message) {
        getLogger().info(message); // Logowanie na konsolę
        logToFile(message); // Logowanie do pliku
    }

    public void logToFile(String message) {
        if (logFile == null) {
            getLogger().severe("Log file is not created!");
            return;
        }

        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Error writing to log file: ", e);
        }
    }

    public String getFormattedMessage(String formatKey, String playerName, String additionalInfo) {
        // Ustawienia domyślne, jeśli nie znaleziono formatu w konfiguracji
        String timestampFormat = config.getString("logging.format.timestamp_format", "yyyy-MM-dd HH:mm:ss");
        String format = config.getString("logging.format." + formatKey, "{time} {player} {message}");

        String timestamp = new SimpleDateFormat(timestampFormat).format(new Date());

        String formattedMessage = format
                .replace("{time}", timestamp)
                .replace("{player}", playerName != null ? playerName : "Unknown")
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
