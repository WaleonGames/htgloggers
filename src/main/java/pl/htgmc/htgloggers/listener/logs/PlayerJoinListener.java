package pl.htgmc.htgloggers.listener.logs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.htgmc.htgloggers.HTGLoggers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerJoinListener implements Listener {

    private final HTGLoggers plugin;

    public PlayerJoinListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        String timestamp = new SimpleDateFormat(plugin.getConfig().getString("logging.format.timestamp_format")).format(new Date());
        String logMessage = plugin.getConfig().getString("logging.format.join_format")
                .replace("{time}", timestamp)
                .replace("{player}", playerName);

        plugin.getLogger().info(logMessage);
    }
}
