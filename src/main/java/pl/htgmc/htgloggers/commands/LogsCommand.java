package pl.htgmc.htgloggers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.htgmc.htgloggers.api.HTGLoggersGuiAPI;

public class LogsCommand implements CommandExecutor {

    private HTGLoggersGuiAPI guiApi = new HTGLoggersGuiAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /logs <reload/support>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "support":
                guiApi.openSupportGui(player);
                break;
            case "reload":
                // Add logic to reload configurations
                player.sendMessage("Configurations reloaded.");
                break;
            default:
                player.sendMessage("Unknown argument. Use /logs <reload/support>");
                break;
        }
        return true;
    }
}
