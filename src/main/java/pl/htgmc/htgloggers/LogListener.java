package pl.htgmc.htgloggers;

import pl.htgmc.htgloggers.events.LogEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogListener implements Listener {

    @EventHandler
    public void onLogEvent(LogEvent event) {
        String message = event.getMessage();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("plugins/LoggerPlugin/logs.txt", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
