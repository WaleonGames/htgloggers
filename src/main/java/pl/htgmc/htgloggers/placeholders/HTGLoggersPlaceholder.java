package pl.htgmc.htgloggers.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HTGLoggersPlaceholder extends PlaceholderExpansion {

    private final JavaPlugin plugin;

    public HTGLoggersPlaceholder(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "htgloggers"; // Use a unique identifier for your plugin
    }

    @Override
    public String getAuthor() {
        return "YourName"; // Your name or your plugin's name
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion(); // Return the plugin version
    }

    @Override
    public boolean canRegister() {
        return true; // Allow registration
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equals("version")) {
            return plugin.getDescription().getVersion(); // Return plugin version for %htgloggers_version%
        }
        return null; // Return null for unrecognized placeholders
    }
}
