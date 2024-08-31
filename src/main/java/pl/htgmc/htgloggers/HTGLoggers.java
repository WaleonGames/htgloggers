package pl.htgmc.htgloggers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.htgmc.htgloggers.events.LogEvent;
import pl.htgmc.htgloggers.listener.LogListener;
import pl.htgmc.htgloggers.listener.LogRequestListener;

import java.io.File;
import java.io.IOException;

public class HTGLoggers extends JavaPlugin {

    @Override
    public void onEnable() {
        // Rejestrujemy nasłuchiwacze
        getServer().getPluginManager().registerEvents(new LogListener(), this);
        getServer().getPluginManager().registerEvents(new LogRequestListener(this), this);
        getLogger().info("LoggerPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LoggerPlugin has been disabled!");
    }

    public void createLogFile(String pluginName) {
        // Create the data folder if it doesn't exist
        File dataFolder = getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            getLogger().severe("Could not create data folder!");
            return;
        }

        // Create the log file for the specified plugin
        File logFile = new File(dataFolder, pluginName + "-log.log");
        try {
            if (!logFile.exists() && !logFile.createNewFile()) {
                getLogger().severe("Could not create log file for plugin: " + pluginName);
            }
        } catch (IOException e) {
            getLogger().severe("An error occurred while creating the log file for plugin " + pluginName + ": " + e.getMessage());
        }
    }

    public void log(String message) {
        Bukkit.getServer().getPluginManager().callEvent(new LogEvent(message));
    }
}
