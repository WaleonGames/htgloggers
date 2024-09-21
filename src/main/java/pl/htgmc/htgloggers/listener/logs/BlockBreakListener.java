package pl.htgmc.htgloggers.listener.logs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.htgmc.htgloggers.HTGLoggers;

public class BlockBreakListener implements Listener {

    private final HTGLoggers plugin;

    public BlockBreakListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("logging.events.log_block_break")) {
            String playerName = event.getPlayer().getName();
            String blockType = event.getBlock().getType().toString();
            // Użyj poprawnej wersji metody getFormattedMessage
            String logMessage = plugin.getFormattedMessage("block_break_format", playerName, blockType);
            plugin.logToFile(logMessage);
        }
    }
}
