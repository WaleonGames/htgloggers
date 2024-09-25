package pl.htgmc.htgloggers.listener.logs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.api.HTGLoggersAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatListener implements Listener {

    private final HTGLoggers plugin;

    public ChatListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();
        String timestamp = new SimpleDateFormat(plugin.getConfig().getString("logging.format.timestamp_format")).format(new Date());
        String logMessage = plugin.getConfig().getString("logging.format.chat_format")
                .replace("{time}", timestamp)
                .replace("{player}", playerName)
                .replace("{message}", message);

        plugin.getLogger().info(logMessage);
        HTGLoggersAPI.logToFile(logMessage, "htgloggers");
    }
}