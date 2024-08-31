package pl.htgmc.htgloggers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.htgmc.htgloggers.events.LogEvent;

public class HTGLoggers extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new LogListener(), this);
        getLogger().info("LoggerPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LoggerPlugin has been disabled!");
    }

    public void log(String message) {
        Bukkit.getServer().getPluginManager().callEvent(new pl.htgmc.htgloggers.events.LogEvent(message));
    }
}
