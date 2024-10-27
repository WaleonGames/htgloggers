package pl.htgmc.htgloggers.listener.logs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.api.HTGLoggersAPI;

public class PlayerAdvancementListener implements Listener {

    private final HTGLoggers plugin;

    public PlayerAdvancementListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        if (plugin.getConfig().getBoolean("logging.events.log_advancement")) {
            String playerName = event.getPlayer().getName();
            String advancement = event.getAdvancement().getKey().getKey();
            // Poprawne wywołanie getFormattedMessage
            String logMessage = plugin.getFormattedMessage("advancement_format", playerName, advancement);
            plugin.getLogger().info(logMessage);
            HTGLoggersAPI.logToFile(logMessage, "htgloggers");
        }
    }
}
