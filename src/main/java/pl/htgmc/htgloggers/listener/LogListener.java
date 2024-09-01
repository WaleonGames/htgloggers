package pl.htgmc.htgloggers.listener;

import pl.htgmc.htgloggers.HTGLoggers;
import pl.htgmc.htgloggers.events.LogEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogListener implements Listener {

    private final HTGLoggers plugin; // Dodajemy kontekst pluginu

    public LogListener(HTGLoggers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogEvent(LogEvent event) {
        String message = event.getMessage();
        File logFile = new File(plugin.getDataFolder(), "htgloggers-log.log");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
