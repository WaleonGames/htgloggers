package pl.htgmc.htgloggers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;
import pl.htgmc.htgloggers.api.HTGLoggersAPI;
import pl.htgmc.htgloggers.listener.LogListener;
import pl.htgmc.htgloggers.listener.LogRequestListener;
import pl.htgmc.htgloggers.listener.logs.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class HTGLoggers extends JavaPlugin implements Listener {

    private File logFile;
    private FileConfiguration config;
    private Map<String, String> formatProperties = new HashMap<>();
    private String currentVersion;

    @Override
    public void onEnable() {
        // Ładowanie konfiguracji
        saveDefaultConfig();
        config = getConfig();

        // Ustawienie aktualnej wersji
        currentVersion = getDescription().getVersion();

        // Ładowanie Plugin
        getLogger().info("§6-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        getLogger().info("§e" + getHTGLoggersLogo());
        getLogger().info("§eŁadowanie LoggerPlugin v" + currentVersion);
        getLogger().info("§6-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

        // Sprawdzenie aktualności wersji
        checkForUpdates();

        // Inicjalizacja API i rejestracja pluginu
        HTGLoggersAPI.initialize(this);

        // Rejestracja eventów - można dodać warunki na podstawie konfiguracji
        registerListeners();

        // Informacja o uruchomieniu pluginu
        getLogger().info("§aLoggerPlugin został pomyślnie włączony!");

        // Tworzenie lub ładowanie pliku logu
        HTGLoggersAPI.createLogFile("htgloggers");

        // Zakończenie procesu ładowania
        getLogger().info("§6-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }

    @Override
    public void onDisable() {
        getLogger().info("LoggerPlugin has been disabled!");
    }

    private String getHTGLoggersLogo() {
        return
                " _    _ _____   _____                 _              \n" +
                        "| |  | |  __ \\ / ____|               | |             \n" +
                        "| |__| | |  | | |  __  __ _ _ __ __ _| |_ ___  _ __  \n" +
                        "|  __  | |  | | | |_ |/ _` | '__/ _` | __/ _ \\| '_ \\ \n" +
                        "| |  | | |__| | |__| | (_| | | | (_| | || (_) | | | |\n" +
                        "|_|  |_|_____/ \\_____|\\__,_|_|  \\__,_|\\__\\___/|_| |_|\n";
    }

    private void checkForUpdates() {
        try {
            URL url = new URL("http://htgloggers.great-site.net/check-version.php"); // Zamień na rzeczywisty URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                getLogger().warning("Unable to check for updates.");
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Przetwarzanie odpowiedzi JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            String latestVersion = jsonResponse.getString("latestVersion");

            if (!latestVersion.equals(currentVersion)) {
                getLogger().warning("New version available! Current: " + currentVersion + ", Latest: " + latestVersion);
            } else {
                getLogger().info("You are using the latest version: " + currentVersion);
            }

        } catch (Exception e) {
            getLogger().severe("Error checking for updates: " + e.getMessage());
        }
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
