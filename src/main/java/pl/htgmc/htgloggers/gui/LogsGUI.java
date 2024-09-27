package pl.htgmc.htgloggers.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LogsGUI {

    private final String title = "Support Plugins";

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, title);

        // Add support plugin items
        ItemStack supportPlugin = new ItemStack(Material.PAPER);
        ItemMeta meta = supportPlugin.getItemMeta();
        meta.setDisplayName("Support Plugin Name");
        supportPlugin.setItemMeta(meta);

        inventory.addItem(supportPlugin);

        player.openInventory(inventory);
    }
}
