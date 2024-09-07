package pl.htgmc.htgloggers.api;

import pl.htgmc.htgloggers.HTGLoggers;

import java.io.File;
import java.io.IOException;

public class HTGLoggersAPI {

    private static HTGLoggers plugin;

    public static void initialize(HTGLoggers plugin) {
        HTGLoggersAPI.plugin = plugin;
    }

    public static File createLogFile(String fileName) {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            plugin.getLogger().severe("Could not create data folder!");
            return null;
        }

        File logFile = new File(dataFolder, fileName);
        try {
            if (!logFile.exists() && !logFile.createNewFile()) {
                plugin.getLogger().severe("Could not create log file: " + fileName);
                return null;
            }
        } catch (IOException e) {
            plugin.getLogger().severe("An error occurred while creating the log file: " + e.getMessage());
            return null;
        }
        return logFile;
    }
}
