package pl.htgmc.htgloggers.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.api.HTGLoggersAPI;
import pl.htgmc.htgloggers.events.LogRequestEvent;

public class LogRequestListener implements Listener {

    private final HTGLoggers plugin;

    public LogRequestListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogRequestEvent(LogRequestEvent event) {
        String pluginName = event.getPluginName();

        try {
            // Używanie API do tworzenia pliku logu
            HTGLoggersAPI.createLogFile(pluginName);
            plugin.getLogger().info("Log file creation requested for plugin: " + pluginName);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to create log file for plugin: " + pluginName + ". Error: " + e.getMessage());
        }
    }
}
