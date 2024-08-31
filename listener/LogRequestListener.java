package pl.htgmc.htgloggers.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.events.LogRequestEvent;

public class LogRequestListener implements Listener {

    private final HTGLoggers plugin;

    public LogRequestListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogRequest(LogRequestEvent event) {
        String pluginName = event.getPluginName();
        plugin.createLogFile(pluginName);
        plugin.getLogger().info("Log file created for plugin: " + pluginName);
    }
}
