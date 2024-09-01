package pl.htgmc.htgloggers.listener;

import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.events.LogRequestEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LogRequestListener implements Listener {

    private final HTGLoggers plugin;

    public LogRequestListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogRequestEvent(LogRequestEvent event) {
        String pluginName = event.getPluginName();
        plugin.createLogFile(pluginName);
    }
}
