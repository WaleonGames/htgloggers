package pl.htgmc.htgloggers.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HTGLoggersGuiAPI {

    public void openSupportGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "Support Plugins");
        // Populate the GUI with support plugin options
        // e.g., gui.addItem(supportPluginItem);
        player.openInventory(gui);
    }

    // Add more GUI functionalities as needed
}
