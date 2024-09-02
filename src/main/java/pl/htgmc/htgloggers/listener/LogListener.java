package pl.htgmc.htgloggers.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.events.LogEvent;

public class LogListener implements Listener {

    private final HTGLoggers plugin;

    public LogListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogEvent(LogEvent event) {
        // Sprawdzenie, czy logowanie jest włączone
        if (plugin.getConfig().getBoolean("logging.enabled")) {
            // Logowanie wiadomości
            plugin.getLogger().info("LogEvent captured: " + event.getMessage());
            plugin.logToFile(event.getMessage());
        }
    }
}
