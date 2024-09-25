package pl.htgmc.htgloggers.listener.logs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.api.HTGLoggersAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandListener implements Listener {

    private final HTGLoggers plugin;

    public CommandListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String playerName = event.getPlayer().getName();
        String command = event.getMessage();
        String timestamp = new SimpleDateFormat(plugin.getConfig().getString("logging.format.timestamp_format")).format(new Date());
        String logMessage = plugin.getConfig().getString("logging.format.command_format")
                .replace("{time}", timestamp)
                .replace("{player}", playerName)
                .replace("{command}", command);

        plugin.getLogger().info(logMessage);
        HTGLoggersAPI.logToFile(logMessage, "htgloggers");
    }
}