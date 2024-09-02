package pl.htgmc.htgloggers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.htgmc.htgloggers.events.LogEvent;
import pl.htgmc.htgloggers.listener.LogListener;
import pl.htgmc.htgloggers.listener.LogRequestListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HTGLoggers extends JavaPlugin implements Listener {

    private File logFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Ładowanie konfiguracji
        saveDefaultConfig();
        config = getConfig();

        // Rejestrujemy nasłuchiwacze
        getServer().getPluginManager().registerEvents(new LogListener(this), this); // Przekazujemy kontekst pluginu
        getServer().getPluginManager().registerEvents(new LogRequestListener(this), this);
        getServer().getPluginManager().registerEvents(this, this); // Rejestracja nasłuchiwacza dla join/quit
        getLogger().info("LoggerPlugin has been enabled!");

        // Utwórz lub załaduj plik logu
        createLogFile("htgloggers");
    }

    @Override
    public void onDisable() {
        getLogger().info("LoggerPlugin has been disabled!");
    }

    public void createLogFile(String pluginName) {
        // Tworzenie folderu danych, jeśli nie istnieje
        File dataFolder = getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            getLogger().severe("Could not create data folder!");
            return;
        }

        // Tworzenie pliku logu dla wskazanego pluginu
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

    public void logToFile(String message) {
        if (logFile != null) {
            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.write(message + "\n");
            } catch (IOException e) {
                getLogger().severe("An error occurred while writing to the log file: " + e.getMessage());
            }
        }
    }

    private String formatLogMessage(String format, String playerName, String additionalInfo) {
        String timestamp = new SimpleDateFormat(config.getString("logging.format.timestamp_format")).format(new Date());
        return format.replace("{time}", timestamp)
                .replace("{player}", playerName)
                .replace("{message}", additionalInfo)
                .replace("{command}", additionalInfo);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (config.getBoolean("logging.events.log_join")) {
            String playerName = event.getPlayer().getName();
            String logMessage = formatLogMessage(config.getString("logging.format.join_format"), playerName, "");
            logToFile(logMessage);
            getLogger().info(logMessage);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (config.getBoolean("logging.events.log_quit")) {
            String playerName = event.getPlayer().getName();
            String logMessage = formatLogMessage(config.getString("logging.format.quit_format"), playerName, "");
            logToFile(logMessage);
            getLogger().info(logMessage);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (config.getBoolean("logging.events.log_chat")) {
            String playerName = event.getPlayer().getName();
            String message = event.getMessage();
            String logMessage = formatLogMessage(config.getString("logging.format.chat_format"), playerName, message);
            logToFile(logMessage);
            getLogger().info(logMessage);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (config.getBoolean("logging.events.log_commands")) {
            String playerName = event.getPlayer().getName();
            String command = event.getMessage();
            String logMessage = formatLogMessage(config.getString("logging.format.command_format"), playerName, command);
            logToFile(logMessage);
            getLogger().info(logMessage);
        }
    }
}
