package pl.htgmc.htgloggers.listener.logs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.api.HTGLoggersAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerQuitListener implements Listener {

    private final HTGLoggers plugin;

    public PlayerQuitListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        String timestamp = new SimpleDateFormat(plugin.getConfig().getString("logging.format.timestamp_format")).format(new Date());
        String logMessage = plugin.getConfig().getString("logging.format.quit_format")
                .replace("{time}", timestamp)
                .replace("{player}", playerName);

        plugin.getLogger().info(logMessage);
        HTGLoggersAPI.logToFile(logMessage, "htgloggers");
    }
}