package pl.htgmc.htgloggers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class HTGLoggers extends JavaPlugin implements Listener {

    private String logFileName;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        logFileName = getDataFolder() + "/" + getName() + "-log.txt";
        getLogger().info(getName() + " enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(getName() + " disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        logToFile("Player joined: " + event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        logToFile("Player quit: " + event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        logToFile("Player executed command: " + event.getPlayer().getName() + " used command: " + event.getMessage());
    }

    private void logToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // API method to log messages with custom formatting
    public void logCustomMessage(String message) {
        logToFile(message);
    }
}
