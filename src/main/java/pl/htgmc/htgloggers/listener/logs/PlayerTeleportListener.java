package pl.htgmc.htgloggers.listener.logs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.api.HTGLoggersAPI;

public class PlayerTeleportListener implements Listener {

    private final HTGLoggers plugin;

    public PlayerTeleportListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (plugin.getConfig().getBoolean("logging.events.log_teleport")) {
            String playerName = event.getPlayer().getName();
            String location = event.getTo() != null ? event.getTo().toString() : "Unknown";
            // Poprawne wywołanie getFormattedMessage
            String logMessage = plugin.getFormattedMessage("teleport_format", playerName, location);
            plugin.getLogger().info(logMessage);
            HTGLoggersAPI.logToFile(logMessage, "htgloggers");
        }
    }
}
